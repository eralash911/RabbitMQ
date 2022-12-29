package org.example.producer;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class ExchangeSender {
    private  final static String EXCHANGER_NAME = "DirectExchanger";

    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()){


            String msq = "info: some msg /////////// ";

            channel.basicPublish(EXCHANGER_NAME, "php", null, msq.getBytes(StandardCharsets.UTF_8));
            System.out.println("XXX Sent  " + msq + "-----------");
        }

    }
}
