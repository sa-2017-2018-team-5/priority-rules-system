package fr.polytech.al.five.util;

import fr.polytech.al.five.message.TrafficMessage;
import org.json.JSONObject;

public interface MessageProcess {

    boolean isCorrectID(String id, JSONObject object);

    TrafficMessage getMessage(JSONObject object);
}
