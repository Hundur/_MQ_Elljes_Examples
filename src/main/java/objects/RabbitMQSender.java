package objects;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMQSender {

    private String QUEUE_NAME;
    private ConnectionFactory FACTORY;

    public RabbitMQSender(String QUEUE_NAME) {
        this.QUEUE_NAME = QUEUE_NAME;
        FACTORY = new ConnectionFactory();
        FACTORY.setHost("localhost");
    }

    public void sendMessage(String message) throws IOException, TimeoutException {

        Connection connection = FACTORY.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + message + "'");
    }
}
