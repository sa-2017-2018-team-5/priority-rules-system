import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.al.five.message.CarInfo;
import fr.polytech.al.five.message.TrafficLightInfo;
import fr.polytech.al.five.message.TrafficMessage;
import fr.polytech.al.five.util.EventEmitter;
import fr.polytech.al.five.util.EventListener;
import fr.polytech.al.five.util.MessageMarshaller;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

        public static void main(String[] args){

            EventEmitter emitter = new EventEmitter("Routes");

            CarInfo myCar = new CarInfo();
            myCar.setId(4987);

            List<TrafficLightInfo> route = new ArrayList<>();
            TrafficLightInfo tl1 = new TrafficLightInfo();
            tl1.setId(90964);
            TrafficLightInfo tl2 = new TrafficLightInfo();
            tl2.setId(76855);
            TrafficLightInfo tl3 = new TrafficLightInfo();
            tl3.setId(1111);
            TrafficLightInfo tl4 = new TrafficLightInfo();
            tl4.setId(48947);
            TrafficLightInfo tl5 = new TrafficLightInfo();
            tl5.setId(60521);
            TrafficLightInfo tl6 = new TrafficLightInfo();
            tl6.setId(37349);

            route.add(tl1);
            route.add(tl2);
            route.add(tl3);
            route.add(tl4);
            route.add(tl5);
            route.add(tl6);

            TrafficMessage trafficMessage = new TrafficMessage(
                    myCar,
                    route,
                    new Date()
            );
            String message;
            try {
                message = MessageMarshaller.construct(trafficMessage);

                System.out.println("Sending : "+ message);
                emitter.publish(message.getBytes("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            emitter.close();
   }
}
