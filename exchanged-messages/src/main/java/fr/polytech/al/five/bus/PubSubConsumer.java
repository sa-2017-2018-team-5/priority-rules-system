package fr.polytech.al.five.bus;

import com.rabbitmq.client.*;
import fr.polytech.al.five.messages.Message;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class PubSubConsumer<T extends Message> extends BusUser {

    private Connection connection;
    private Channel channel;

    public PubSubConsumer(BusInformation busInformation) {
        super(busInformation);
    }

    public void makeConsume(BusChannel on, Consumer<T> action) throws IOException, TimeoutException {
        // Setup the bus connection if not already done.
        setupBusConnection();

        channel.exchangeDeclare(on.toString(), "fanout");

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, on.toString(), "");

        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                T message = SerializationUtils.deserialize(body);

                action.accept(message);
            }
        };

        channel.basicConsume(queueName, true, consumer);
    }

    private void setupBusConnection() throws IOException, TimeoutException {
        if (connection == null) {
            connection = getConnectionFactory().newConnection();
        }

        if (channel == null) {
            channel = connection.createChannel();
        }
    }

    public void close() throws IOException, TimeoutException {
        if (channel != null) {
            channel.close();
        }

        if (connection != null) {
            connection.close();
        }
    }
}
