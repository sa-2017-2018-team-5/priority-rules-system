package fr.polytech.al.five.components;

import fr.polytech.al.five.TrafficSupervisor;
import fr.polytech.al.five.entities.TrafficInformation;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONObject;

import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import java.net.ConnectException;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@Stateless
public class TrafficSupervisionClient implements TrafficSupervisor {

    @Override
    public TrafficInformation getTraffic(String road_id) {
        // TODO: Connect to the external service.
        int port = 9090;
        String host = "localhost";
        String serviceName = "/traffic-api/road/section=";

        WebClient webClient = WebClient.create("http://" + host + ":" + port + serviceName + road_id)
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .query("section", "request");
        String rawResult;

        try {
            rawResult = webClient.get(String.class);
        } catch (Exception e){
            return TrafficInformation.fromName("unknown");
        }

        JSONObject jsonResult = new JSONObject(rawResult);
        return TrafficInformation.fromName(jsonResult.getString("status").toLowerCase());
    }
}
