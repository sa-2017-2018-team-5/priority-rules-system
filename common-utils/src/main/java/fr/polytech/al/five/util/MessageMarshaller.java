package fr.polytech.al.five.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.al.five.message.CarDetection;
import fr.polytech.al.five.message.TrafficLightCommand;
import fr.polytech.al.five.message.TrafficMessage;

import java.util.Map;

public class MessageMarshaller {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String construct(TrafficMessage message) throws JsonProcessingException {
        return mapper.writeValueAsString(message);
    }
    public static String construct(TrafficLightCommand command) throws JsonProcessingException {
        return mapper.writeValueAsString(command);
    }
    public static String construct(CarDetection detection) throws JsonProcessingException {
        return mapper.writeValueAsString(detection);
    }
}
