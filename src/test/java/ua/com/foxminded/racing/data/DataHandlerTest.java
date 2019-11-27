package ua.com.foxminded.racing.data;


import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


import org.junit.jupiter.api.Test;

public class DataHandlerTest {
    String startDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\start.log";
    String endDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\end.log";
    String abbreviationsDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\abbreviations.txt";
    
    DataHandler dataHandler = new DataHandler();
    Map<String, Racer> racers = dataHandler.getRacers(abbreviationsDataPath);
            
    @Test
    void test_getRacers() {
        Map<String, Racer> map = dataHandler.getRacers(abbreviationsDataPath);
        System.out.println(map);
        map.forEach((k,v) -> System.out.println(k + "\n" + v.getName() + "\n"));  
    }
    @Test
    void test_setStartTimes() {
        dataHandler.setStartTimes(racers, startDataPath);
        racers.entrySet().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue().getStartTime()));    
    }
    @Test
    void test_setEndTimes() {
        dataHandler.setEndTimes(racers, endDataPath);
        racers.entrySet().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue().getEndTime()));    
    }
    
//    @Test
//    void test_calculateLapTimes() {
//        Map<String, Racer> racers = dataHandler.getRacers(abbreviationsDataPath);
//        dataHandler.setStartTimes(racers, startDataPath);
//        dataHandler.setEndTimes(racers, endDataPath);
//        dataHandler.calculateLapTimes(racers);
//        racers.forEach((k,v) -> {
//            String lapTime = dataHandler.formatDuration(v.getLapTime());
//            System.out.println(k + " " + lapTime);
//        });
//    }

//    @Test
//    void test_rankRacers() {
//        Map<String, Racer> racers = dataHandler.getRacers(abbreviationsDataPath);
//        dataHandler.setStartTimes(racers, startDataPath);
//        dataHandler.setEndTimes(racers, endDataPath);
//        dataHandler.calculateLapTimes(racers);
//    
//        Map<String, Racer> rankedRacers = new LinkedHashMap<>();
//        rankedRacers = dataHandler.rankRacers(racers);
//        rankedRacers.forEach((k,v) -> {
//            String lapTime = dataHandler.formatDuration(v.getLapTime());
//            System.out.println(k + " " + lapTime);
//        });
//    }
  
    
    class Emp {
        int id;
        String name;
        Emp(String name) {
            this.name = name;
        }
    }
    @Test
    void test43() {
        Map<Emp, Integer> map = new HashMap<>();
  
        map.put(new Emp("AAA"), 3);
        map.put(new Emp("AAB"), 6);
        map.put(new Emp("AAC"), 7);
        map.put(new Emp("AAD"), 2);
        
        Map<Emp, Integer> sortedMap = new LinkedHashMap<>();
        map.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue())
        .forEach(x -> sortedMap.put(x.getKey(), x.getValue()));
        
        map.forEach((k,v) -> System.out.print(k.name + " " + v + "; "));
        System.out.println();
        sortedMap.forEach((k,v) -> System.out.print(k.name + " " + v + "; "));
    }
    
}


