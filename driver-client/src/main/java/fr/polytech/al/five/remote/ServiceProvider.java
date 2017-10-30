package fr.polytech.al.five.remote;

import stubs.route.RouteWebService;
import stubs.route.RouteWebServiceImplementationService;

import javax.xml.ws.BindingProvider;
import java.net.URL;

public class ServiceProvider {

    private ServiceProvider() {
        // No ServiceProvider object!
    }

    private static String getEnvironment(String variable, String defaultValue) {
        String content = System.getenv(variable);
        if (content == null) {
            content = defaultValue;
        }

        return content;
    }

    private static String getHost() {
        return getEnvironment("BACKEND_HOST", "localhost");
    }

    private static String getPort() {
        return getEnvironment("BACKEND_PORT", "8080");
    }

    public static RouteWebService getRouteService() {
        URL wsdlLocation = ServiceProvider.class.getResource("/wsdl/RouteWS.wsdl");

        RouteWebServiceImplementationService factory =
                new RouteWebServiceImplementationService(wsdlLocation);

        RouteWebService ws = factory.getRouteWebServiceImplementationPort();

        String address = String.format(
                "http://%s:%s/priority-rules-backend/webservices/RouteWS",
                getHost(),
                getPort());

        ((BindingProvider) ws).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);

        return ws;
    }
}
