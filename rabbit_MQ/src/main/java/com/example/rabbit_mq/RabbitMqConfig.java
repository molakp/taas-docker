package com.example.rabbit_mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.queue.name}")
    private String qName;


    @Value("${rabbitmq.exchange}")
    private String exchange;


    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    @Bean
    Queue qu() {
        // se mettiamo true come secondo parametro, Questo significa che la coda verrà mantenuta anche dopo
        // che il server RabbitMQ verrà riavviato, in modo che i messaggi non vengano persi.
        return new Queue(qName, Boolean.FALSE);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    Binding binding(final Queue queue, final TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(routingKey);
    }
}
