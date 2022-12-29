package org.example.consumer;

import com.rabbitmq.client.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TaskReceiverApp {

    private final static String TASK_NAME = "task";
    private final static String TASK_NAME_EX = "task_exchanger";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_NAME, true, false, false, null);
        channel.queueBind(TASK_NAME, TASK_NAME_EX, "");
        System.out.println("waiting msg");

            channel.basicQos(3);

        DeliverCallback deliverCallback = (consumerTag, delivery)->{
            String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("XXXXX " + msg);
//            if (1<10){
//                throw new RuntimeException();
//            }
            doWork(msg);
            System.out.println("done");

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

        };
        channel.basicConsume(TASK_NAME,false, deliverCallback, consumerTag ->{

                });

//        String queueName = channel.queueDeclare().getQueue();
//        System.out.println("My name is" + queueName);
    }
    private static void doWork(String msg){
        for (char ch :
                msg.toCharArray()) {
            if (ch == '.') {
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException _ignored){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
