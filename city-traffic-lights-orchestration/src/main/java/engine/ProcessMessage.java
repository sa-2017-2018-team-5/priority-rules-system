package engine;

import org.json.JSONArray;
import org.json.JSONObject;
import stubs.route.Car;
import stubs.route.Route;

import java.util.List;

public interface ProcessMessage {

    boolean isCorrectID(String id, JSONObject object);

//    JSONObject getRoute(JSONObject object);
//
//    JSONObject getCar(JSONObject object);
//
//    JSONArray getEncounteredLights(JSONObject object);
//
//    List<String> getLightsID(JSONObject object);

    Car getCar(JSONObject object);

    Route getRoute(JSONObject object);

    List<String> getLightsID(Route route);
}
