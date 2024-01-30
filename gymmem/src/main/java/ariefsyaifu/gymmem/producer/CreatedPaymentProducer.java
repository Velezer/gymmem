package ariefsyaifu.gymmem.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

@Component
public class CreatedPaymentProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public CreatedPaymentProducer(
            KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce(
            String creditCardNumber,
            String cvv,
            String expiredDate,
            String cardHolderName) {
        JsonObject jo = new JsonObject();
        jo.addProperty("creditCardNumber", creditCardNumber);
        jo.addProperty("cvv", cvv);
        jo.addProperty("expiredDate", expiredDate);
        jo.addProperty("cardHolderName", cardHolderName);
        kafkaTemplate.send("gymmem-created-payment", jo.toString());
    }

}
