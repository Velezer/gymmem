package ariefsyaifu.gymmem.user.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequestBody {

    @NotBlank(message = "accessToken must not be blank")
    public String accessToken;
    
    @NotBlank(message = "refreshToken must not be blank")
    public String refreshToken;
    
}
