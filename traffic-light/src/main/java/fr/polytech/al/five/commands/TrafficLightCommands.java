package fr.polytech.al.five.commands;

import asg.cliche.Command;
import com.fasterxml.jackson.core.JsonProcessingException;
import fr.polytech.al.five.message.CarDetection;
import fr.polytech.al.five.message.CarInfo;
import fr.polytech.al.five.message.CarPosition;
import fr.polytech.al.five.message.TrafficLightInfo;
import fr.polytech.al.five.runner.TrafficLightRunner;
import fr.polytech.al.five.util.EventEmitter;
import fr.polytech.al.five.util.MessageMarshaller;

import java.io.IOException;

public class TrafficLightCommands {

    @Command(name = "see-car")
    public void seeCar(Integer carId) throws IOException {
        for (CarInfo carInfo : TrafficLightRunner.cars) {
            if (carInfo.getId().equals(carId)) {
                event(carInfo, CarPosition.SEEN);
            }
        }
    }

    @Command(name = "pass-car")
    public void passCar(Integer carId) throws IOException {
        CarInfo carInfoToRemove = null;
        for (CarInfo carInfo : TrafficLightRunner.cars) {
            if (carInfo.getId().equals(carId)) {
                carInfoToRemove = carInfo;
                event(carInfo, CarPosition.PASSED);
            }
        }
        if (carInfoToRemove != null) {
            TrafficLightRunner.cars.remove(carInfoToRemove);
        }
    }

    private void event(CarInfo carInfo, CarPosition carPosition) throws IOException {
        EventEmitter emitter = new EventEmitter("TLActivity");

        CarDetection carDetection = new CarDetection();
        carDetection.setTrafficLightInfo(new TrafficLightInfo(TrafficLightRunner.ID));
        carDetection.setCarInfo(carInfo);
        carDetection.setCarPosition(carPosition);

        String message = "";
        try {
            message = MessageMarshaller.construct(carDetection);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("Sending :" + message);

        emitter.publish(message.getBytes("UTF-8"));
    }
}
