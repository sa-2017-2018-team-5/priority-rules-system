package fr.polytech.al.five.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import fr.polytech.al.five.message.TrafficLightCommand;
import fr.polytech.al.five.message.TrafficLightInfo;
import fr.polytech.al.five.runner.TrafficLightRunner;

import java.io.IOException;
import java.util.Objects;

public class TrafficLightRoutesConsumer extends DefaultConsumer {

    private ObjectMapper mapper = new ObjectMapper();

    public TrafficLightRoutesConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        String message = new String(body, "UTF-8");

        TrafficLightCommand trafficLightCommand = mapper.readValue(message, TrafficLightCommand.class);

        TrafficLightInfo trafficLightInfo = trafficLightCommand.getTrafficLightInfo();

        if (Objects.equals(TrafficLightRunner.ID, trafficLightInfo.getId())) {
            System.out.println("Trafic Light set to " + trafficLightCommand.getColour());
        }
    }
}
