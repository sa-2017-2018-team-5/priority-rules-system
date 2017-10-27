package fr.polytech.al.five.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.al.five.message.CarDetection;
import fr.polytech.al.five.message.TrafficLightCommand;
import fr.polytech.al.five.message.TrafficMessage;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MessageMarshaller {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String construct(TrafficMessage message){
        return mapper.convertValue(message, Map.class).toString();
    }
    public static String construct(TrafficLightCommand command){
        return mapper.convertValue(command, Map.class).toString();
    }
    public static String construct(CarDetection detection){
        return mapper.convertValue(detection, Map.class).toString();
    }
}
