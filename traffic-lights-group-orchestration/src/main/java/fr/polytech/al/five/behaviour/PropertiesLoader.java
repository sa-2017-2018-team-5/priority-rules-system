package fr.polytech.al.five.behaviour;

import com.google.common.base.Splitter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class PropertiesLoader {

    private  Integer groupId;
    private  List<Integer> trafficLights;
    private  Map<Integer, List<Integer>> trafficRules = new HashMap<>();

    public PropertiesLoader(){
        this.groupId = -1;
        this.trafficLights = new ArrayList<>();
        this.trafficRules = new HashMap<>();
        loadConfigFile();
    }

    private void loadConfigFile() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            setGroupId(prop.getProperty("group.id"));
            setTrafficLights(prop.getProperty("traffic.lights"));
            setTrafficRules(prop.getProperty("traffic.rules"));

        } catch (IOException ex) {
            ex.printStackTrace();
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

    private void setGroupId(String id) {
        if (id != null){
            setId(id);
        }
    }

    private void setTrafficLights(String trafficList) {
        if (trafficList != null){
            fillTrafficLights(trafficList);
        }
    }

    private void setTrafficRules(String ruleList) {
        if (ruleList!=null){
            fillTrafficRules(ruleList);
        }
    }

    private void setId(String id){
        groupId = Integer.parseInt(id);
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

    private void fillTrafficLights(String trafficList){
        trafficLights = Arrays.asList(converter(trafficList));
    }

    private void fillTrafficRules(String ruleList){
        Map<String,String> tmp =  Splitter.on(":").withKeyValueSeparator("=").split(ruleList);

        for (String rule:tmp.keySet()) {
            trafficRules.put(Integer.parseInt(rule), Arrays.asList(converter(tmp.get(rule))));
        }
    }

    /*
            Data loader methods
     */
    List<Integer> getTrafficLights(){
        return trafficLights;
    }

    Map<Integer,List<Integer>> getTrafficRules(){
        return trafficRules;
    }

    Integer getId(){
        return groupId;
    }

    /*
        Test
     */

    @Override
    public String toString() {
        return "PropertiesLoader{" +
                "groupId=" + groupId +
                ", trafficLights=" + trafficLights +
                ", trafficRules=" + trafficRules +
                '}';
    }
}
