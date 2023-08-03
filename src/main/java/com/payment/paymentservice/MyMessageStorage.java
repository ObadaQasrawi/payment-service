package com.payment.paymentservice;

import org.springframework.stereotype.Component;

@Component
public class MyMessageStorage {

    private String lastReceivedMessage;

    public void setLastReceivedMessage(String message) {
        lastReceivedMessage = message;
    }

    public String getLastReceivedMessage() {
        return lastReceivedMessage;
    }
}
