package ariefsyaifu.gymmem.otp.service;

import java.security.SecureRandom;
import java.time.Instant;

import org.springframework.stereotype.Component;

import ariefsyaifu.gymmem.otp.dto.GenerateOtpRequestBody;
import ariefsyaifu.gymmem.otp.dto.GenerateOtpResponse;
import ariefsyaifu.gymmem.otp.model.Otp;
import ariefsyaifu.gymmem.otp.producer.EmailProducer;
import ariefsyaifu.gymmem.otp.repository.OtpRepository;

@Component
public class OtpService {

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
        newOtp.expiredAt = Instant.now().plusSeconds(300);
        newOtp.type = params.type;
        otpRepository.save(newOtp);

        // @MOCK otp sent by email
        emailProducer.sendGeneratedOtp(params.email, newOtp.value);

        return GenerateOtpResponse.valueOf(newOtp);
    }

    public boolean validateOtp(String id, String key, String value, Otp.Type type) {
        Otp otp = otpRepository.findById(id).orElse(null);
        boolean isNotValid = (otp == null || !otp.key.equals(key) || !otp.value.equals(value)
                || !otp.type.equals(type));
        if (!isNotValid) {
            otpRepository.delete(otp);
        }
        return !isNotValid;
    }

}
