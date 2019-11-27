package ua.com.foxminded.racing.data;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Visualiser {
    private static final int NUMBER_OF_QUALIFYING_RACERS = 15;
    
    public String visualiseRaceResults() {
        DataHandler dataHandler = new DataHandler();
        List<Racer> racers = dataHandler.prepareDataForVisualisation();
        
        int longestNameLength = getLongestNameLength(racers);
        int longestTeamNameLength = getLongestTeamNameLength(racers);
       
        StringBuilder results = new StringBuilder();
        
        for (int i = 0; i < racers.size(); i++) {
            Racer racer = racers.get(i);
            String formattedLapTime = formatLapTime(racer.getLapTime());
            String line = String.format(
                    "%1$2s. " + "%2$-" + longestNameLength + "s | " + "%3$-" + longestTeamNameLength + "s | %4$s\n",
                    i + 1, racer.getName(), racer.getTeam(), formattedLapTime);
            results.append(line);
            
            if(i == NUMBER_OF_QUALIFYING_RACERS - 1) {
                String underline = String.format("%" + (line.length() - 1) + "s", " ");
                underline = underline.replace(' ', '-') + "\n";
                results.append(underline);
            }
        }
        return results.toString();
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
    
    String formatLapTime(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.toSeconds() % 60;
        long millis = duration.toMillis() % 1000;
        String output = String.format("%1$02d" + ":" + "%2$02d" + "." + "%3$03d", minutes, seconds, millis);
        return output;
    }
}
