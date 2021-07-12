package gr.esdalab.msc101.dataconsumer.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.esdalab.msc101.dataconsumer.config.MQTTConfig;
import gr.esdalab.msc101.dataconsumer.model.EventDTO;
import gr.esdalab.msc101.dataconsumer.model.SensorDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class MessagingService {

    private static final Logger log = LogManager.getLogger(MessagingService.class);

    @Autowired
    private IMqttClient mqttClient;

    @Autowired
    RuleService ruleService;

    @Autowired
    MQTTConfig mqttConfig;

//    @Value("${mqtt.topics}")
//    private Map<String, String> topics;

    ObjectMapper objectMapper;


    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {

        objectMapper = new ObjectMapper();

        // Subscribe to the topics as configured in application.yml file
        mqttConfig.getTopics().forEach((label, topic) -> {
            try {
                log.info("Subscribing to {}:{}.", label, topic);
                subscribe(topic);
            } catch (MqttException | InterruptedException e) {
                log.error(e);
            }
        });
    }


    /**
     * This method is used for receiving messages from the subscribed topics. It is called on every message publishing
     * @param topic
     * @throws MqttException
     * @throws InterruptedException
     */
    public void subscribe(final String topic) throws MqttException, InterruptedException {


        mqttClient.subscribeWithResponse(topic, (tpic, msg) -> {

//            Get payload from published message and convert it to String.
            String message = new String(msg.getPayload(), StandardCharsets.UTF_8);
//            Uncomment the following if you want to print the received message payload
            log.info(message);
//            Next step is to check the Class that the payload we received belongs to.
//            Check if the payload is a SensorDTO object
            if (checkJsonCompatibility(message, SensorDTO.class)) {
//              Convert the JSON string to SensorDTO object
                SensorDTO sensorDTO = objectMapper.readValue(msg.getPayload(), SensorDTO.class);
//              Do further processing by calling the processData(SensorDTO sensorDTO. This method will produce an
//              EventDTO object.
                EventDTO eventDTO = ruleService_fish_tank.processData(sensorDTO);
//              Create a new MqttMessage
                MqttMessage mqttMessage = new MqttMessage();
//              Set the payload
                mqttMessage.setPayload(objectMapper.writeValueAsString(eventDTO).getBytes());
//              Publish to the events topic. Update accordingly if needed
                mqttClient.publish(mqttConfig.getTopics().get("events"), mqttMessage);
//            Check if the payload is a EventDTO object
            } else if (checkJsonCompatibility(message, EventDTO.class)) {
//              Convert the JSON string to SensorDTO object
                EventDTO eventDTO = objectMapper.readValue(msg.getPayload(), EventDTO.class);
//                Print the event
                log.info("EventDTO received: " + eventDTO.toString());
            } else {
//              If the payload does not belong to either the knwown classes report it through a warning.
                log.warn("Unknown payload object.");
            }
        });
    }

    /**
     * Method that checks whether the provided Json String is a serialized instance of the provided class
     * @param jsonStr
     * @param valueType
     * @return
     * @throws JsonParseException
     * @throws IOException
     */
    public boolean checkJsonCompatibility(String jsonStr, Class<?> valueType) throws JsonParseException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.readValue(jsonStr, valueType);
            return true;
        } catch (JsonParseException | JsonMappingException e) {
            return false;
        }

    }

}
