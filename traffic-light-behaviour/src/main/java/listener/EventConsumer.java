package listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import controller.TrafficLightController;
import model.TrafficLightColour;

import java.io.IOException;

public class EventConsumer extends DefaultConsumer{

    private String message;
    private TrafficLightController controller;

    public EventConsumer(Channel channel) {
        super(channel);
        this.controller = new TrafficLightController();
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {
        controller.switchColour(TrafficLightColour.valueOf(new String(body, "UTF-8")));

    }

}
