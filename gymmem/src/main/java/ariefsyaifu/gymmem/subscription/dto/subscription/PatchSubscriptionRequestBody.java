package ariefsyaifu.gymmem.subscription.dto.subscription;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PatchSubscriptionRequestBody {

    @NotNull(message = "qty required")
    @Min(value = 1, message = "qty minimum is 1")
    public Integer qty;

}
