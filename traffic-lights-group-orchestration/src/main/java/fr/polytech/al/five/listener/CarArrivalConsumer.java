package fr.polytech.al.five.listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import fr.polytech.al.five.message.CarInfo;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CarArrivalConsumer extends DefaultConsumer{

    final static Logger logger = Logger.getLogger(CarArrivalConsumer.class);

    private final static String ID = "Group-40096";
    private Set<CarInfo> myCars;

    public CarArrivalConsumer(Channel channel, String exchange) {
        super(channel);
        this.myCars = new HashSet<>();
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {



        JSONObject jsonObject = new JSONObject(new String(body, "UTF-8"));

//
//        if (process.isCorrectID(ID,jsonObject)){
//            logger.info("Received message ["+consumerTag+"] : " + jsonObject.toString());
//
//            myCars.add(process.getMessage(jsonObject).getCar());
//
//            Set<Integer> carsID = new HashSet<>();
//            myCars.forEach(e -> { carsID.add(e.getId());});
//            logger.info("Waiting for vehicles : " + carsID);
//        }
    }


}
