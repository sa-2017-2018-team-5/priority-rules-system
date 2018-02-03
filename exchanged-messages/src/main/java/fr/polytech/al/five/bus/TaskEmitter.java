package fr.polytech.al.five.bus;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import fr.polytech.al.five.messages.Message;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TaskEmitter<T extends Message> extends BusUser {
    public TaskEmitter(BusInformation busInformation) {
        super(busInformation);
    }

    public void send(Message message, BusChannel to) throws IOException, TimeoutException {
        try (Connection connection = getConnectionFactory().newConnection()) {
            try (Channel channel = connection.createChannel()) {
                channel.queueDeclare(to.toString(), true, false, false, null);
                channel.basicPublish("", to.toString(),
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        SerializationUtils.serialize(message));
            }
        }
    }
}
