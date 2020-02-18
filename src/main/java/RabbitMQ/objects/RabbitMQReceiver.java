package RabbitMQ.objects;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class RabbitMQReceiver {

    private String QUEUE_NAME;
    private ConnectionFactory FACTORY;
    private Connection CONNECTION;
    private Channel CHANNEL;
    private String CONSUMER_TAG;

    private ArrayList<String> MESSAGES;

    public RabbitMQReceiver(String QUEUE_NAME) {
        this.QUEUE_NAME = QUEUE_NAME;
        FACTORY = new ConnectionFactory();
        FACTORY.setHost("localhost");

        MESSAGES = new ArrayList<>();
    }

    public void startReceiving() throws IOException, TimeoutException {

        CONNECTION = FACTORY.newConnection();
        CHANNEL = CONNECTION.createChannel();

        CHANNEL.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            MESSAGES.add(message);
        };

        CONSUMER_TAG = CHANNEL.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }

    public String getMessage() {
        if (MESSAGES.size() > 0)
            return MESSAGES.remove(0);
        else
            return "No messages";
    }

    public void stopReceiving() throws IOException, TimeoutException {
        CHANNEL.basicCancel(CONSUMER_TAG);
        CHANNEL.close();
        CONNECTION.close();
    }
}
