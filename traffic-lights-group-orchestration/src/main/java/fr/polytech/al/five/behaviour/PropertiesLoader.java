package fr.polytech.al.five.behaviour;

import com.google.common.base.Splitter;
import fr.polytech.al.five.model.TrafficGroup;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PropertiesLoader {

    private static final String CSV_FILE_PATH = "traffic-rules.csv";

    private List<TrafficGroup> trafficGroups;

    public PropertiesLoader(){
        this.trafficGroups = new ArrayList<>();
        loadConfigFile();
    }

    private void loadConfigFile() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withHeader("group-id", "traffic-id", "rules")
                    .withIgnoreHeaderCase()
                    .withTrim());
            List<CSVRecord> csvRecords = csvParser.getRecords();
            csvRecords.remove(0);
            csvRecords.forEach(record -> {
                TrafficGroup group = new TrafficGroup();
                group.setGroupId(Integer.valueOf(record.get("group-id")));
                fillTrafficLights(group.getTrafficID(), record.get("traffic-id"));
                fillTrafficRules(group.getRules(), record.get("rules"));
                trafficGroups.add(group);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Integer[] converter(String txt){
        String[] strArray = txt.split("\\s*,\\s*");
        Integer[] intArray=new Integer[strArray.length];
        int i=0;
        for(String str:strArray){
            intArray[i]=Integer.parseInt(str.trim());
            i++;
        }
        return intArray;
    }

    private void fillTrafficLights(List<Integer> trafficLight, String trafficList) {
        trafficLight.addAll(Arrays.asList(converter(trafficList)));
    }

    private void fillTrafficRules(Map<Integer, List<Integer>> rules, String ruleList) {
        Map<String,String> tmp =  Splitter.on(":").withKeyValueSeparator("=").split(ruleList);

        for (String rule:tmp.keySet()) {
            rules.put(Integer.parseInt(rule), Arrays.asList(converter(tmp.get(rule))));
        }
    }

    /*
            Data loader methods
     */
    public List<TrafficGroup> getTrafficGroups() {
        return trafficGroups;
    }

}
