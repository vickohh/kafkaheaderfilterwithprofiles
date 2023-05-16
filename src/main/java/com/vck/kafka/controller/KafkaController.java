package com.vck.kafka.controller;

import com.vck.kafka.model.Payload;
import com.vck.kafka.producer.KafkaPublisher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("v1")
public class KafkaController {

    @Autowired
    private KafkaPublisher sender;

    @GetMapping("/publish/{name}")
    public String postMessage(@PathVariable("name") final String environment){

        Payload data = new Payload();
        data.setLoanNumber("300456695");
        data.setSourceEnv(environment);
        data.setStatus("PreQual");
        data.setMappingSetID("CREATEAPPLICATION");

        sender.send(data);

        return "Message Published Successfully ok";
    }
}
