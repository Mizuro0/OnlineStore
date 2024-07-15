package org.mizuro.aviatickets.kafka;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.models.MailMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, MailMessage> kafkaTemplate;

    public void sendMessage(MailMessage message) {
        kafkaTemplate.send("confirmation", message);
    }
}
