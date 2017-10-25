package engine;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProcessMessageImpl implements ProcessMessage{

    @Override
    public boolean isCorrectID(String id, JSONObject object) {
        System.out.println(object.toString());
        return object.get("id").equals(id);
    }

    @Override
    public JSONObject getRoute(JSONObject object) {
        return object.getJSONObject("route");
    }

    @Override
    public JSONObject getCar(JSONObject object) {
        return object.getJSONObject("car");
    }

    @Override
    public JSONArray getEncounteredLights(JSONObject object){
        return this.getRoute(object).getJSONArray("encounteredLights");
    }

    @Override
    public List<String> getLightsID(JSONObject object) {
        List<String> trafficID = new ArrayList<>();
        JSONArray trafficLights = this.getEncounteredLights(object);
        for(int i = 0; i < trafficLights.length(); i++){
            trafficID.add(trafficLights.getJSONObject(i).getString("id"));
        }
        return trafficID;
    }
}
