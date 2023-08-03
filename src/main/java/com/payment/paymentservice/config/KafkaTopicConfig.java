package com.payment.paymentservice.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

    @Configuration
    public class KafkaTopicConfig {



        @Bean
        public NewTopic createXmlTopic() {
            return TopicBuilder.name("XML").partitions(5)
                    .build();
        }

        @Bean
        public NewTopic createJsonTopic() {
            return TopicBuilder.name("JSON").partitions(5)
                    .build();
        }
        @Bean
        public NewTopic createTrashTopic() {
            return TopicBuilder.name("trash").partitions(5)
                    .build();
        }
    }