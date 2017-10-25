package fr.polytech.al.five;

import fr.polytech.al.five.remote.ServiceProvider;
import stubs.administration.*;

import static stubs.administration.CarStatus.EMERGENCY;

public class Runner {

    private static CarType CAR_TYPE;
    private static AdministrationWebService ADMINISTRATION_WS;

    public static void main(String... args) {
        ADMINISTRATION_WS = ServiceProvider.getAdministrationWebService();

        create("FIREFIGHTERS", 100, EMERGENCY);
        create("POLICEMEN", 90, EMERGENCY);

        fetch("FIREFIGHTERS");

        update("FIREFIGHTERS", 120);
        update("POLICEMEN", 110);

        fetch("FIREFIGHTERS");
        fetch("POLICEMEN");
    }

    private static void create(String name, int priority, CarStatus status) {
        System.out.println("[x] Creating a new car type.");
        System.out.println("[x] * Name: " + name);
        System.out.println("[x] * Priority: " + priority);
        System.out.println("[x] * Status: " + status);
        System.out.println();

        CAR_TYPE = new CarType();
        CAR_TYPE.setName(name);
        CAR_TYPE.setPriority(priority);
        CAR_TYPE.setStatus(status);

        try {
            ADMINISTRATION_WS.registerPriority(CAR_TYPE);
            System.out.println("[s] Type '" + name + "' registered!");
        } catch (AlreadyExistingCarTypeException e) {
            System.err.println("[e] Could not register the type '" + name + "'.");
        }

        System.out.println();
    }

    private static void fetch(String name) {
        System.out.println("[f] Fetching the car type '" + name + "'.");

        try {
            CAR_TYPE = ADMINISTRATION_WS.findPriorityByName(name);
            System.out.println("[f] Type '" + name + "' fetched!");
            System.out.println("[f] '" + name + "' ; priority: " + CAR_TYPE.getPriority() + ".");
        } catch (NotExistingCarTypeException e) {
            System.err.println("[e] Could not fetch the car type '" + name + "'.");
        }

        System.out.println();
    }

    private static void update(String name, int updatedPriority) {
        System.out.println("[x] Updating type '" + name + "'.");
        System.out.println("[x] * Priority: " + updatedPriority);

        CAR_TYPE.setPriority(updatedPriority);

        try {
            ADMINISTRATION_WS.modifyPriority(CAR_TYPE);
            System.out.println("[s] Type '" + name + "' updated!");
        } catch (NotExistingCarTypeException e) {
            System.err.println("[e] Could not update the type '" + name + "'.");
        }

        System.out.println();
    }
}
