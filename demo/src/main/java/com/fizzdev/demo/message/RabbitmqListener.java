package com.fizzdev.demo.message;

import com.fizzdev.demo.dao.domain.User;
import com.fizzdev.demo.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqListener {

    @Autowired
    UserService userService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receivedMessage(User msg) {
        userService.saveUser(msg);
    }

}
