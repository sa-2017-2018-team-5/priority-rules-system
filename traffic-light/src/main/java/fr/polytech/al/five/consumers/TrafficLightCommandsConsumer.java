package fr.polytech.al.five.consumers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import fr.polytech.al.five.message.TrafficLightCommand;
import fr.polytech.al.five.runner.TrafficLightRunner;
import fr.polytech.al.five.util.MessageUnmarshaller;
import org.apache.log4j.Logger;

import java.io.IOException;

public class TrafficLightCommandsConsumer extends DefaultConsumer {

    private final static Logger logger = Logger.getLogger(TrafficLightCommandsConsumer.class);

    public TrafficLightCommandsConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        TrafficLightCommand trafficLightCommand = MessageUnmarshaller.getTrafficLightCommand(new String(body, "UTF-8"));
        if (TrafficLightRunner.ID.equals(trafficLightCommand.getTrafficLightInfo().getId())) {
            logger.info("Trafic Light set to " + trafficLightCommand.getColour());
        }
    }
}
