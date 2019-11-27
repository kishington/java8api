package ua.com.foxminded.racing.data;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class Visualiser {
    
    String startDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\start.log";
    String endDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\end.log";
    String abbreviationsDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\abbreviations.txt";
    
    
    String printQualifyingRacers(List<Racer> racers, int qualifyingRacersNumber) {
        int longestNameLength = getLongestNameLength(racers);
        int longestTeamNameLength = getLongestTeamNameLength(racers);
        StringBuilder results = new StringBuilder();
        
        for (int i = 0; i < racers.size(); i++) {
            Racer racer = racers.get(i);
            String formattedLapTime = formatLapTime(racer.getLapTime());
            String line = String.format(
                    "%1$2s. " + "%2$-" + longestNameLength + "s| " + "%3$-" + longestTeamNameLength + "s | %4$s\n",
                    i + 1, racer.getName(), racer.getTeam(), formattedLapTime);
            results.append(line);
            
            if(i == qualifyingRacersNumber - 1) {
                String underline = String.format("%" + (line.length() - 1) + "s", " ");
                underline = underline.replace(' ', '-') + "\n";
                results.append(underline);
            }
        }
        return results.toString();
    }

    @Test
    void test_printResult() {
        DataHandler dataHandler = new DataHandler();
        Map<String, Racer> racers = dataHandler.getRacers(abbreviationsDataPath);
        dataHandler.setStartTimes(racers, startDataPath);
        dataHandler.setEndTimes(racers, endDataPath);
        List<Racer> racersList = dataHandler.calculateLapTimes2(racers);
        dataHandler.rankRacers2(racersList);
        String results = printQualifyingRacers(racersList, 15);
        System.out.println(results);
    }
    
    String formatLapTime(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.toSeconds() % 60;
        long millis = duration.toMillis() % 1000;
        String output = String.format("%1$02d" + ":" + "%2$02d" + "." + "%3$03d", minutes, seconds, millis);
        return output;
    }
    int getLongestNameLength(List<Racer> racers) {
        String longestName = "";
        Optional<String> longestNameOp = racers.stream().map(r -> r.getName()).max(Comparator.comparingInt(String::length));
        if (longestNameOp.isPresent()) {
            longestName = longestNameOp.get();
        }
        return longestName.length();
    }
    
    int getLongestTeamNameLength(List<Racer> racers) {
        String longestTeamName = "";
        Optional<String> longestTeamNameOp = racers.stream().map(r -> r.getTeam()).max(Comparator.comparingInt(String::length)); 
        if (longestTeamNameOp.isPresent()) {
            longestTeamName = longestTeamNameOp.get();
        }
        return longestTeamName.length();
    }
}
