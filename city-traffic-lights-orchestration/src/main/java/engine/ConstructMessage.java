package engine;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface ConstructMessage {

    List<JSONObject> construct(Map<String,List<String>> object);
}
