package ariefsyaifu.gymmem.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic topicCreatedPayment() {
        return new NewTopic("gymmem-created-payment", 1, (short) 1);
    }

}
