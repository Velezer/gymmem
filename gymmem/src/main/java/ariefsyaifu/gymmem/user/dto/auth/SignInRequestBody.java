package ariefsyaifu.gymmem.user.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class SignInRequestBody {
    @NotBlank(message = "email must not be blank")
    public String email;
    @NotBlank(message = "password must not be blank")
    public String password;

}
