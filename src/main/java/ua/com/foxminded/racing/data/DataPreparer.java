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

import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.toMap;

public class DataPreparer {

    private static final String ABBREVIATIONS_DATA_PATH = "\\workspace\\javaapiexercise\\src\\main\\resources\\abbreviations.txt";
    private static final String START_DATA_PATH = "\\workspace\\javaapiexercise\\src\\main\\resources\\start.log";
    private static final String END_DATA_PATH = "\\workspace\\javaapiexercise\\src\\main\\resources\\end.log";
    
    List<Racer> prepareData() {
        Map<String, Racer> racersMap = getRacers(ABBREVIATIONS_DATA_PATH);
        setStartTimes(racersMap, START_DATA_PATH);
        setEndTimes(racersMap, END_DATA_PATH);
        List<Racer> racers = calculateLapTimes(racersMap);
        rankRacers(racers);
        return racers;
    }
    
    private Map<String, Racer> getRacers(String abbreviationsDataPath) {
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
    
    @Test
    void test() {
        String line = "MES2018-05-24_12:04:45.513";
        Pattern pattern = Pattern.compile("([A-Z]{3}).*_(.{12})");
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        String abbreviation = matcher.group(1);
        String time = matcher.group(2);
        System.out.println("abbreviation: " + abbreviation + "\ntime: " + time);
    }
    
    private void setStartTimes(Map<String, Racer> racers, String startDataPath) {
        try (Stream<String> startData = Files.lines(Paths.get(startDataPath))) {
            startData.forEach(line -> {
                Pattern pattern = Pattern.compile("([A-Z]{3}).*_(.{12})");
                Matcher matcher = pattern.matcher(line);
                matcher.find();
                String abbreviation = matcher.group(1);
                String startTime = matcher.group(2);
                LocalTime startTimeParsed = LocalTime.parse(startTime);
                racers.get(abbreviation).setStartTime(startTimeParsed);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void setEndTimes(Map<String, Racer> racers, String endDataPath) {
        try (Stream<String> endData = Files.lines(Paths.get(endDataPath))) {
            endData.forEach(line -> {
                Pattern pattern = Pattern.compile("([A-Z]{3}).*_(.{12})");
                Matcher matcher = pattern.matcher(line);
                matcher.find();
                String abbreviation = matcher.group(1);
                String endTime = matcher.group(2);
                LocalTime endTimeParsed = LocalTime.parse(endTime);
                racers.get(abbreviation).setEndTime(endTimeParsed);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private List<Racer> calculateLapTimes(Map<String, Racer> racers) {
        racers.values().forEach(racer -> {
            Duration lapTime = Duration.between(racer.getStartTime(), racer.getEndTime());
            racer.setLapTime(lapTime);
        });
        List<Racer> racersList = new ArrayList<>(racers.values());
        return(racersList);
    }
    
    private void rankRacers(List<Racer> racers) {
        Collections.sort(racers, (racer1, racer2) -> {
            long racer1LapTimeInMillis = racer1.getLapTime().toMillis();
            long racer2LapTimeInMillis = racer2.getLapTime().toMillis();
            return (int) (racer1LapTimeInMillis - racer2LapTimeInMillis);
        });
    }
}