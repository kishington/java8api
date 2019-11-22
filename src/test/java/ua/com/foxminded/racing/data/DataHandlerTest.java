package ua.com.foxminded.racing.data;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class DataHandlerTest {
    String startDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\start.log";
    String endDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\end.log";
    String abbreviationsDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\abbreviations.txt";
    DataHandler dataHandler = new DataHandler();
    Map<String, Racer> racers = dataHandler.getRacers(abbreviationsDataPath);
            
    @Test
    void test() {
        Map<String, Racer> map = dataHandler.getRacers(abbreviationsDataPath);
        System.out.println(map);
        map.forEach((k,v) -> System.out.println(k + "\n" + v.getName() + "\n"));  
    }
    @Test
    void test4() {
        dataHandler.setStartTimes(racers, startDataPath);
        racers.entrySet().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue().getStartTime()));    
    }
    @Test
    void test5() {
        dataHandler.setEndTimes(racers, endDataPath);
        racers.entrySet().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue().getEndTime()));    
    }
    
    @Test
    void test2() {
        String timeColonPattern = "HH:mm:ss.SSS";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        LocalTime colonTime = LocalTime.of(17, 35, 50).plus(329, ChronoUnit.MILLIS);
        System.out.println(timeColonFormatter.format(colonTime));
    }
    @Test
    void test3() {
        String text = "17:35:50.300";
        String timeColonPattern = "HH:mm:ss.SSS";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        System.out.println(LocalTime.parse(text).plus(25, ChronoUnit.MILLIS));
        System.out.println(LocalTime.of(14, 32, 23).plus(432, ChronoUnit.MILLIS));
    }
}


