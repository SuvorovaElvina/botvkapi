package com.task.botvk.service;

public interface MessageSenderService<T> {
    void send(T message);
}
