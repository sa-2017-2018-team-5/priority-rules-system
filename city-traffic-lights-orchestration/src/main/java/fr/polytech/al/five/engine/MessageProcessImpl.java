package fr.polytech.al.five.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.al.five.message.TrafficMessage;
import org.json.JSONObject;
import fr.polytech.al.five.util.MessageProcess;

import java.io.IOException;


public class MessageProcessImpl implements MessageProcess{

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean isCorrectID(String id, JSONObject object) {
        return object.get("id").equals(id);
    }

    @Override
    public TrafficMessage getMessage(JSONObject object) {
        try {
            return mapper.readValue(object.get("message").toString(),TrafficMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
