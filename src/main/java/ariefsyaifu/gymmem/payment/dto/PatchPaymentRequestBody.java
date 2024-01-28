package ariefsyaifu.gymmem.payment.dto;

import jakarta.validation.constraints.NotBlank;

public class PatchPaymentRequestBody {
    @NotBlank(message = "otpId must not be blank")
    public String otpId;
    @NotBlank(message = "otpValue must not be blank")
    public String otpValue;
}
