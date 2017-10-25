package engine;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConstructMessageImpl implements ConstructMessage {

    @Override
    public List<JSONObject> construct(Map<String, List<String>> object) {
        List<JSONObject> result = new ArrayList<>();
        object.forEach((k,v) ->{
            JSONObject item = new JSONObject();
            JSONArray array = new JSONArray();
            item.put("id", k);
            v.forEach(e ->{
                JSONObject light = new JSONObject();
                light.put("trafficID", e);
                array.put(light);
            });
            item.put("trafficLights",array);
            result.add(item);
        });
        return result;
    }
}
