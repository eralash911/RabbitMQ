package org.example.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class SimpleSenderApp {
    private  final static String QUEUE_NAME = "Hola";
    private  final static String EXCHANGER_NAME = "Hola_EX";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()){
            channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGER_NAME, "java");

            String msq = "Hello World";
            channel.basicPublish(EXCHANGER_NAME, "java", null, msq.getBytes(StandardCharsets.UTF_8));
            System.out.println("XXX Sent  " + msq + "-----------");
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
