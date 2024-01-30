package ariefsyaifu.gymmem.otp.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailProducer {
    private Logger logger = LoggerFactory.getLogger(EmailProducer.class);

    public void sendGeneratedOtp(String email, String otpValue) {
        logger.info("email={}, otpValue={}", email, otpValue);
    }

}
