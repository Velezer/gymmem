package ariefsyaifu.gymmem.producer;

import java.math.BigDecimal;
import java.time.Instant;

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
            String cardHolderName,
            BigDecimal amount,
            Instant timestamp) {
        JsonObject jo = new JsonObject();
        jo.addProperty("creditCardNumber", creditCardNumber);
        jo.addProperty("cvv", cvv);
        jo.addProperty("expiredDate", expiredDate);
        jo.addProperty("cardHolderName", cardHolderName);
        jo.addProperty("amount", amount);
        jo.addProperty("timestamp", timestamp.toString());
        kafkaTemplate.send("gymmem-created-payment", jo.toString());
    }

}
