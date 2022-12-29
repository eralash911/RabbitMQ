package org.example.consumer;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class DoubleReceiver {

    private final static String EXC_NAME = "Double";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXC_NAME, BuiltinExchangeType.DIRECT);

        String queueName = channel.queueDeclare().getQueue();
        System.out.println("My name is" + queueName);

        channel.queueBind(queueName, EXC_NAME, "php");
        channel.queueBind(queueName, EXC_NAME, "java");

        System.out.println("Waiting msg");


        DeliverCallback callback = (consumerTag, delivery) ->{
            String  msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(Thread.currentThread().getName() + "[X] Received " + msg + ".....");
            System.out.println(Thread.currentThread().getName());
        };
        channel.basicConsume(queueName, true, callback, consumerTag ->{});
    }
}
