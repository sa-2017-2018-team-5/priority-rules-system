package fr.polytech.al.five.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.al.five.message.CarDetection;
import fr.polytech.al.five.message.TrafficLightCommand;
import fr.polytech.al.five.message.TrafficMessage;
import org.apache.log4j.Logger;

import java.io.IOException;


public class MessageUnmarshaller {

    private final static Logger logger = Logger.getLogger(MessageUnmarshaller.class);

    private final static ObjectMapper mapper = new ObjectMapper();

    public static TrafficMessage getTrafficMessage(String message){
        try {
            return mapper.readValue(message, TrafficMessage.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return new TrafficMessage();
    }

    public static CarDetection getCarDetection(String message) {
        try {
            return mapper.readValue(message, CarDetection.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return new CarDetection();
    }

    public static TrafficLightCommand getTrafficLightCommand(String message){
        try {
            return mapper.readValue(message, TrafficLightCommand.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return new TrafficLightCommand();
    }
}
