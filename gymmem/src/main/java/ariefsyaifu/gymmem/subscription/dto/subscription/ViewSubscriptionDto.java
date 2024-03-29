package ariefsyaifu.gymmem.subscription.dto.subscription;

import java.math.BigDecimal;

import ariefsyaifu.gymmem.subscription.model.Subscription;

public class ViewSubscriptionDto {

    public String id;

    public String userId;

    public String productId;

    public Integer remainingTimesOfMeeting;

    public BigDecimal amount;

    public Subscription.Status status;

    public static ViewSubscriptionDto valueOf(Subscription s) {
        ViewSubscriptionDto dto = new ViewSubscriptionDto();
        dto.id = s.id;
        dto.userId = s.userId;
        dto.productId = s.product.id;
        dto.remainingTimesOfMeeting = s.remainingTimesOfMeeting;
        dto.amount = s.amount;
        dto.status = s.status;
        return dto;
    }

}
