package engine;

import org.json.JSONObject;

public class MessageProcessImpl implements MessageProcess {

    @Override
    public boolean isCorrectID(String id, JSONObject object) {
        return object.getString("id").equals(id);
    }

    @Override
    public JSONObject getCar(JSONObject object) {
        return object.getJSONObject("car");
    }
}
