package fr.polytech.al.five.routes.services;

import fr.polytech.al.five.routes.business.*;
import fr.polytech.al.five.routes.components.PriorityChecker;
import fr.polytech.al.five.routes.components.RouteBuilder;
import fr.polytech.al.five.routes.components.RouteRegisterer;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.Optional;

@Path("/routes")
@Produces(MediaType.APPLICATION_JSON)
public class RoutesWebService {

    @EJB
    private RouteBuilder routeBuilder;

    @EJB
    private PriorityChecker priorityChecker;

    @EJB
    private RouteRegisterer routeRegisterer;

    @POST
    public Response action(final String stringBody) {
        JSONObject body = new JSONObject(stringBody);

        RouteAction requiredAction = RouteAction.valueOf(body.getString("action"));

        JSONObject content = body.getJSONObject("content");

        if (requiredAction == RouteAction.ASK) {
            return askRoute(content);
        }

        // Sends an "Not implemented" error.
        return Response.status(501).build();
    }

    private Response askRoute(JSONObject requestContent) {
        JSONObject carDescription = requestContent.getJSONObject("car");

        Optional<CarType> verifiedCarType = priorityChecker.checkPriority(carDescription.getString("type"));

        if (!verifiedCarType.isPresent()) {
            return Response.status(403).build();
        }

        Car car = new Car(carDescription.getInt("id"),
                parsePosition(carDescription.getJSONObject("currentPosition")),
                verifiedCarType.get());
        Position destination = parsePosition(requestContent.getJSONObject("destination"));

        Route route = routeBuilder.makeRoute(car.getId(), car.getCurrentPosition(), destination, new Date());
        routeRegisterer.sendRoute(car, route);

        JSONObject responseBody = new JSONObject();

        return Response.ok().entity(responseBody.toString()).build();
    }

    private static Position parsePosition(JSONObject document) {
        return new Position(document.getLong("longitude"),
                document.getLong("latitude"));
    }
}
