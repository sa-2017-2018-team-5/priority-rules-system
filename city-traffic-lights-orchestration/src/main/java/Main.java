import listener.IncomingCarConsumer;
import util.EventEmitter;
import util.EventListener;

import java.io.IOException;

public class Main {
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
