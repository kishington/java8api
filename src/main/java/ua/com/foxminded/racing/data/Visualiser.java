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
    
   
    String printQualifyingRacers(Map<String,Racer> racers, int qualifyingRacersNumber) {
        int longestNameLength = getLongestNameLength(racers);
        int longestTeamNameLength = getLongestTeamNameLength(racers);
        
        StringBuilder results = new StringBuilder();
        int i = 1;
        Iterator<Map.Entry<String, Racer>> itr = racers.entrySet().iterator();
        while (i <= qualifyingRacersNumber) {
            Map.Entry<String, Racer> entry = itr.next();
            Racer racer = entry.getValue();
            String formattedLapTime = formatLapTime(racer.getLapTime());
            String line = String.format("%1$2s. " + "%2$-" + longestNameLength + "s| " + 
            "%3$-" + longestTeamNameLength + "s | %4$s\n", 
            i, racer.getName(), racer.getTeam(), formattedLapTime);
            results.append(line);
            i++;
        }
        return results.toString();
    }
    
    String printQualifyingRacers2(List<Racer> racers, int qualifyingRacersNumber) {
        int longestNameLength = getLongestNameLength2(racers);
        int longestTeamNameLength = getLongestTeamNameLength2(racers);
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
        String results = printQualifyingRacers2(racersList, 15);
        System.out.println(results);
    }
    
    String formatLapTime(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.toSeconds() % 60;
        long millis = duration.toMillis() % 1000;
        String output = String.format("%1$02d" + ":" + "%2$02d" + "." + "%3$03d", minutes, seconds, millis);
        return output;
    }
    int getLongestNameLength2(List<Racer> racers) {
        String longestName = "";
        Optional<String> longestNameOp = racers.stream().map(r -> r.getName()).max(Comparator.comparingInt(String::length));
        if (longestNameOp.isPresent()) {
            longestName = longestNameOp.get();
        }
        return longestName.length();
    }
    
    int getLongestTeamNameLength2(List<Racer> racers) {
        String longestTeamName = "";
        Optional<String> longestTeamNameOp = racers.stream().map(r -> r.getTeam()).max(Comparator.comparingInt(String::length)); 
        if (longestTeamNameOp.isPresent()) {
            longestTeamName = longestTeamNameOp.get();
        }
        return longestTeamName.length();
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
    
    @Test
    void test2() {
        Map<String,String> gfg = new HashMap<String,String>(); 
        
         
        gfg.put("GFG", "geeksforgeeks.org"); 
        gfg.put("Practice", "practice.geeksforgeeks.org"); 
        gfg.put("Code", "code.geeksforgeeks.org"); 
        gfg.put("Quiz", "quiz.geeksforgeeks.org"); 
          
        int i = 0;
        for (String name : gfg.keySet())  
        { 
            i++; 
            String url = gfg.get(name); 
            System.out.println("Key = " + name + ", Value = " + url + " " + i); 
        } 
    }
}
