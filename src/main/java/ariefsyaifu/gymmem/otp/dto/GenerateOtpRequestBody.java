package ariefsyaifu.gymmem.otp.dto;

import ariefsyaifu.gymmem.otp.model.Otp;
import jakarta.validation.Valid;

public class GenerateOtpRequestBody {

    @Valid
    public String email;
    
    @Valid
    public Otp.Type type;

}
