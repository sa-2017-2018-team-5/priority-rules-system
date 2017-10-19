package fr.polytech.al.five.remote;

import stubs.route.RouteWebService;
import stubs.route.RouteWebServiceImplementationService;

public class ServiceProvider {

    public static RouteWebService getRouteService() {
        RouteWebServiceImplementationService factory =
                new RouteWebServiceImplementationService();

        return factory.getRouteWebServiceImplementationPort();
    }
}
