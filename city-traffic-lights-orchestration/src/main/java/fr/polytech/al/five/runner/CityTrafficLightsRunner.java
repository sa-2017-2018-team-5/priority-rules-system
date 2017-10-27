package fr.polytech.al.five.runner;

import fr.polytech.al.five.consumers.IncomingCarConsumer;
import fr.polytech.al.five.util.EventListener;

import java.io.IOException;

public class CityTrafficLightsRunner {
    public static void main(String[] args){

        EventListener listener = new EventListener("CityExchange");

        try {
            listener.bind();
            IncomingCarConsumer incomingCarConsumer = new IncomingCarConsumer(listener.getChannel(), "GroupExchange");
            listener.getChannel().basicConsume(listener.getQueueName(), true,incomingCarConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
