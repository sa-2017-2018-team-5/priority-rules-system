package fr.polytech.al.five.bus;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import fr.polytech.al.five.messages.Message;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PubSubEmitter extends BusUser {

    public PubSubEmitter(BusInformation busInformation) {
        super(busInformation);
    }

    public void send(Message message, BusChannel to) throws IOException, TimeoutException {
        try (Connection connection = getConnectionFactory().newConnection()) {
            try (Channel channel = connection.createChannel()) {
                channel.exchangeDeclare(to.toString(), "fanout");

                channel.basicPublish(to.toString(), "", null,
                        SerializationUtils.serialize(message));
            }
        }
    }
}
