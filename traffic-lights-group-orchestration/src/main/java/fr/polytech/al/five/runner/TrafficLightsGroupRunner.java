package fr.polytech.al.five.runner;

import fr.polytech.al.five.util.EventListener;
import fr.polytech.al.five.consumers.CarArrivalConsumer;

import java.io.IOException;

public class TrafficLightsGroupRunner {

    public static void main(String[] args){

        EventListener listener = new EventListener("TLActivity");
        CarArrivalConsumer consumer = new CarArrivalConsumer(listener.getChannel(), "TLActivity");

        try {
            listener.bind();
            listener.getChannel().basicConsume(listener.getQueueName(),true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
