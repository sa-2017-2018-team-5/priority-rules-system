package fr.polytech.al.five;

import fr.polytech.al.five.remote.ServiceProvider;
import stubs.administration.AdministrationWebService;
import stubs.administration.CarType;

public class Runner {

    public static void main(String... args) {
        System.out.println("[x] Creating a new car type.");
        System.out.println("[x] 'EMERGENCY' ; priority: 100.");

        AdministrationWebService administrationWebService =
                ServiceProvider.getAdministrationWebService();

        CarType carType = new CarType();
        carType.setName("EMERGENCY");
        carType.setPriority(100);

        administrationWebService.registerPriority(carType);

        System.out.println("[s] Type 'EMERGENCY' registered!\n");
        System.out.println("[x] Updating type 'EMERGENCY'.");
        System.out.println("[x] 'EMERGENCY' ; priority: 120.");

        carType.setPriority(120);

        administrationWebService.modifyPriority(carType);

        System.out.println("[s] Type 'EMERGENCY' updated!");
    }
}
