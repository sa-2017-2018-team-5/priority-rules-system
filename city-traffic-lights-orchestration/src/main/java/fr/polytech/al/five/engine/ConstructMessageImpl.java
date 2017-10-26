package fr.polytech.al.five.engine;

import org.json.JSONObject;
import util.ConstructMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConstructMessageImpl implements ConstructMessage {

    @Override
    public List<JSONObject> construct(Set<String> object) {
        List<JSONObject> result = new ArrayList<>();
        object.forEach(item->{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",item);
            result.add(jsonObject);
        });
        return result;
    }
}
