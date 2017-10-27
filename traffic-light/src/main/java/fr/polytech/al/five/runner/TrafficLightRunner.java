package fr.polytech.al.five.runner;

import fr.polytech.al.five.commands.TrafficLightCommands;
import fr.polytech.al.five.consumers.TrafficLightRoutesConsumer;
import fr.polytech.al.five.consumers.TrafficLightCommandsConsumer;
import fr.polytech.al.five.message.CarInfo;
import fr.polytech.al.five.util.EventListener;
import asg.cliche.ShellFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TrafficLightRunner {

    public static final Integer ID = 1111;
    public static ArrayList<CarInfo> cars = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        EventListener routesListener = new EventListener("TLActivity");
        try {
            routesListener.bind();
            TrafficLightCommandsConsumer trafficLightRoutesConsumer = new TrafficLightCommandsConsumer(routesListener.getChannel());
            routesListener.getChannel().basicConsume(routesListener.getQueueName(), true, trafficLightRoutesConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        EventListener TLActivityListener = new EventListener("Routes");
        try {
            TLActivityListener.bind();
            TrafficLightRoutesConsumer trafficLightCommandsConsumer = new TrafficLightRoutesConsumer(TLActivityListener.getChannel());
            TLActivityListener.getChannel().basicConsume(TLActivityListener.getQueueName(), true, trafficLightCommandsConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ShellFactory.createConsoleShell("traffic-light",
                "Traffic Light",
                new TrafficLightCommands())
                .commandLoop();
    }
}
