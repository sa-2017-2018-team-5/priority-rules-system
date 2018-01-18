package fr.polytech.al.five.runner;

import fr.polytech.al.five.behaviour.PropertiesLoader;
import fr.polytech.al.five.behaviour.TrafficLightsGroupState;
import fr.polytech.al.five.bus.BusInformation;
import org.apache.log4j.Logger;

public class TrafficLightsGroupRunner {

    private static final Logger LOGGER = Logger.getLogger(TrafficLightsGroupRunner.class);

    public static void main(String[] args) {

        String busAddress = System.getenv("BUS_ADDRESS");
        if (busAddress == null) {
            busAddress = "localhost";
        }

        PropertiesLoader properties = new PropertiesLoader();

        BusInformation busInformation = new BusInformation(busAddress);

        TrafficLightsGroupState state = new TrafficLightsGroupState(properties);

        System.out.println(properties.toString());
//        // Set up the Traffic Lights Observation messages consumer.
//        MessageConsumer<TrafficLightObservationMessage> observationConsumer =
//                new MessageConsumer<>(busInformation);
//        try {
//            observationConsumer.makeConsume(BusChannel.TRAFFIC_LIGHT_OBSERVATION,
//                    new OnTrafficLightObservation(busInformation, state).getAction());
//        } catch (IOException | TimeoutException e) {
//            LOGGER.error("Error while connecting to the bus: " + e);
//            System.exit(1);
//        }
    }
}
