/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.avro;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import org.apache.avro.generic.GenericRecord;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;
/**
 *
 * @author shivaraj
 */
public class AvroConsumer {
    public static void main(String args[]){
        Properties props = new Properties();

props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.43.83:9092,192.168.43.87:9092,192.168.43.84:9092");
props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");


props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
props.put("schema.registry.url", "http://192.168.43.83:8081");

props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

String topic = "second-example-dept";
final KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
consumer.subscribe(Arrays.asList("second-example-dept","second-example-employee"));

try {
  while (true) {
    ConsumerRecords<String, String> records = consumer.poll(100);
    for (ConsumerRecord<String, String> record : records) {
      System.out.printf("topic = %s offset = %d, key = %s, value = %s \n",record.topic(), record.offset(), record.key(), record.value());
    }
  }
} finally {
  consumer.close();
}
    }
}
