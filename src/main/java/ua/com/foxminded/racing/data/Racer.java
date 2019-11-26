package ua.com.foxminded.racing.data;

import java.time.Duration;
import java.time.LocalTime;

public class Racer implements Comparable<Racer> {
    String name;
    String abbreviation;
    Duration lapTime;
    LocalTime startTime;
    LocalTime endTime;
    int position;
    
    @Override
    public int compareTo(Racer racer) {
        long racerLapTimeInMillis = racer.lapTime.toMillis();
        long thisRacerLapTimeInMillis = this.lapTime.toMillis();
        return (int) (thisRacerLapTimeInMillis - racerLapTimeInMillis);
    }
    
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
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
    public Duration getLapTime() {
        return lapTime;
    }
    public void setLapTime(Duration lapTime) {
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
