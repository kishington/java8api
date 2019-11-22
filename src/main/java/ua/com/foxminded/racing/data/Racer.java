package ua.com.foxminded.racing.data;

import java.time.LocalTime;

public class Racer {
    String name;
    String abbreviation;
    String lapTime;
    LocalTime startTime;
    LocalTime endTime;
    
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
    public String getLapTime() {
        return lapTime;
    }
    public void setLapTime(String lapTime) {
        this.lapTime = lapTime;
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
}
