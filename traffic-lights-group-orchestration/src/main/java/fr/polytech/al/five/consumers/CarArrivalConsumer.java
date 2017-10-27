package fr.polytech.al.five.consumers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import fr.polytech.al.five.message.CarDetection;
import fr.polytech.al.five.message.TrafficLightColour;
import fr.polytech.al.five.message.TrafficLightCommand;
import fr.polytech.al.five.message.TrafficLightInfo;
import fr.polytech.al.five.util.EventEmitter;
import fr.polytech.al.five.util.MessageMarshaller;
import fr.polytech.al.five.util.MessageUnmarshaller;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarArrivalConsumer extends DefaultConsumer{

    private final static Logger logger = Logger.getLogger(CarArrivalConsumer.class);

    private final static List<Integer> TRAFFIC_ID = new ArrayList<Integer>(){{
        add(1111);
        add(2222);
        add(3333);
    }};

    public CarArrivalConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        CarDetection carDetection = MessageUnmarshaller.getCarDetection(new String(body,"UTF-8"));

        logger.info("Received : " + new String(body, "UTF-8"));
        if (TRAFFIC_ID.contains(carDetection.getTrafficLightInfo().getId())) {
            switch (carDetection.getCarPosition()) {
                case SEEN:
                    handleSeenCar(carDetection);
                case PASSED:
                    handlePassedCar(carDetection);
                default:
                    break;
            }
        }
    }

    private void handleSeenCar(CarDetection carDetection){
        EventEmitter emitter = new EventEmitter("TLActivity");
        List<TrafficLightCommand> commands = new ArrayList<>();
        TRAFFIC_ID.forEach(id->{
            TrafficLightCommand tl = new TrafficLightCommand();
            tl.setTrafficLightInfo(new TrafficLightInfo(id));
            tl.setColour(id.equals(carDetection.getTrafficLightInfo().getId())?
                    TrafficLightColour.GREEN: TrafficLightColour.RED);
            commands.add(tl);
        });

        commands.forEach(command -> {
            try {
                logger.info("sending : " + MessageMarshaller.construct(command));
                emitter.publish(MessageMarshaller.construct(command).getBytes("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        emitter.close();
    }

    private void handlePassedCar(CarDetection carDetection){

    }

}
