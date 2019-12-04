package ua.com.foxminded.racing.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toMap;

public class DataPreparer {

    List<Racer> prepareData(String abbreviationsDataPath, String startDataPath, String endDataPath) throws IOException {
        Map<String, Racer> racersMap = getRacers(abbreviationsDataPath);
        if (racersMap.size() == 0) {
            throw new IllegalArgumentException("There should be valid abbreviation data for at least one racer");
        }
        setStartTimes(racersMap, startDataPath);
        setEndTimes(racersMap, endDataPath);
        List<Racer> racers = calculateLapTimes(racersMap);
        rankRacers(racers);
        return racers;
    }

    private Map<String, Racer> getRacers(String abbreviationsDataPath) throws IOException {
        InputStream in = getClass().getResourceAsStream(abbreviationsDataPath);
        BufferedReader abbreviationsDataReader = new BufferedReader(new InputStreamReader(in));
        Map<String, Racer> racers = abbreviationsDataReader.lines().collect(toMap(line -> {
            Pattern pattern = Pattern.compile("([A-Z]{3})_");
            Matcher matcher = pattern.matcher(line);
            boolean found = matcher.find();
            if (!found) {
                throw new IllegalArgumentException("Invalid format in abbreviations data");
            }
            String abbreviation = matcher.group(1);
            return abbreviation;
        }, line -> {
            Racer racer = new Racer();
            Pattern pattern = Pattern.compile("_(.*)_(.*)");
            Matcher matcher = pattern.matcher(line);
            boolean found = matcher.find();
            if (!found) {
                throw new IllegalArgumentException("Invalid format in abbreviations data");
            }
            String name = matcher.group(1);
            String team = matcher.group(2);
            racer.setName(name);
            racer.setTeam(team);
            return racer;
        }));
        abbreviationsDataReader.close();
        return racers;
    }

    private void setStartTimes(Map<String, Racer> racers, String startDataPath) throws IOException {
        InputStream in = getClass().getResourceAsStream(startDataPath);
        BufferedReader startDataReader = new BufferedReader(new InputStreamReader(in));
        startDataReader.lines().forEach(line -> {
            Pattern pattern = Pattern.compile("([A-Z]{3}).*_([0-9:.]{12})");
            Matcher matcher = pattern.matcher(line);
            boolean found = matcher.find();
            if (!found) {
                throw new IllegalArgumentException("Invalid format of start times data");
            }
            String abbreviation = matcher.group(1);
            String startTime = matcher.group(2);
            LocalTime startTimeParsed = LocalTime.parse(startTime);
            racers.get(abbreviation).setStartTime(startTimeParsed);
        });
        startDataReader.close();
    }

    private void setEndTimes(Map<String, Racer> racers, String endDataPath) throws IOException {
        InputStream in = getClass().getResourceAsStream(endDataPath);
        BufferedReader endDataReader = new BufferedReader(new InputStreamReader(in));
        endDataReader.lines().forEach(line -> {
            Pattern pattern = Pattern.compile("([A-Z]{3}).*_([0-9:.]{12})");
            Matcher matcher = pattern.matcher(line);
            boolean found = matcher.find();
            if (!found) {
                throw new IllegalArgumentException("Invalid format of end times data");
            }
            String abbreviation = matcher.group(1);
            String endTime = matcher.group(2);
            LocalTime endTimeParsed = LocalTime.parse(endTime);
            racers.get(abbreviation).setEndTime(endTimeParsed);
        });
        endDataReader.close();
    }

    private List<Racer> calculateLapTimes(Map<String, Racer> racers) {
        racers.values().forEach(racer -> {
            if (racer.getStartTime() == null && racer.getEndTime() != null) {
                throw new IllegalArgumentException("Missing strart time data for at least one racer");
            }
            if (racer.getEndTime() == null && racer.getStartTime() != null) {
                throw new IllegalArgumentException("Missing end time data for at least one racer");
            }
            if (racer.getEndTime() == null && racer.getStartTime() == null) {
                throw new IllegalArgumentException("Missing start time and end time data for at least one racer");
            }
            Duration lapTime = Duration.between(racer.getStartTime(), racer.getEndTime());
            racer.setLapTime(lapTime);
        });
        List<Racer> racersList = new ArrayList<>(racers.values());
        return (racersList);
    }

    private void rankRacers(List<Racer> racers) {
        Collections.sort(racers, (racer1, racer2) -> {
            long racer1LapTimeInMillis = racer1.getLapTime().toMillis();
            long racer2LapTimeInMillis = racer2.getLapTime().toMillis();
            return (int) (racer1LapTimeInMillis - racer2LapTimeInMillis);
        });
    }
}
