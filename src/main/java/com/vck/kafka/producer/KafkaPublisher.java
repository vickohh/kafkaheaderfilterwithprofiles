package com.vck.kafka.producer;

import com.vck.kafka.model.Payload;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaPublisher {

    @Value("${kafka.topic}")
    private String topic;


    private KafkaTemplate<String, Payload> kafkaTemplate;

    public KafkaPublisher(KafkaTemplate<String, Payload> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private static  String ENVIRONMENT;
    private static final Logger LOG = LoggerFactory.getLogger(KafkaPublisher.class);

    public void send(Payload data){

        ENVIRONMENT = data.getSourceEnv();
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader(ENVIRONMENT, data.getSourceEnv().getBytes(StandardCharsets.UTF_16)));
        headers.add(new RecordHeader("VCK", "victor".getBytes()));

        ProducerRecord<String, Payload> bar = new ProducerRecord<>(topic, 0, "111", data, headers);
        LOG.info("sending message='{}' to topic='{}'", data.toString(), topic);

        kafkaTemplate.send(bar);
    }

}
