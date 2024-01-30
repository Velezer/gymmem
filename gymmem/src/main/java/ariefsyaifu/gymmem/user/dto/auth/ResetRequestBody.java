package ariefsyaifu.gymmem.user.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class ResetRequestBody {
    @NotBlank(message = "email must not be blank")
    public String email;

    @NotBlank(message = "password must not be blank")
    public String password;

    @NotBlank(message = "otpId must not be blank")
    public String otpId;

    @NotBlank(message = "otpValue must not be blank")
    public String otpValue;


}
