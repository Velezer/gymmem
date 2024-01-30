package ariefsyaifu.gymmem.payment.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PatchPaymentRequestBody {
    @NotBlank(message = "otpId must not be blank")
    public String otpId;
    @NotBlank(message = "otpValue must not be blank")
    public String otpValue;

    @NotNull(message = "amount required")
    @Min(value = 0, message = "amount minimum is 0")
    public BigDecimal amount;
}
