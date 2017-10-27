package fr.polytech.al.five.listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import fr.polytech.al.five.message.TrafficMessage;
import org.apache.log4j.Logger;
import fr.polytech.al.five.util.EventEmitter;
import org.json.JSONObject;
import fr.polytech.al.five.util.TrafficLightGroupLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IncomingCarConsumer extends DefaultConsumer{

    final static Logger logger = Logger.getLogger(IncomingCarConsumer.class);

    private static final String ID = "City";

    private EventEmitter emitter;

    public IncomingCarConsumer(Channel channel, String exchange) {
        super(channel);
        this.emitter = new EventEmitter(exchange);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        JSONObject jsonObject = new JSONObject(new String(body,"UTF-8"));
//
//        if (processMessage.isCorrectID(ID,jsonObject)){
//            logger.info("Received message ["+consumerTag+ "] : " + jsonObject.toString());
//
//            TrafficMessage message = processMessage.getMessage(jsonObject);
//            // Associate each traffic lights to its Group
//            List<Integer> trafficID = new ArrayList<>();
//            message.getTrafficLights().forEach(trafficLightInfo -> {
//                trafficID.add(trafficLightInfo.getId());
//            });
//            Set<String> groups =
//                    TrafficLightGroupLoader.findGroup(trafficID);
//
//            // Construct the message
//            List<JSONObject> result = constructMessage.construct(groups);
//
//            result.forEach((reply)->{
//                reply.put("message",jsonObject.get("message"));
//
//                logger.info("Reply message to ["+reply.getString("id")+ "] : " + reply.toString());
//                try {
//                    emitter.publish(reply.toString().getBytes());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
    }

}
