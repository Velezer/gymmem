package ariefsyaifu.gymmem.user.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class SignUpRequestBody {
    @NotBlank(message = "name must not be blank")
    public String name;
    @NotBlank(message = "phoneNumber must not be blank")
    public String phoneNumber;
    @NotBlank(message = "email must not be blank")
    public String email;
    @NotBlank(message = "password must not be blank")
    public String password;

    @NotBlank(message = "creditCardNumber must not be blank")
    public String creditCardNumber;
    @NotBlank(message = "cvv must not be blank")
    public String cvv;
    @NotBlank(message = "expiredDate must not be blank")
    public String expiredDate;
    @NotBlank(message = "cardHolderName must not be blank")
    public String cardHolderName;
}
