package RabbitMQ;

import RabbitMQ.objects.RabbitMQReceiver;
import RabbitMQ.objects.RabbitMQSender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQTests {

    private RabbitMQSender rabbitMQSender;
    private RabbitMQReceiver rabbitMQReceiver;

    @BeforeEach
    public void beforeEach() {
        rabbitMQSender = new RabbitMQSender("My_First_Queue");
        rabbitMQReceiver = new RabbitMQReceiver("My_First_Queue");
        try {
            rabbitMQReceiver.startReceiving();
        } catch (IOException | TimeoutException e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterEach
    public void afterEach() {
        try {
            rabbitMQReceiver.stopReceiving();
        } catch (IOException | TimeoutException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void sendOneMessage() {
        try {
            String originMessage = "This is a test";
            String receivedMessage;

            rabbitMQSender.sendMessage(originMessage);

            Thread.sleep(100);

            receivedMessage = rabbitMQReceiver.getMessage();

            assertEquals(originMessage, receivedMessage);

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
            String receivedMessage;

            rabbitMQSender.sendMessage(originMessage1);
            rabbitMQSender.sendMessage(originMessage2);
            rabbitMQSender.sendMessage(originMessage3);

            Thread.sleep(100);

            receivedMessage = rabbitMQReceiver.getMessage();
            assertEquals(originMessage1, receivedMessage);

            receivedMessage = rabbitMQReceiver.getMessage();
            assertEquals(originMessage2, receivedMessage);

            receivedMessage = rabbitMQReceiver.getMessage();
            assertEquals(originMessage3, receivedMessage);

        } catch (IOException | TimeoutException | InterruptedException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void receiveWhenNoMessages() {

        String receivedMessage;
        receivedMessage = rabbitMQReceiver.getMessage();

        assertEquals("No messages", receivedMessage);
    }
}
