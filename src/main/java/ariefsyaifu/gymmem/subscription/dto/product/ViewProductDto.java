package ariefsyaifu.gymmem.subscription.dto.product;

import java.math.BigDecimal;

import ariefsyaifu.gymmem.subscription.model.Product;

public class ViewProductDto {

    public String id;

    public BigDecimal price;

    public String scheduleDaysOfWeek;

    public Integer timesOfMeeting;

    public Short minutesOfDuration;

    public String description;

    public static ViewProductDto valueOf(Product p) {
        ViewProductDto dto = new ViewProductDto();
        dto.id = p.id;
        dto.price = p.price;
        dto.scheduleDaysOfWeek = String.valueOf(p.scheduleDaysOfWeek);
        dto.timesOfMeeting = p.timesOfMeeting;
        dto.minutesOfDuration = p.minutesOfDuration;
        dto.description = p.description;
        return dto;
    }
}
