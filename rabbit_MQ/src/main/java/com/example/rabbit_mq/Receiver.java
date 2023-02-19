package com.example.rabbit_mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    Queue qu;



    @RabbitListener(queues = "#{qu.getName()}")
    public void receiveMessage(final String message) {
        LOGGER.info("Getting messages.....");
        LOGGER.info("Finally Receiver received the message and the message  is..\n" + message);
    }
}

