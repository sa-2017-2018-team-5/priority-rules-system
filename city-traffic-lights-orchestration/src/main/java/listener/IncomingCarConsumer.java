package listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import stubs.route.Route;
import util.EventEmitter;
import engine.ConstructMessageImpl;
import engine.ProcessMessageImpl;
import org.json.JSONObject;
import util.TrafficLightGroupLoader;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        JSONObject jsonObject = new JSONObject(new String(body,"UTF-8"));

        if (processMessage.isCorrectID(ID,jsonObject)){
            System.out.println("received : " + jsonObject.toString());
            Route route = processMessage.getRoute(jsonObject);
            // Associate each traffic lights to its Group
            Set<String> groups =
                    TrafficLightGroupLoader.findGroup(processMessage.getLightsID(route));

            // Construct th reply message
            List<JSONObject> result = constructMessage.construct(groups);

            result.forEach((reply)->{
                reply.put("car",jsonObject.get("car"));
                reply.put("route",jsonObject.get("route"));
                System.out.println("replying :  " + reply.toString());
                try {
                    emitter.publish(reply.toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
