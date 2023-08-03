package com.payment.paymentservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;

@Component
public class MQListener {

    private static final Logger logger = LoggerFactory.getLogger(controller.class);
    private final KafkaMessageProducer kafkaMessageProducer;

    @Autowired
    public MQListener(KafkaMessageProducer kafkaMessageProducer) {
        this.kafkaMessageProducer = kafkaMessageProducer;
    }

    @Async
    @JmsListener(destination = "DEV.QUEUE.1")
    public void receiveMessage(String message) throws IOException, SAXException {

        // there is another approach using @Scheduled(fixedDelay = 30000)
        logger.info("Received message: " + message +"  before   ");

        if (message == null) {
            // If the message is null, it means the queue is empty.
            // Sleep for 30 seconds before checking again.

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                // Handle the InterruptedException if needed
            }

        } else {
        Thread currentThread = Thread.currentThread();
        logger.info("Thread ID: " + currentThread.getId());
        String topic = getTopic(message);
        logger.info("Received message: " + message +"   topic : " +topic);
        kafkaMessageProducer.sendMessageToKafka(topic, "default-key", message);
        }

    }

    private String getTopic(String message) throws IOException, SAXException {
        if(isJSONValid(message))
              return   "JSON" ;
            else if(isXmlFormat(message))
                return  "XML";
       return "trash";
    }

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
    public boolean isXmlFormat(String text) {

        return text.trim().startsWith("<") && text.trim().endsWith(">");

    }
}
