package fr.polytech.al.five.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.al.five.message.CarDetection;
import fr.polytech.al.five.message.TrafficLightCommand;
import fr.polytech.al.five.message.TrafficMessage;
import org.apache.log4j.Logger;

public class MessageMarshaller {

    private final static Logger logger = Logger.getLogger(MessageMarshaller.class);

    private final static ObjectMapper mapper = new ObjectMapper();

    public static String construct(TrafficMessage message) {
        try {
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return "";
    }
    public static String construct(TrafficLightCommand command) {
        try {
            return mapper.writeValueAsString(command);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return "";
    }
    public static String construct(CarDetection detection) {
        try {
            return mapper.writeValueAsString(detection);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return "";
    }
}
