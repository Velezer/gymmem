package ariefsyaifu.gymmem.user.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import ariefsyaifu.gymmem.client.OtpClient;
import ariefsyaifu.gymmem.otp.model.Otp;
import ariefsyaifu.gymmem.user.dto.auth.RefreshTokenRequestBody;
import ariefsyaifu.gymmem.user.dto.auth.ResetRequestBody;
import ariefsyaifu.gymmem.user.dto.auth.SignInRequestBody;
import ariefsyaifu.gymmem.user.dto.auth.SignInResponse;
import ariefsyaifu.gymmem.user.dto.auth.SignUpRequestBody;
import ariefsyaifu.gymmem.user.dto.auth.VerifyCheckResponse;
import ariefsyaifu.gymmem.user.dto.auth.VerifyEmailRequestBody;
import ariefsyaifu.gymmem.user.model.CreditCard;
import ariefsyaifu.gymmem.user.model.Token;
import ariefsyaifu.gymmem.user.model.User;
import ariefsyaifu.gymmem.user.repository.CreditCardRepository;
import ariefsyaifu.gymmem.user.repository.TokenRepository;
import ariefsyaifu.gymmem.user.repository.UserRepository;
import ariefsyaifu.gymmem.util.JwtUtil;

@Component
public class AuthService {

    @Autowired
    public AuthService(
            CreditCardRepository creditCardRepository,
            UserRepository userRepository,
            TokenRepository tokenRepository,
            OtpClient otpClient,
            JwtUtil jwtUtil) {
        this.creditCardRepository = creditCardRepository;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.otpClient = otpClient;
        this.jwtUtil = jwtUtil;
    }

    private TokenRepository tokenRepository;
    private JwtUtil jwtUtil;
    private CreditCardRepository creditCardRepository;
    private OtpClient otpClient;
    private UserRepository userRepository;

    @Transactional
    public void signUp(SignUpRequestBody params) {

        boolean exists = userRepository.existsByEmail(params.email);
        if (exists) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        User user = new User();
        user.name = params.name;
        user.phoneNumber = params.phoneNumber;
        user.email = params.email;
        user.password = BCrypt.hashpw(params.password, BCrypt.gensalt());
        String cc = params.creditCardNumber +
                params.cvv +
                params.expiredDate +
                params.cardHolderName;
        cc = BCrypt.hashpw(cc, BCrypt.gensalt());

        user.status = User.Status.UNVERIFIED;
        userRepository.save(user);

        CreditCard creditCard = new CreditCard();
        creditCard.encrypted = cc;
        creditCard.user = user;
        creditCardRepository.save(creditCard);
    }

    public void verifyEmail(VerifyEmailRequestBody params) {
        boolean isValid = otpClient.validateOtp(params.otpId, params.email, params.otpValue, Otp.Type.VERIFY_EMAIL);
        if (!isValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP_IS_NOT_VALID");
        }

        User user = userRepository.findByEmail(params.email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        user.status = User.Status.VERIFIED;
        userRepository.save(user);
    }

    public SignInResponse signIn(SignInRequestBody params) {
        User user = userRepository.findByEmail(params.email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if (!user.status.equals(User.Status.VERIFIED)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (!BCrypt.checkpw(params.password, user.password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> claims = Map.of(
                "id", user.id,
                "email", user.email);

        String accessToken = jwtUtil.generateAccessToken(claims, user.email);
        String refreshToken = jwtUtil.generateRefreshToken(claims, accessToken);

        Token t = tokenRepository.findByUser_Id(user.id).orElse(new Token());
        t.user = user;
        t.encryptedAccessToken = BCrypt.hashpw(accessToken, BCrypt.gensalt());
        t.refreshToken = refreshToken;
        tokenRepository.save(t);
        return SignInResponse.valueOf(accessToken, refreshToken);
    }

    public void reset(ResetRequestBody params) {
        boolean isOtpValid = otpClient.validateOtp(params.otpId, params.email, params.otpValue,
                Otp.Type.RESET_PASSWORD);
        if (!isOtpValid) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findByEmail(params.email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        user.password = BCrypt.hashpw(params.password, BCrypt.gensalt());
        userRepository.save(user);
    }

    public SignInResponse refresh(RefreshTokenRequestBody params) {
        Token token = tokenRepository.findByRefreshToken(params.refreshToken);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        boolean isValid = BCrypt.checkpw(params.accessToken, token.encryptedAccessToken);
        if (!isValid) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        User user = token.user;

        Map<String, Object> claims = Map.of(
                "id", user.id,
                "email", user.email);

        String accessToken = jwtUtil.generateAccessToken(claims, user.email);
        tokenRepository.delete(token);
        return SignInResponse.valueOf(accessToken, null);

    }

    public VerifyCheckResponse verifyCheck(String email) {
        if (Optional.ofNullable(email).orElse("").isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EMAIL_NOT_PROVIDED");
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return VerifyCheckResponse.valueOf("UNREGISTERED");
        }

        return VerifyCheckResponse.valueOf(user.status.name());

    }

}
