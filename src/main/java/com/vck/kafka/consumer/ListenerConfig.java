package com.vck.kafka.consumer;


import com.vck.kafka.model.Payload;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.nio.charset.Charset;

import static java.util.Objects.nonNull;

@EnableKafka
@Configuration
public class ListenerConfig {

    private KafkaProps kafkaProps;
    Charset charset = StandardCharsets.UTF_16;

    @Value("${spring.environment}")
    private  String ENVIRONMENT;

    ListenerConfig(KafkaProps kafkaProps) {
        this.kafkaProps = kafkaProps;
    }

    @Bean("kafkaListenerFilteredFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Object, Object> concurrentKafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(new DefaultKafkaConsumerFactory(kafkaProps.consumerProps()));
        concurrentKafkaListenerContainerFactory.setAckDiscarded(true);
        concurrentKafkaListenerContainerFactory.setRecordFilterStrategy(consumerRecord -> {
            if(nonNull(consumerRecord.headers())) {
                System.out.println("consumerRecord header iteration started....");
                Iterator<Header> iterator = consumerRecord.headers().iterator();
                while(iterator.hasNext()) {
                    Header header = iterator.next();
                    String headerValue = new String(header.value(), charset);
                    String headerkey = new String(header.key().getBytes(), charset);
                    if(headerValue.contains(ENVIRONMENT)) {
                        System.out.println("Kafka msg consumed");
                        return false;
                    }else{
                        System.out.println("No '"+ ENVIRONMENT + "' in Kafka headers");
                    }
                }
            }
            System.out.println("Kafka msg skipped");
            return true;
        });




        return concurrentKafkaListenerContainerFactory;
    }

}
