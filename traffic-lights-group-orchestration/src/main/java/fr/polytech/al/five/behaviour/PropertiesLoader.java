package fr.polytech.al.five.behaviour;

import com.google.common.base.Splitter;

import java.io.*;
import java.util.*;

public class PropertiesLoader {

    private static final String CONF_FILE = "config.properties";

    private static Integer groupId;
    private static List<Integer> trafficLights;
    private static Map<Integer, List<Integer>> trafficRules = new HashMap<>();

    private static void setId(String id){
        groupId = Integer.parseInt(id);
    }

    private static Integer[] converter(String txt){
        String[] strArray = txt.split("\\s*,\\s*");
        Integer[] intArray=new Integer[strArray.length];
        int i=0;
        for(String str:strArray){
            intArray[i]=Integer.parseInt(str.trim());
            i++;
        }
        return intArray;
    }

    private static void fillTrafficLights(String trafficList){
        trafficLights = Arrays.asList(converter(trafficList));
    }

    private static void fillTrafficRules(String ruleList){
        Map<String,String> tmp =  Splitter.on(":").withKeyValueSeparator("=").split(ruleList);

        for (String rule:tmp.keySet()) {
            trafficRules.put(Integer.parseInt(rule), Arrays.asList(converter(tmp.get(rule))));
        }
    }

    static {
        ClassLoader classLoader = PropertiesLoader.class.getClassLoader();
        Properties properties = new Properties();
        InputStream input = null;
        try {
            // Read the conf file
            input = new FileInputStream(classLoader.getResource(CONF_FILE).getPath());

            // Load properties
            properties.load(input);
            setId(properties.getProperty("group.id"));
            fillTrafficLights(properties.getProperty("traffic.lights"));
            fillTrafficRules(properties.getProperty("traffic.rules"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
            Data loader methods
     */
    static List<Integer> getTrafficLights(){
        return trafficLights;
    }

    static Map<Integer,List<Integer>> getTrafficRules(){
        return trafficRules;
    }

    static Integer getId(){
        return groupId;
    }

}
