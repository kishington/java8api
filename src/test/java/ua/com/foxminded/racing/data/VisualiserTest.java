package ua.com.foxminded.racing.data;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class VisualiserTest {
    
    Visualiser visualiser = new Visualiser();
    
    String startDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\start.log";
    String endDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\end.log";
    String abbreviationsDataPath = "\\workspace\\javaapiexercise\\src\\main\\resources\\abbreviations.txt";

    @Test
    void testFormatRaceResults() {
        DataHandler dataHandler = new DataHandler();
        Map<String, Racer> racers = dataHandler.getRacers(abbreviationsDataPath);
        dataHandler.setStartTimes(racers, startDataPath);
        dataHandler.setEndTimes(racers, endDataPath);
        List<Racer> racersList = dataHandler.calculateLapTimes(racers);
        dataHandler.rankRacers(racersList);
        String results = visualiser.visualiseRaceResults();
        System.out.println(results);
    }
    
    
    /*
     * @Test void test_longestNames() { DataHandler dataHandler = new DataHandler();
     * Map<String, Racer> tRacers = dataHandler.getRacers(abbreviationsDataPath);
     * int nameLength = dataHandler.getLongestNameLength(tRacers); int
     * teamNameLength = dataHandler.getLongestTeamNameLength(tRacers);
     * System.out.println("name: " + nameLength + "\n" + "team: " + teamNameLength);
     * }
     */
    
    @Test
    void test() {
        String formattedLapTime = "01:32.234";
        String name = "Daniel Ricciardo";
        String team = "RED BULL RACING TAG HEUER";
        int longestNameLength = 17;
        int longestTeamNameLength = 20;
        int i = 1;
        String line = String.format("%1$2s. " + "%2$-" + longestNameLength + "s| " + 
        "%3$-" + longestTeamNameLength + "s | %4$s\n", 
        i, name, team, formattedLapTime);
        System.out.println(line);
    }
}
