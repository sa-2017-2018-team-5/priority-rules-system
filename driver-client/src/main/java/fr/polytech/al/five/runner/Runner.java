package fr.polytech.al.five.runner;

import fr.polytech.al.five.remote.ServiceProvider;
import org.apache.log4j.Logger;
import stubs.route.*;

public class Runner {
    
    private static Logger LOGGER = Logger.getLogger(Runner.class);

    public static void main(String... args) {
        LOGGER.info("[x] Asking for a route.");
        LOGGER.info("[x] From: [43.641127, 7.134966]");
        LOGGER.info("[x] To: [43.6155793, 7.071874799999932]\n");

        RouteWebService routeWebService = ServiceProvider.getRouteService();

        Position from = new Position();
        from.setLatitude(43.641127f);
        from.setLongitude(7.134966f);

        Position to = new Position();
        to.setLatitude(43.6155793f);
        to.setLongitude(7.071874799999932f);

        CarType carType = new CarType();
        carType.setName("EMERGENCY");
        carType.setPriority(100);

        Car myCar = new Car();
        myCar.setId(0);
        myCar.setCurrentPosition(from);
        myCar.setType(carType);

        try {
            Route route = routeWebService.getRoute(myCar, to);
            LOGGER.info("[i] Route ID: " + route.getId());
            route.getInstructions().forEach(instruction ->
                    LOGGER.info("[r] " + instruction));
        } catch (NotAuthorizedCarException_Exception e) {
            LOGGER.error("[e] Error while querying the backend.");
        }
    }
}
