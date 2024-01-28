package ariefsyaifu.gymmem.payment.dto;

import jakarta.validation.constraints.NotBlank;

public class CreatePaymentRequestBody {
    @NotBlank(message = "creditCardId must not be blank")
    public String creditCardId;
    
    @NotBlank(message = "subscriptionId must not be blank")
    public String subscriptionId;
}
