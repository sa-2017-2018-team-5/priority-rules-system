package engine;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ConstructMessage {

    public List<JSONObject> construct(Set<String> object);
}
