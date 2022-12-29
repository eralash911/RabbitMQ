package org.example.producer;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class TaskSenderProducer {
    private final static String EXCHANGE_NAME = "task";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()){
            String msg = "TASK...............";
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            for (int i = 0; i < 5; i++) {
                channel.basicPublish(EXCHANGE_NAME, "", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes(StandardCharsets.UTF_8));
                System.out.println("XXX msg " + msg + "?????????");
                
            }
        }
    }
}
