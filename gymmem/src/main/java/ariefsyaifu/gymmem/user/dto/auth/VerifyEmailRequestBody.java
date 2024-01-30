package ariefsyaifu.gymmem.user.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class VerifyEmailRequestBody {
    @NotBlank(message = "otpId must not be blank")
    public String otpId;
    @NotBlank(message = "email must not be blank")
    public String email;
    @NotBlank(message = "otpValue must not be blank")
    public String otpValue;
}
