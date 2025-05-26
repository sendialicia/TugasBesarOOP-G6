package org.example.time;

public class GameDate {
    private int day;
    private int seasonIndex;

    private static final String[] SEASONS = {"Spring", "Summer", "Fall", "Winter"};

    public GameDate(){
        this.day = 1;
        this.seasonIndex = 0;
    }

    public GameDate(GameDate other){
        this.day = other.getDay();
        this.seasonIndex = other.getSeasonIndex();
    }

    public void nextDay(){
        day++;
        if (day > 10){
            day = 1;
            seasonIndex = (seasonIndex + 1) % SEASONS.length;
        }
    }

    public String getSeason(){
        return SEASONS[seasonIndex];
    }

    public int getDay(){
        return day;
    }

    public int getSeasonIndex(){
        return seasonIndex;
    }

    @Override
    public String toString(){
        return SEASONS[seasonIndex] + " Day " + day;
    }
}
