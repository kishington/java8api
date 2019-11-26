package ua.com.foxminded.racing.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    
    void calculateLapTimes(Map<String, Racer> racers) {
        
        racers.values().forEach(racer -> {
            Duration lapTime = Duration.between(racer.getStartTime(), racer.getEndTime());
            racer.setLapTime(lapTime);
        });
    }

    String formatDuration(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.toSeconds() % 60;
        long millis = duration.toMillis() % 1000;
        String output = String.format("%1$02d" + ":" + "%2$02d" + "." + "%3$03d", minutes, seconds, millis);
        return output;
    }
    
    Map<String, Racer> rankRacers(Map<String, Racer> racers) {
        /*
         * racers = racers.entrySet() .stream() .sorted(Map.Entry.comparingByValue())
         * .collect(toMap( Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue)
         * -> oldValue, LinkedHashMap::new));
         */
        
        Map<String, Racer> rankedRacers = new LinkedHashMap<>();
        racers.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue())
        .forEach(x -> rankedRacers.put(x.getKey(), x.getValue()));
        return rankedRacers;
    }
 
    int getLongestNameLength(Map<String, Racer> racers) {
        List<String> names = new ArrayList<>();
        racers.values().forEach(racer -> names.add(racer.getName()));
 
        String longestName = "";
        Optional<String> longestNameOp = names.stream().max(Comparator.comparingInt(String::length)); 
        if (longestNameOp.isPresent()) {
            longestName = longestNameOp.get();
        }
        return longestName.length();
    }
    
    int getLongestTeamNameLength(Map<String, Racer> racers) {
        List<String> teams = new ArrayList<>();
        racers.values().forEach(racer -> teams.add(racer.getTeam()));
 
        String longestTeamName = "";
        Optional<String> longestTeamNameOp = teams.stream().max(Comparator.comparingInt(String::length)); 
        if (longestTeamNameOp.isPresent()) {
            longestTeamName = longestTeamNameOp.get();
        }
        return longestTeamName.length();
    }
}