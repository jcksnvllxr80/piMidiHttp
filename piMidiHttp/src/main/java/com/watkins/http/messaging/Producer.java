package com.watkins.http.messaging;

public interface Producer extends AmqpInterface {
    void sendEmpMessage(String empMsg);
}
