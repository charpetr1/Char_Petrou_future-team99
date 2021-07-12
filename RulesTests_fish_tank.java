package gr.esdalab.msc101.dataconsumer;

import gr.esdalab.msc101.dataconsumer.model.EventLevel;
import gr.esdalab.msc101.dataconsumer.model.SensorDTO;
import gr.esdalab.msc101.dataconsumer.service.RuleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class RulesTests {

    private static final Logger log = LogManager.getLogger(RulesTests.class);


    SensorDTO outOfOperationLowSensorDTO = new SensorDTO("temp", 13.0);
    SensorDTO outOfOperationHighSensorDTO = new SensorDTO("temp", 25.0);
    SensorDTO heatSensorDTO = new SensorDTO("temp", 27.0);
    SensorDTO coolSensorDTO = new SensorDTO("temp", 12.0);
    SensorDTO normalSensorDTO = new SensorDTO("temp", 22.0);
    
    SensorDTO outOfOperationLowSensorDTO = new SensorDTO("ammonia", 1.0);
    SensorDTO outOfOperationHighSensorDTO = new SensorDTO("ammonia", 7.0);
    SensorDTO highSensorDTO = new SensorDTO("ammonia", 8.0);
    SensorDTO lowSensorDTO = new SensorDTO("ammonia", 2.0);
    SensorDTO normalSensorDTO = new SensorDTO("ammonia", 6.0);

    @Autowired
    RuleService_fish_tank ruleService_fish_tank;

    @BeforeAll
    public static void init(){
        log.info("Tesing RuleService_fish_tank started.");
    }

    @Test
    void testOutOfOperationLow() {
        assertEquals(EventLevel.DANGER, ruleService_fish_tank.processData(outOfOperationLowSensorDTO).getLevel());
    }

    @Test
    void testOutOfOperationHigh() {
        assertEquals(EventLevel.DANGER, ruleService_fish_tank.processData(outOfOperationHighSensorDTO).getLevel());
    }

    @Test
    void testHeat() {
        assertEquals(EventLevel.WARN, ruleService_fish_tank.processData(heatSensorDTO).getLevel());
    }

    @Test
    void testCool() {
        assertEquals(EventLevel.WARN, ruleService_fish_tank.processData(coolSensorDTO).getLevel());
    }

    @Test
    void testNormal() {
        assertEquals(EventLevel.NORMAL, ruleService_fish_tank.processData(normalSensorDTO).getLevel());
    }
    @Test
    void testOutOfOperationLow() {
        assertEquals(EventLevel.DANGER, ruleService_fish_tank.processData(outOfOperationLowSensorDTO).getLevel());
    }

    @Test
    void testOutOfOperationHigh() {
        assertEquals(EventLevel.DANGER, ruleService_fish_tank.processData(outOfOperationHighSensorDTO).getLevel());
    }

    @Test
    void testHeat() {
        assertEquals(EventLevel.WARN, ruleService_fish_tank.processData(HightSensorDTO).getLevel());
    }

    @Test
    void testCool() {
        assertEquals(EventLevel.WARN, ruleService_fish_tank.processData(coolSensorDTO).getLevel());
    }

    @Test
    void testNormal() {
        assertEquals(EventLevel.NORMAL, ruleService_fish_tank.processData(normalSensorDTO).getLevel());
    }

}
