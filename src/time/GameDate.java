package time;

public class GameDate {
    private int day;
    private int season;

    private static final String[] SEASON = {"Spring", "Summer", "Fall", "Winter"};

    public GameDate(){
        this.day = 1;
        this.season = 0;
    }

    public GameDate(int day, int season){
        this.day = day;
        this.season = season;
    }

    public GameDate(GameDate other){
        this.day = other.getDay();
        this.season = other.getSeason();
    }

    public int getDay(){ return day; }
    public int getSeason(){ return season; }
    public String getSeasonString(){ return SEASON[season]; }

    public void nextDay(){
        day++;
        if (day > 10){
            day = 1;
            season = (season + 1) % SEASON.length;
        }
    }
}
