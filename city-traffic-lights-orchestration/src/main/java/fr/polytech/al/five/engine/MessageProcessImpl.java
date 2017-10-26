package fr.polytech.al.five.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import message.TrafficMessage;
import org.json.JSONObject;
import util.MessageProcess;

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
