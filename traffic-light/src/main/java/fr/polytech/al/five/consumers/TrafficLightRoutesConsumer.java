package fr.polytech.al.five.consumers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import fr.polytech.al.five.message.TrafficLightInfo;
import fr.polytech.al.five.message.TrafficMessage;
import fr.polytech.al.five.runner.TrafficLightRunner;
import fr.polytech.al.five.util.MessageUnmarshaller;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

public class TrafficLightRoutesConsumer extends DefaultConsumer {

    private final static Logger logger = Logger.getLogger(TrafficLightRoutesConsumer.class);

    public TrafficLightRoutesConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        TrafficMessage trafficMessage = MessageUnmarshaller.getTrafficMessage(new String(body, "UTF-8"));

        for (TrafficLightInfo trafficLightInfo : trafficMessage.getTrafficLights()) {
            if (Objects.equals(TrafficLightRunner.ID, trafficLightInfo.getId())) {
                logger.info("Waiting for car " + trafficMessage.getCar().getId());
                TrafficLightRunner.cars.add(trafficMessage.getCar());
            }
        }

    }

}
