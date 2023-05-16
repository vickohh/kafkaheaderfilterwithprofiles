package com.vck.kafka.consumer;

import com.vck.kafka.model.Payload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

@Service
public class Listener {
    @Value("${spring.environment}")
    private  String ENVIRONMENT;
    private static final Logger LOG = LoggerFactory.getLogger(Listener.class);

    @KafkaListener(topics = "${kafka.topic}",containerFactory = "kafkaListenerFilteredFactory")
    public void receive(@org.springframework.messaging.handler.annotation.Payload Payload payload,
                         @Headers MessageHeaders messageHeaders) {

        LOG.info("- - - - - - - - - - - - - - -");
        LOG.info("received message -- SCOPE: " + ENVIRONMENT + " ONLY-- "+ ENVIRONMENT + " CONSUMER ='{}'", payload.toString());
        messageHeaders.keySet().forEach(key -> {
            Object value = messageHeaders.get(key);
            if (key.equals("Environment")){
                LOG.info("{}: {}", key, new String((byte[])value));
            } else {
                LOG.info("{}: {}", key, value);
            }
        });
    }
}
