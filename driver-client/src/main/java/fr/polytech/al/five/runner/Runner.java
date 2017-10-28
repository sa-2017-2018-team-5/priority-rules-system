package fr.polytech.al.five.runner;

import asg.cliche.ShellFactory;
import fr.polytech.al.five.commands.DriverCommands;
import fr.polytech.al.five.remote.ServiceProvider;

import java.io.IOException;

public class Runner {

    public static void main(String... args) throws IOException {
        ShellFactory.createConsoleShell("driver",
                "Driver Client",
                new DriverCommands(ServiceProvider.getRouteService()))
                .commandLoop();
    }
}
