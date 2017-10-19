package fr.polytech.al.five;

import fr.polytech.al.five.remote.ServiceProvider;
import stubs.route.*;

public class Runner {

    public static void main(String... args) {
        System.out.println("[x] Asking for a route.");
        System.out.println("[x] From: [43.641127, 7.134966]");
        System.out.println("[x] To: [43.6155793, 7.071874799999932]\n");

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
            System.out.println("[i] Route ID: " + route.getId());
            route.getInstructions().forEach(instruction ->
                    System.out.println("[r] " + instruction));
        } catch (NotAuthorizedCarException_Exception e) {
            System.out.println("[error] Error while querying the backend.");
        }
    }
}
