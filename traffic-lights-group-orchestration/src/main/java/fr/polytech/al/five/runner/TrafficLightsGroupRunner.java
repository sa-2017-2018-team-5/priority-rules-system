package fr.polytech.al.five.runner;

import util.EventListener;
import fr.polytech.al.five.listener.CarArrivalConsumer;

import java.io.IOException;

public class TrafficLightsGroupRunner {

    public static void main(String[] args){

        EventListener listener = new EventListener("GroupExchange");
        CarArrivalConsumer consumer = new CarArrivalConsumer(listener.getChannel(), "TrafficExchange");

        try {
            listener.bind();
            listener.getChannel().basicConsume(listener.getQueueName(),true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
