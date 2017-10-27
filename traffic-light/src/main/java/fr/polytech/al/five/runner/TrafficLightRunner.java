package fr.polytech.al.five.runner;

import fr.polytech.al.five.commands.TrafficLightCommands;
import fr.polytech.al.five.listener.TrafficLightConsumer;
import fr.polytech.al.five.message.CarInfo;
import fr.polytech.al.five.util.EventListener;
import asg.cliche.ShellFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TrafficLightRunner {

    public static final Integer ID = 60521;
    public static ArrayList<CarInfo> cars = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        EventListener listener = new EventListener("Routes");

        try {
            listener.bind();
            TrafficLightConsumer trafficLightConsumer = new TrafficLightConsumer(listener.getChannel());
            listener.getChannel().basicConsume(listener.getQueueName(), true, trafficLightConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ShellFactory.createConsoleShell("traffic-light",
                "Traffic Light",
                new TrafficLightCommands())
                .commandLoop();
    }
}
