package bnext.backend.position;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    Queue qu;

    @Autowired
    private PositionService positionService;

    @RabbitListener(queues = "#{qu.getName()}")
    public void receiveMessage(final String position) {
        LOGGER.info("Getting messages.....");
        LOGGER.info("Finally Receiver received the message and the message  is..\n" + position);


        ObjectMapper objectMapper = new ObjectMapper();
        Position received;
        try {
            Gson gson = new Gson();
            Position pos = gson.fromJson(position, Position.class);
            //received =  objectMapper.readValue(position, Position.class);
            positionService.createPosition(pos);
            LOGGER.info("Saved position received from RabbitMQ in DB");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }
}

