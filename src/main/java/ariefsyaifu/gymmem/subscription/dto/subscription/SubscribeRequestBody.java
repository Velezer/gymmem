package ariefsyaifu.gymmem.subscription.dto.subscription;

import jakarta.validation.constraints.NotBlank;

public class SubscribeRequestBody {

    @NotBlank(message = "productId must not be blank")
    public String productId;
    
}
