package fr.polytech.al.five.behaviour;

import com.google.common.base.Splitter;

import java.io.*;
import java.util.*;

public class PropertiesLoader {

    private  Integer groupId;
    private  List<Integer> trafficLights;
    private  Map<Integer, List<Integer>> trafficRules = new HashMap<>();

    public PropertiesLoader(){
        this.groupId = -1;
        this.trafficLights = new ArrayList<>();
        this.trafficRules = new HashMap<>();
    }

    public PropertiesLoader setGroupId(String id){
        if (id != null){
            setId(id);
        }
        return this;
    }

    public PropertiesLoader setTrafficLights(String trafficList){
        if (trafficList != null){
            fillTrafficLights(trafficList);
        }
        return this;
    }

    public PropertiesLoader setTrafficRules(String ruleList){
        if (ruleList!=null){
            fillTrafficRules(ruleList);
        }
        return this;
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
