package ariefsyaifu.gymmem.payment.dto;

public class CreatePaymentResponse {
    public String id;

    public static CreatePaymentResponse valueOf(String id) {
        CreatePaymentResponse r = new CreatePaymentResponse();
        r.id = id;
        return r;
    }
}
