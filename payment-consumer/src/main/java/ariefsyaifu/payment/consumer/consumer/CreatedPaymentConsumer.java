package ariefsyaifu.payment.consumer.consumer;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CreatedPaymentConsumer {
    private Logger logger = LoggerFactory.getLogger(CreatedPaymentConsumer.class);

    @Autowired
    public CreatedPaymentConsumer(
            ObjectMapper objectMapper,
            SimpMessageSendingOperations simpMessageSendingOperations) {
        this.objectMapper = objectMapper;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    private SimpMessageSendingOperations simpMessageSendingOperations;
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "gymmem-created-payment", groupId = "${spring.kafka.consumer.group.id}")
    public void consumeCreatedPayment(String message) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> payload = objectMapper.readValue(message, Map.class);
        String creditCardNumber = (String) payload.get("creditCardNumber");
        String cvv = (String) payload.get("cvv");
        // String expiredDate = (String) payload.get("expiredDate");
        String cardHolderName = (String) payload.get("cardHolderName");
        String timestamp = (String) payload.get("timestamp");
        BigDecimal amount = BigDecimal.valueOf((Double) Optional.ofNullable(payload.get("amount")).orElse(0));

        if (creditCardNumber == null) {
            throw new RuntimeException("creditCardNumber null");
        }
        if (cvv == null) {
            throw new RuntimeException("creditCardNumber null");
        }
        if (cardHolderName == null) {
            throw new RuntimeException("creditCardNumber null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("amount invalid");
        }

        Map<String, Object> r3 = RestClient.builder().baseUrl("http://localhost:8082/api/v1/mock/payment/r3").build()
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "creditCardNumber", creditCardNumber,
                        "amount", amount))
                .retrieve()
                .body(Map.class);
        logger.info("r3={}", r3);
        Map<String, Object> r4 = RestClient.builder().baseUrl("http://localhost:8082/api/v1/mock/payment/r4").build()
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "creditCardNumber", creditCardNumber,
                        "cvv", cvv))
                .retrieve()
                .body(Map.class);

        logger.info("r4={}", r4);
        Map<String, Object> r5 = RestClient.builder().baseUrl("http://localhost:8082/api/v1/mock/payment/r5").build()
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "creditCardNumber", creditCardNumber,
                        "cardHolderName", cardHolderName))
                .retrieve()
                .body(Map.class);
        logger.info("r5={}", r5);

        simpMessageSendingOperations.convertAndSend("/topic/payment/processing", Map.of(
                "startTimestamp", timestamp,
                "r3Timestamp", r3.get("timestamp"),
                "r4Timestamp", r4.get("timestamp"),
                "r5Timestamp", r5.get("timestamp"),
                "responseTimestamp", Instant.now()));
    }

}
