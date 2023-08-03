package com.payment.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class MqSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqSpringApplication.class, args);
    }

}