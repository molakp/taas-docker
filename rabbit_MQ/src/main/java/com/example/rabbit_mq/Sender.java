package com.example.rabbit_mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



import java.util.ArrayList;


@RestController
public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);


    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    Binding binding;

    @GetMapping(value = "/send/{msg}")
    @ResponseStatus(code = HttpStatus.OK)
    public String send(@PathVariable("msg") final String message) {
        LOGGER.info("Sending message to the queue.");
        rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), message);
        LOGGER.info("Message sent successfully to the queue!!!");
        return "Great!! your message is sent";
    }


    /*@RequestMapping("getAllPositions")
    public String getAllPositions() {

        ArrayList<Position> positions = new ArrayList<>();
        positionRepository.findAll().forEach(positions::add);
    }*/
}