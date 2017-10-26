package fr.polytech.al.five.listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import fr.polytech.al.five.controller.TrafficLightController;
import fr.polytech.al.five.model.TrafficLightColour;

import java.io.IOException;

public class EventConsumer extends DefaultConsumer{

    private TrafficLightController controller;

    public EventConsumer(Channel channel) {
        super(channel);
        this.controller = new TrafficLightController();
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        // We assume at the moment that the message receive
        // contains only one word from the following RED, GREEN and  YELLOW
        controller.switchColour(TrafficLightColour.valueOf(new String(body, "UTF-8")));

    }

}
