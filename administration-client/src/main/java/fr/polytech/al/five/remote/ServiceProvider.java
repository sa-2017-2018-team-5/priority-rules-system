package fr.polytech.al.five.remote;

import stubs.administration.AdministrationWebService;
import stubs.administration.AdministrationWebServiceImplementationService;

public class ServiceProvider {

    public static AdministrationWebService getAdministrationWebService() {
        AdministrationWebServiceImplementationService factory =
                new AdministrationWebServiceImplementationService();

        return factory.getAdministrationWebServiceImplementationPort();
    }
}
