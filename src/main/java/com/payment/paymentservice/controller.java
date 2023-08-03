package com.payment.paymentservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a Spring RestController responsible for handling incoming HTTP requests related to payment services.
 * It integrates with IBM MQ and Kafka for sending and receiving messages.
 *
 * The class exposes two endpoints: "/send" and "/rout-message".
 *
 * - "/send": This endpoint receives a message as a JSON or XML string in the request body and sends it to an IBM MQ queue
 *            named "DEV.QUEUE.1". The sent message and destination name are logged using Log4j.
 *
 * - "/rout-message": This endpoint receives a message from the IBM MQ queue "DEV.QUEUE.1", determines its format (JSON or XML)
 *                    based on its content, and then sends the message to a Kafka topic accordingly. The received message and
 *                    source name are logged using Log4j.
 */
@RestController
@EnableJms
public class controller {
    private final JmsTemplate jmsTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger logger =  LoggerFactory.getLogger(controller.class);

    // Instance variables for JmsTemplate and KafkaTemplate, injected via constructor

    /**
     * Creates a new instance of the controller class with JmsTemplate and KafkaTemplate as dependencies.
     *
     * @param jmsTemplate    The JmsTemplate to interact with IBM MQ.
     * @param kafkaTemplate  The KafkaTemplate to produce messages to Kafka topics.
     */
    @Autowired
    public controller(JmsTemplate jmsTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * This endpoint receives a message as a JSON or XML string in the request body and sends it to an IBM MQ queue
     * named "DEV.QUEUE.1". The sent message and destination name are logged using Log4j.
     *
     * @param message The JSON or XML message received in the request body.
     * @return Returns "OK" as the response after successfully sending the message to IBM MQ.
     */
    @PostMapping("send")
    String send(@RequestBody String message) {
        jmsTemplate.convertAndSend("DEV.QUEUE.1", message);
        logger.info("DestinationName :  DEV.QUEUE.1  \n  message: " + message);
        return "OK";
    }


}
