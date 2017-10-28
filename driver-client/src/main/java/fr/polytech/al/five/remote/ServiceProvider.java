package fr.polytech.al.five.remote;

import stubs.route.RouteWebService;
import stubs.route.RouteWebServiceImplementationService;

public class ServiceProvider {

    private ServiceProvider() {
        // No ServiceProvider object!
    }

    public static RouteWebService getRouteService() {
        RouteWebServiceImplementationService factory =
                new RouteWebServiceImplementationService();

        return factory.getRouteWebServiceImplementationPort();
    }
}
