package ua.com.foxminded.racing;

import ua.com.foxminded.racing.data.Visualiser;

public class RaceResults {

    public static void main(String[] args) {
        Visualiser visualiser = new Visualiser();
        String results = visualiser.visualiseRaceResults();
        System.out.print(results);
    }
}
