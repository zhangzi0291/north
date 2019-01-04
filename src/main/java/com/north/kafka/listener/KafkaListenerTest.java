package com.north.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.HashMap;
import java.util.Map;

public class KafkaListenerTest {

    @KafkaListener(topicPattern = "test*", errorHandler = "kafkaErrorHandler")
    public void listenerTest(ConsumerRecord<?, ?> record) {
        System.out.println(record);
        System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());

//        throw new Exception("kafka erro test");

    }
}