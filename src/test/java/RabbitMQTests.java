import objects.RabbitMQReciever;
import objects.RabbitMQSender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQTests {

    private RabbitMQSender rabbitMQSender;
    private RabbitMQReciever rabbitMQReciever;

    @BeforeEach
    public void beforeEach() {
        rabbitMQSender = new RabbitMQSender("My_First_Queue");
        rabbitMQReciever = new RabbitMQReciever("My_First_Queue");
        try {
            rabbitMQReciever.startRecieving();
        } catch (IOException | TimeoutException e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterEach
    public void afterEach() {


        try {
            rabbitMQReciever.stopRecieving();
        } catch (IOException | TimeoutException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void sendOneMessage() {
        try {
            String originMessage = "This is a test";
            String recievedMessage;

            rabbitMQSender.sendMessage(originMessage);

            Thread.sleep(100);

            recievedMessage = rabbitMQReciever.getMessage();

            assertEquals(originMessage, recievedMessage);

        } catch (IOException | TimeoutException | InterruptedException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void sendMultipleMessages() {
        try {
            String originMessage1 = "This is a test";
            String originMessage2 = "This is also test";
            String originMessage3 = "This is the third test";
            String recievedMessage;

            rabbitMQSender.sendMessage(originMessage1);
            rabbitMQSender.sendMessage(originMessage2);
            rabbitMQSender.sendMessage(originMessage3);

            Thread.sleep(100);

            recievedMessage = rabbitMQReciever.getMessage();
            assertEquals(originMessage1, recievedMessage);

            recievedMessage = rabbitMQReciever.getMessage();
            assertEquals(originMessage2, recievedMessage);

            recievedMessage = rabbitMQReciever.getMessage();
            assertEquals(originMessage3, recievedMessage);

        } catch (IOException | TimeoutException | InterruptedException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}
