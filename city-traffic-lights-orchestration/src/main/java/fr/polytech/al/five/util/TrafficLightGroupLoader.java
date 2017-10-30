package fr.polytech.al.five.util;

import fr.polytech.al.five.model.TrafficLightsGroup;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class TrafficLightGroupLoader {

    private static final Logger LOGGER = Logger.getLogger(TrafficLightGroupLoader.class);

    private static Map<String, List<Integer>> trafficLightgroup = new HashMap<>();

    /**
     * temporary method to fill the car list
     * @param trafficLightsGroup
     */
    private static void build(TrafficLightsGroup trafficLightsGroup){
        List<Integer> tmp = new ArrayList<>();
        if (trafficLightgroup.containsKey(trafficLightsGroup.getId())) {
            tmp = trafficLightgroup.get(trafficLightsGroup.getId());
            tmp.add(trafficLightsGroup.getTrafficLight());
            trafficLightgroup.replace(trafficLightsGroup.getId(), tmp);
        } else {
            tmp.add(trafficLightsGroup.getTrafficLight());
            trafficLightgroup.put(trafficLightsGroup.getId(),tmp);
        }
    }

    /**
     * temporary implemented to retrieve data from csv file
     */
    static {
        ClassLoader classLoader = TrafficLightGroupLoader.class.getClassLoader();
        File carData = new File(classLoader.getResource("traffic-light-group.csv").getFile());
        try {
            CSVParser parser = CSVParser.parse(carData, Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader());

            parser.forEach(item->{
                TrafficLightsGroup trafficLightsGroup = new TrafficLightsGroup(
                        item.get("Group"),
                        Integer.parseInt(item.get("Traffic-lights"))
                );

                build(trafficLightsGroup);
            });

            parser.close();

        } catch (IOException e) {
            LOGGER.error("Could not load the properties file: " + e);
        }
    }

    public static Set<String> findGroup(List<Integer> traffics){
        Set<String> groups = new HashSet<>();
        for (String key: trafficLightgroup.keySet()) {
            for (int traffic : traffics) {
                if (trafficLightgroup.get(key).contains(traffic)) {
                    groups.add(key);
                }
            }
        }
        return groups;
    }
}
