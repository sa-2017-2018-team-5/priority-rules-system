package fr.polytech.al.five.bus;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import fr.polytech.al.five.messages.Message;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageEmitter extends BusUser {

    public MessageEmitter(BusInformation busInformation) {
        super(busInformation);
    }

    public void send(Message message, BusChannel to) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;

        try {
            connection = getConnectionFactory().newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(to.toString(), "fanout");

            channel.basicPublish(to.toString(), "", null,
                    SerializationUtils.serialize(message));
        } finally {
             if (channel != null) {
                 channel.close();
             }

             if (connection != null) {
                 connection.close();
             }
        }
    }
}
