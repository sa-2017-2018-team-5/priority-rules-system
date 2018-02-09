package fr.polytech.al.five.tlws.services;

import fr.polytech.al.five.messages.contents.CarAction;
import fr.polytech.al.five.tlws.components.BusCommunication;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/observations")
@Produces(MediaType.APPLICATION_JSON)
public class TrafficLightsService {

    @EJB
    private BusCommunication busCommunication;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response action(final String stringBody) {
        JSONObject body = new JSONObject(stringBody);

        JSONObject content = body.getJSONObject("content");

        String status = content.getString("status");
        int carId = content.getInt("car");
        int trafficLightId = content.getInt("trafficLight");

        if ("SEEN".equals(status)) {
            return seenCar(trafficLightId, carId);
        } else if ("PASSED".equals(status)) {
            return passedCar(trafficLightId, carId);
        }

        // Sends an "Not implemented" error.
        return Response.status(501).build();
    }

    private Response seenCar(final int trafficLightId, final int carId) {
        busCommunication.sendCarStatus(trafficLightId, carId, CarAction.SEEN);
        return Response.ok().build();
    }

    private Response passedCar(final int trafficLightId, final int carId) {
        busCommunication.sendCarStatus(trafficLightId, carId, CarAction.PASSED);
        return Response.ok().build();
    }
}
