package fr.polytech.al.five.listener;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import fr.polytech.al.five.message.TrafficLightInfo;
import fr.polytech.al.five.message.TrafficMessage;
import fr.polytech.al.five.runner.TrafficLightRunner;


import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class TrafficLightConsumer extends DefaultConsumer {

    private ObjectMapper mapper = new ObjectMapper();

    public TrafficLightConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        String message = new String(body, "UTF-8");
        System.out.println("Receive: " + message);

        TrafficMessage trafficMessage = mapper.readValue(message, TrafficMessage.class);

        List<TrafficLightInfo> trafficLightInfos = trafficMessage.getTrafficLights();

        for (TrafficLightInfo trafficLightInfo : trafficLightInfos) {
            if (Objects.equals(TrafficLightRunner.ID, trafficLightInfo.getId())) {
                System.out.println("Waiting for car " + trafficMessage.getCar().getId());
                TrafficLightRunner.cars.add(trafficMessage.getCar());
            }
        }

    }

}
