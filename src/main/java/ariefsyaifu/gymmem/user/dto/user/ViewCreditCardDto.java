package ariefsyaifu.gymmem.user.dto.user;

import ariefsyaifu.gymmem.user.model.CreditCard;

public class ViewCreditCardDto {
    public String id;

    public static ViewCreditCardDto valueOf(CreditCard cc) {
        ViewCreditCardDto dto = new ViewCreditCardDto();
        dto.id = cc.id;
        return dto;
    }
}
