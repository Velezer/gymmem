package ariefsyaifu.gymmem.otp.service;

import java.security.SecureRandom;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ariefsyaifu.gymmem.otp.dto.GenerateOtpRequestBody;
import ariefsyaifu.gymmem.otp.dto.GenerateOtpResponse;
import ariefsyaifu.gymmem.otp.model.Otp;
import ariefsyaifu.gymmem.otp.producer.EmailProducer;
import ariefsyaifu.gymmem.otp.repository.OtpRepository;

@Component
public class OtpService {

    @Value("${otp.expiration.minutes}")
    private Integer expirationMinutes;

    public OtpService(OtpRepository otpRepository, EmailProducer emailProducer) {
        this.otpRepository = otpRepository;
        this.emailProducer = emailProducer;
    }

    private EmailProducer emailProducer;
    private OtpRepository otpRepository;

    public GenerateOtpResponse generate(GenerateOtpRequestBody params) {
        Otp otp = otpRepository.findByKey(params.email);
        if (otp != null) {
            otpRepository.deleteById(otp.id);
        }

        Otp newOtp = new Otp();
        newOtp.key = params.email;
        newOtp.value = String.valueOf(new SecureRandom().nextInt(1000, 9999));
        newOtp.expiredAt = Instant.now().plusSeconds(expirationMinutes * 60);
        newOtp.type = params.type;
        otpRepository.save(newOtp);

        // @MOCK otp sent by email
        emailProducer.sendGeneratedOtp(params.email, newOtp.value);

        return GenerateOtpResponse.valueOf(newOtp);
    }

    public boolean validateOtp(String id, String key, String value, Otp.Type type) {
        Otp otp = otpRepository.findById(id).orElse(null);
        if (otp == null) {
            return false;
        }
        if (!otp.key.equals(key)) {
            return false;
        }
        if (!otp.value.equals(value)) {
            return false;
        }
        if (!otp.type.equals(type)) {
            return false;
        }
        if (Instant.now().isAfter(otp.expiredAt)) {
            return false;
        }
        otpRepository.delete(otp);
        return true;
    }

}
