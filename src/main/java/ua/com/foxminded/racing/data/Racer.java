package ua.com.foxminded.racing.data;

import java.time.Duration;
import java.time.LocalTime;

public class Racer {
    private String name;
    private String abbreviation;
    private String team;
    private LocalTime startTime;
    private LocalTime endTime;
    private Duration lapTime;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAbbreviation() {
        return abbreviation;
    }
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
    
    public String getTeam() {
        return team;
    }
    public void setTeam(String team) {
        this.team = team;
    }
    
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    public Duration getLapTime() {
        return lapTime;
    }
    public void setLapTime(Duration lapTime) {
        this.lapTime = lapTime;
    }
}
