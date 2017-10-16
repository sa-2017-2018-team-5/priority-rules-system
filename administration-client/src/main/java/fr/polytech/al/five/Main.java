package fr.polytech.al.five;

public class Main {
    public static void main(String[] args) {
        AdministrationWebServiceImplementationService service = new AdministrationWebServiceImplementationService();
        AdministrationWebService admin = service.getAdministrationWebServiceImplementationPort();
        admin.registerPriority(new CarType());
    }
}
