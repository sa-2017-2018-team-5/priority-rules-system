package fr.polytech.al.five.runner;

import asg.cliche.ShellFactory;
import fr.polytech.al.five.remote.ServiceProvider;

import java.io.IOException;

public class AdministrationRunner {

    public static void main(String... args) throws IOException {
        ShellFactory.createConsoleShell("administration",
                "Administration Client",
                new AdministrationInterface(ServiceProvider.getAdministrationWebService()))
                .commandLoop();
    }
}
