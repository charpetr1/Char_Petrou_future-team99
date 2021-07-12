package gr.esdalab.msc101.dataconsumer.service;

import gr.esdalab.msc101.dataconsumer.model.EventDTO;
import gr.esdalab.msc101.dataconsumer.model.EventLevel;
import gr.esdalab.msc101.dataconsumer.model.SensorDTO;
import org.springframework.stereotype.Service;

@Service
public class RuleService {

    /**
     * This method implements a set of rules that apply to the sensorDTO object value
     * @param sensorDTO
     * @return
     */
    public EventDTO processData(SensorDTO sensorDTO){
        EventDTO eventDTO = new EventDTO();

        // @TODO Implement your own rules and populate the eventDTO object
        //Rules for fish hatchery station
        // Tank temperature rules
        if (sensorDTO.getValue() <= 13) {
            eventDTO.setLevel(EventLevel.DANGER);
            eventDTO.setMessage("Value " + sensorDTO.getValue() + " received.Something wrong with the tank temperature");
        } else if (sensorDTO.getValue() >= 13 && sensorDTO.getValue() <= 15) {
            eventDTO.setLevel(EventLevel.WARN);
            eventDTO.setMessage("Value " + sensorDTO.getValue() + " received. Too low, you must increase the tank temperature!");
        } else if (sensorDTO.getValue() > 15  && sensorDTO.getValue() < 23) {
            eventDTO.setLevel(EventLevel.NORMAL);
            eventDTO.setMessage("Value " + sensorDTO.getValue() + " received. Everything seems ok.");
        } else if (sensorDTO.getValue() >= 23 && sensorDTO.getValue() <= 27){
            eventDTO.setLevel(EventLevel.WARN);
            eventDTO.setMessage("Value " + sensorDTO.getValue() + " received. Too high tank temperature, you must cool the environment!");
        }else if (sensorDTO.getValue() > 27){
            eventDTO.setLevel(EventLevel.DANGER);
            eventDTO.setMessage("Value " + sensorDTO.getValue() + " received.Something wrong with the tank temperature");
        }
        //Ammonia rulew for the tanks
        if (sensorDTO.getValue() >= 6 ) {
            eventDTO.setLevel(EventLevel.DANGER);
            eventDTO.setMessage("Value " + sensorDTO.getValue() + " received.Something wrong with the ammonia of the tank ");
        } else if (sensorDTO.getValue() <=6 && sensorDTO.getValue() >= 4) {
            eventDTO.setLevel(EventLevel.WARN);
            eventDTO.setMessage("Value " + sensorDTO.getValue() + " received.You must decrease the ammonia levels of the tank!");
        } else if (sensorDTO.getValue() <4  && sensorDTO.getValue() > 2) {
            eventDTO.setLevel(EventLevel.NORMAL);
            eventDTO.setMessage("Value " + sensorDTO.getValue() + " received. Everything seems ok.");
        } else if (sensorDTO.getValue() <= 2 && sensorDTO.getValue() >1){
            eventDTO.setLevel(EventLevel.WARN);
            eventDTO.setMessage("Value " + sensorDTO.getValue() + " received. Too low ammonia in the tank , you must increase the feed!");
        }else if (sensorDTO.getValue() <1){
            eventDTO.setLevel(EventLevel.DANGER);
            eventDTO.setMessage("Value " + sensorDTO.getValue() + " received.Something wrong with the sensor");
        }
//      Return the eventDTO object
        return eventDTO;
    }
}
