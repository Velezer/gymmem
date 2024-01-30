package ariefsyaifu.gymmem.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import ariefsyaifu.gymmem.user.dto.user.UpdateUserMeRequestBody;
import ariefsyaifu.gymmem.user.dto.user.ViewCreditCardDto;
import ariefsyaifu.gymmem.user.model.CreditCard;
import ariefsyaifu.gymmem.user.model.User;
import ariefsyaifu.gymmem.user.repository.CreditCardRepository;
import ariefsyaifu.gymmem.user.repository.UserRepository;
import io.jsonwebtoken.Claims;

@Component
public class UserService {

    @Autowired
    public UserService(UserRepository userRepository, CreditCardRepository creditCardRepository) {
        this.userRepository = userRepository;
        this.creditCardRepository = creditCardRepository;
    }

    private CreditCardRepository creditCardRepository;
    private UserRepository userRepository;

    public void updateMe(UpdateUserMeRequestBody params, Claims claims) {
        String userId = Optional.ofNullable(claims.get("id", String.class)).orElse("");
        if (userId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        });

        if (!Optional.ofNullable(params.name).orElse("").isBlank()) {
            user.name = params.name;
        }
        if (!Optional.ofNullable(params.password).orElse("").isBlank()) {
            user.password = BCrypt.hashpw(params.password, BCrypt.gensalt());
        }
        userRepository.save(user);
    }

    public boolean validateCreditCard(String userId, String creditCardId,
            String creditCardNumber,
            String cvv,
            String expiredDate,
            String cardHolderName) {
        String cc = creditCardNumber +
                cvv +
                expiredDate +
                cardHolderName;
        CreditCard creditCard = creditCardRepository.findByIdAndUser_Id(creditCardId, userId);
        if (creditCard == null) {
            return false;
        }
        return BCrypt.checkpw(cc, creditCard.encrypted);
    }

    public List<ViewCreditCardDto> getMeCreditCard(Claims c) {
        String userId = c.get("id", String.class);

        List<CreditCard> ccs = creditCardRepository.findAllByUser_Id(userId);

        return ccs.stream().map(cc -> ViewCreditCardDto.valueOf(cc)).toList();
    }

}
