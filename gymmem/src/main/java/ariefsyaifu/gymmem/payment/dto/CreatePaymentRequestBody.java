package ariefsyaifu.gymmem.payment.dto;

import jakarta.validation.constraints.NotBlank;

public class CreatePaymentRequestBody {
    @NotBlank(message = "creditCardId must not be blank")
    public String creditCardId;

    @NotBlank(message = "subscriptionId must not be blank")
    public String subscriptionId;

    @NotBlank(message = "creditCardNumber must not be blank")
    public String creditCardNumber;

    @NotBlank(message = "cvv must not be blank")
    public String cvv;

    @NotBlank(message = "expiredDate must not be blank")
    public String expiredDate;

    @NotBlank(message = "cardHolderName must not be blank")
    public String cardHolderName;

}
