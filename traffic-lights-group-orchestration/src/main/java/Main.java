import listener.CarArrivalConsumer;
import util.EventListener;

import java.io.IOException;

public class Main {

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
