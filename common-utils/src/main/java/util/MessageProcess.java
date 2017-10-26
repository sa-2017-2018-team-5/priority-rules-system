package util;

import message.TrafficMessage;
import org.json.JSONObject;

public interface MessageProcess {

    boolean isCorrectID(String id, JSONObject object);

    TrafficMessage getMessage(JSONObject object);
}
