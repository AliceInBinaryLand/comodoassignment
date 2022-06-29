package org.thejava.assignment.service.api;

public interface MqServiceApi<T> {

    void send(String topic , T message);

}
