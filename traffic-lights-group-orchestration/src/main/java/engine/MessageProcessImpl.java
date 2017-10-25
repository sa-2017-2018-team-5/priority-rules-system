package engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import stubs.route.Car;

import java.io.IOException;

public class MessageProcessImpl implements MessageProcess {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean isCorrectID(String id, JSONObject object) {
        return object.getString("id").equals(id);
    }

    @Override
    public Car getCar(JSONObject object) {
        try {
            return mapper.readValue(object.get("car").toString(),Car.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
