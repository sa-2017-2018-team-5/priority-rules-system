package fr.polytech.al.five.runner;

import asg.cliche.ShellFactory;
import fr.polytech.al.five.remote.ServiceProvider;

import java.io.IOException;

public class DriverRunner {

    public static void main(String... args) throws IOException {
        ShellFactory.createConsoleShell("driver",
                "Driver Client",
                new DriverInterface(ServiceProvider.getURL())).commandLoop();
    }
}
