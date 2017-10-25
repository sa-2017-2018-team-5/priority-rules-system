package engine;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface ProcessMessage {

    boolean isCorrectID(String id, JSONObject object);

    JSONObject getRoute(JSONObject object);

    JSONObject getCar(JSONObject object);

    JSONArray getEncounteredLights(JSONObject object);

    List<String> getLightsID(JSONObject object);
}
