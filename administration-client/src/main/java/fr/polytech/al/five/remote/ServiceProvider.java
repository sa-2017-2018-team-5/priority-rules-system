package fr.polytech.al.five.remote;

import stubs.administration.AdministrationWebService;
import stubs.administration.AdministrationWebServiceImplementationService;

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

    public static AdministrationWebService getAdministrationWebService() {
        URL wsdlLocation = ServiceProvider.class.getResource("/wsdl/AdministrationWS.wsdl");

        AdministrationWebServiceImplementationService factory =
                new AdministrationWebServiceImplementationService(wsdlLocation);

        AdministrationWebService ws = factory.getAdministrationWebServiceImplementationPort();

        String address = String.format(
                "http://%s:%s/priority-rules-backend/webservices/AdministrationWS",
                getHost(),
                getPort());

        ((BindingProvider) ws).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);

        return ws;
    }
}
