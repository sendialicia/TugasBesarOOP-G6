package time;

public class GameDate {
    private int originDay;
    private int day;
    private int season;

    private static final String[] SEASON = {"Spring", "Summer", "Fall", "Winter"};

    public GameDate(){
        this.originDay = 1;
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

    public int getOriginDay(){ return originDay; }
    public int getDay(){ return day; }
    public int getSeason(){ return season; }
    public String getSeasonString(){ return SEASON[season]; }

    public void nextDay(){
        day++;
        originDay++;
        if (day > 10){
            day = 1;
            season = (season + 1) % SEASON.length;
        }
    }

    @Override
    public String toString(){
        return "Season " + this.getSeasonString() + " Day " + this.day;
    } // pisahin season ama date
}
