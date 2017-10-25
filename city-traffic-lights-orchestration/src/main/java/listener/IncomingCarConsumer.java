package listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import engine.ConstructMessageImpl;
import engine.ProcessMessageImpl;
import org.json.JSONObject;
import util.EventEmitter;
import util.TrafficLightGroupLoader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class IncomingCarConsumer extends DefaultConsumer{

    private static final String ID = "City";

    private ProcessMessageImpl processMessage  = new ProcessMessageImpl();
    private ConstructMessageImpl constructMessage = new ConstructMessageImpl();
    private EventEmitter emitter;

    public IncomingCarConsumer(Channel channel, String exchange) {
        super(channel);
        this.emitter = new EventEmitter(exchange);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        JSONObject jsonObject = new JSONObject(new String(body, "UTF-8"));

        if (processMessage.isCorrectID(ID,jsonObject)){
            System.out.println("receive : " + jsonObject.toString());

            // Associate each traffic lights to its Group
            Map<String,List<String>> groups =
                    TrafficLightGroupLoader.findGroup(processMessage.getLightsID(jsonObject));

            // Construct th reply message
            List<JSONObject> result = constructMessage.construct(groups);

            result.forEach((reply)->{
                reply.put("car",processMessage.getCar(jsonObject));
                System.out.println("replying :  " + reply.toString());
                try {
                    emitter.publish(reply.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
