package fr.polytech.al.five.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.al.five.message.CarDetection;
import fr.polytech.al.five.message.TrafficLightCommand;
import fr.polytech.al.five.message.TrafficMessage;
import org.json.JSONObject;

import java.io.IOException;

public class MessageUnmarshaller {

    private static ObjectMapper mapper = new ObjectMapper();

    public static TrafficMessage getTrafficMessage(String message) throws IOException {
       return mapper.readValue(message, TrafficMessage.class);
    }

    CarDetection getCarDetection(String message) throws IOException {
        return mapper.readValue(message, CarDetection.class);
    }

    TrafficLightCommand getTrafficLightCommand(String message) throws IOException {
        return mapper.readValue(message, TrafficLightCommand.class);
    }
}
