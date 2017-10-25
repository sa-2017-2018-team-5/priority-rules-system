package engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import stubs.route.Car;
import stubs.route.Route;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ProcessMessageImpl implements ProcessMessage{

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean isCorrectID(String id, JSONObject object) {
        System.out.println(object.toString());

        return object.get("id").equals(id);
    }

    @Override
    public Car getCar(JSONObject object) {
        try {
            return  mapper.readValue(object.get("car").toString(), Car.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Route getRoute(JSONObject object) {
        try {
            return  mapper.readValue(object.get("route").toString(),Route.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getLightsID(Route route) {
        List<String> lightsID = new ArrayList<>();

        route.getEncounteredLights().forEach(light -> lightsID.add(light.getId().toString()));

        return lightsID;
    }
//
//    @Override
//    public JSONObject getRoute(JSONObject object) {
//        return object.getJSONObject("route");
//    }
//
//    @Override
//    public JSONObject getCar(JSONObject object) {
//        return object.getJSONObject("car");
//    }
//
//    @Override
//    public JSONArray getEncounteredLights(JSONObject object){
//        return this.getRoute(object).getJSONArray("encounteredLights");
//    }
//
//    @Override
//    public List<String> getLightsID(JSONObject object) {
//        List<String> trafficID = new ArrayList<>();
//        JSONArray trafficLights = this.getEncounteredLights(object);
//        for(int i = 0; i < trafficLights.length(); i++){
//            trafficID.add(trafficLights.getJSONObject(i).getString("id"));
//        }
//        return trafficID;
//    }
}
