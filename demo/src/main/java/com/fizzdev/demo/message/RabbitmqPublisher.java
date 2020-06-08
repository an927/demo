package com.fizzdev.demo.message;

import com.fizzdev.demo.dao.domain.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqPublisher {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    public void produceMsg(User msg) {
        amqpTemplate.convertAndSend(exchange, "", msg);
    }
}
