package util;

import model.TrafficGroup;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class TrafficLightGroupLoader {

    private static Map<String, List<String>> trafficLightgroup = new HashMap<>();

    /**
     * temporary method to fill the car list
     * @param trafficGroup
     */
    private static void build(TrafficGroup trafficGroup){
        List<String> tmp = new ArrayList<>();
        if (trafficLightgroup.containsKey(trafficGroup.getId())) {
            tmp = trafficLightgroup.get(trafficGroup.getId());
            tmp.add(trafficGroup.getTrafficLight());
            trafficLightgroup.replace(trafficGroup.getId(), tmp);
        }else {
            tmp.add(trafficGroup.getTrafficLight());
            trafficLightgroup.put(trafficGroup.getId(),tmp);
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
                TrafficGroup trafficGroup = new TrafficGroup(
                        item.get("Group"),
                        item.get("Traffic-lights")
                );

                build(trafficGroup);
            });

            parser.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<String> findGroup(List<String> traffics){
        Set<String> groups = new HashSet<>();
        for (String key: trafficLightgroup.keySet()) {
            for (String traffic : traffics) {
                if (trafficLightgroup.get(key).contains(traffic)) {
                    groups.add(key);
                }
            }
        }
        return groups;
    }
}
