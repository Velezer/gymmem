package ariefsyaifu.gymmem.otp.dto;

import ariefsyaifu.gymmem.otp.model.Otp;

public class GenerateOtpResponse {
    public String id;
    public String value;

    public static GenerateOtpResponse valueOf(Otp newOtp) {
        GenerateOtpResponse r = new GenerateOtpResponse();
        r.id = newOtp.id;
        r.value = newOtp.value;
        return r;
    }
}
