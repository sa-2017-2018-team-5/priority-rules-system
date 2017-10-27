import fr.polytech.al.five.util.EventListener;
import fr.polytech.al.five.listener.TrafficLightConsumer;
import java.io.IOException;

public class Main {

        public static void main(String[] args){

        EventListener listener = new EventListener("TrafficExchange");
        TrafficLightConsumer consumer = new TrafficLightConsumer(listener.getChannel());

        try {
            listener.bind();
            listener.getChannel().basicConsume(listener.getQueueName(),true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
       }
   }
}
