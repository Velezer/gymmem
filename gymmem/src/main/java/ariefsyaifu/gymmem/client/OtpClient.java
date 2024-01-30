package ariefsyaifu.gymmem.client;

import org.springframework.stereotype.Component;

import ariefsyaifu.gymmem.otp.model.Otp;
import ariefsyaifu.gymmem.otp.service.OtpService;

/**
 * i'm trying to make modulith
 * this implementation can be change over http call if otp service is separated
 */
@Component
public class OtpClient {

    public OtpClient(OtpService otpService) {
        this.otpService = otpService;
    }

    private OtpService otpService;

    public boolean validateOtp(String id, String key, String value, Otp.Type type) {
        return otpService.validateOtp(id, key, value, type);
    }

}
