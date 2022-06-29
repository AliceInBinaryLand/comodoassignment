package org.thejava.assignment.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thejava.assignment.model.Todo;
import org.thejava.assignment.service.api.MqServiceApi;

@Component
public class MqServiceImpl implements MqServiceApi<Todo> {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void send(String topic, Todo message) {
        rabbitTemplate.convertAndSend(topic , message);
    }
}
