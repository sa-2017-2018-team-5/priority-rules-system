package engine;

import org.json.JSONObject;
import stubs.route.Car;

public interface MessageProcess {

    boolean isCorrectID(String id, JSONObject object);

    Car getCar(JSONObject object);
}
