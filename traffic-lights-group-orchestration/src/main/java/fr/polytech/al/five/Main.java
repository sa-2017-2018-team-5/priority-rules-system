package fr.polytech.al.five;

import fr.polytech.al.five.message.TrafficLightColour;
import fr.polytech.al.five.message.TrafficLightCommand;
import fr.polytech.al.five.message.TrafficLightInfo;
import fr.polytech.al.five.util.EventEmitter;
import fr.polytech.al.five.util.MessageMarshaller;

import java.io.IOException;

public class Main {

    public static void main(String[] args){

        EventEmitter emitter = new EventEmitter("TLActivity");
        TrafficLightCommand command = new TrafficLightCommand();
        command.setTrafficLightInfo(new TrafficLightInfo(1111));
        command.setColour(TrafficLightColour.RED);

        try {
            emitter.publish(MessageMarshaller.construct(command).getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        emitter.close();
    }
}
