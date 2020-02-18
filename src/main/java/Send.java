import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import objects.RabbitMQReciever;
import objects.RabbitMQSender;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Send {

    private final static String QUEUE_NAME = "My_First_Queue";

    public static void main(String[] argv) {

        RabbitMQSender rabbitMQSender = new RabbitMQSender("My_First_Queue");
        RabbitMQReciever rabbitMQReciever = new RabbitMQReciever("My_First_Queue");

        try {
            rabbitMQReciever.startRecieving();

            Thread.sleep(200);

            rabbitMQSender.sendMessage("This is cool");

            Thread.sleep(200);

            rabbitMQSender.sendMessage("This is also cool");

            Thread.sleep(200);

            rabbitMQReciever.stopRecieving();

        } catch (IOException | TimeoutException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.exit(0);
    }
}