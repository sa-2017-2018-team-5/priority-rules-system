package fr.polytech.al.five.bus;

import com.rabbitmq.client.*;
import fr.polytech.al.five.messages.Message;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class TaskConsumer<T extends Message> extends BusUser {
    private Connection connection;
    private Channel channel;

    public TaskConsumer(BusInformation busInformation) {
        super(busInformation);
    }

    public void makeConsume(BusChannel on, Consumer<T> action) throws IOException, TimeoutException {
        // Setup the bus connection if not already done.
        setupBusConnection();
        channel.queueDeclare(on.toString(), true, false, false, null);

        // consumes only 1 message at a time
        channel.basicQos(1);

        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                T message = SerializationUtils.deserialize(body);

                action.accept(message);

                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(on.toString(), false, consumer);
    }

    private void setupBusConnection() throws IOException, TimeoutException {
        if (connection == null) {
            connection = getConnectionFactory().newConnection();
        }

        if (channel == null) {
            channel = connection.createChannel();
        }
    }
}
