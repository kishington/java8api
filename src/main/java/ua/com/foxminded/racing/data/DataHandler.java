package ua.com.foxminded.racing.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class DataHandler {

    String startDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\start.log";
    String endDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\end.log";
    String abbreviationsDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\abbreviations.txt";

    
    Map<String, Racer> getRacers(String abbreviationsDataPath) {
        Map<String, Racer> racers = new HashMap<>();
        try (Stream<String> abbreviationsData = Files.lines(Paths.get(abbreviationsDataPath))) {
            racers = abbreviationsData.collect(toMap(line -> line.substring(0, 3), line -> {
                Racer racer = new Racer();
                Pattern pattern = Pattern.compile("_(.*)_");
                Matcher matcher = pattern.matcher(line);
                matcher.find();
                String name = matcher.group(1);
                racer.setName(name);
                return racer;
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return racers;
    }
    
    void setStartTimes(Map<String, Racer> racers, String startDataPath) {
        try (Stream<String> startData = Files.lines(Paths.get(startDataPath))) {
            startData.forEach(line -> {
                String abbreviation = line.substring(0, 3);
                String startTime = line.substring(14, 26);
                LocalTime startTimeParsed = LocalTime.parse(startTime);
                racers.get(abbreviation).setStartTime(startTimeParsed);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    void setEndTimes(Map<String, Racer> racers, String endDataPath) {
        try (Stream<String> endData = Files.lines(Paths.get(endDataPath))) {
            endData.forEach(line -> {
                String abbreviation = line.substring(0, 3);
                String endTime = line.substring(14, 26);
                LocalTime endTimeParsed = LocalTime.parse(endTime);
                racers.get(abbreviation).setEndTime(endTimeParsed);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    void getLapTimes(Map<String, Racer> racers, String endDataPath) {
        try (Stream<String> endData = Files.lines(Paths.get(endDataPath))) {
            endData.forEach(line -> {
                String abbreviation = line.substring(0, 3);
                String endTime = line.substring(14, 26);
                LocalTime endTimeParsed = LocalTime.parse(endTime);
                racers.get(abbreviation).setEndTime(endTimeParsed);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * List<Racer> setStartTimes(List<Racer> racers, String startDataPath) { try
     * (Stream<String> startData = Files.lines(Paths.get(startDataPath))) {
     * 
     * List<Racer> tempRacers = startData.map(line -> { Racer racer = new Racer();
     * racer.setStartTime(line.substring(14, 26)); return racer;
     * }).collect(toList()); tempRacers.stream().
     * 
     * } catch (IOException e) { e.printStackTrace(); }
     * 
     * return racers; }
     */
}