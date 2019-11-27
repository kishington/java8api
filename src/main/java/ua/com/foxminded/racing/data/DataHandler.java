package ua.com.foxminded.racing.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


import static java.util.stream.Collectors.toMap;

public class DataHandler {

    String abbreviationsDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\abbreviations.txt";
    String startDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\start.log";
    String endDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\end.log";

    List<Racer> prepareDataForVisualisation() {
        Map<String, Racer> racersMap = getRacers(abbreviationsDataPath);
        setStartTimes(racersMap, startDataPath);
        setEndTimes(racersMap, endDataPath);
        List<Racer> racers = calculateLapTimes(racersMap);
        rankRacers(racers);
        return racers;
    }
    
    Map<String, Racer> getRacers(String abbreviationsDataPath) {
        Map<String, Racer> racers = new HashMap<>();
        try (Stream<String> abbreviationsData = Files.lines(Paths.get(abbreviationsDataPath))) {
            racers = abbreviationsData.collect(toMap(line -> line.substring(0, 3), line -> {
                Racer racer = new Racer();
                Pattern pattern = Pattern.compile("_(.*)_(.*)");
                Matcher matcher = pattern.matcher(line);
                matcher.find();
                String name = matcher.group(1);
                String team = matcher.group(2);
                racer.setName(name);
                racer.setTeam(team);
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
    
    List<Racer> calculateLapTimes(Map<String, Racer> racers) {
        racers.values().forEach(racer -> {
            Duration lapTime = Duration.between(racer.getStartTime(), racer.getEndTime());
            racer.setLapTime(lapTime);
        });
        List<Racer> racersList = new ArrayList<>(racers.values());
        return(racersList);
    }
    
    void rankRacers(List<Racer> racers) {
        Collections.sort(racers, (racer1, racer2) -> {
            long racer1LapTimeInMillis = racer1.lapTime.toMillis();
            long racer2LapTimeInMillis = racer2.lapTime.toMillis();
            return (int) (racer1LapTimeInMillis - racer2LapTimeInMillis);
        });
    }
}