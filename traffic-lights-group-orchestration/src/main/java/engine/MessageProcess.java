package engine;

import org.json.JSONObject;

public interface MessageProcess {

    boolean isCorrectID(String id, JSONObject object);

    JSONObject getCar(JSONObject object);
}
