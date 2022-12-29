package org.example.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class DoubleDirectSenderApp {

    private final static String EXCHANGE_NAME ="Double";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()){
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            channel.basicPublish(EXCHANGE_NAME, "java", null, "java msg".getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, "c++", null, "c++ msg".getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, "php", null, "php msg".getBytes(StandardCharsets.UTF_8));
            System.out.println("Ok");
        }
    }
}
