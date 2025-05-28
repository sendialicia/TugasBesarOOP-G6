package time;

public class GameTime {
    private int hour;
    private int min;

    public static final GameTime MIN = new GameTime(0, 0);
    public static final GameTime MAX = new GameTime(23, 59);

    public GameTime(){
        this.hour = 0;
        this.min = 0;
    }

    public GameTime(int hour, int min){
        if (hour < 0 || hour > 23 || min < 0 || min > 59) throw new IllegalArgumentException("Invalid time!");
        this.hour = hour;
        this.min = min;
    }

    public GameTime(GameTime other){
        this.hour = other.getHour();
        this.min = other.getMinute();
    }
    
    public static GameTime parse(String timeString){
        String[] parts = timeString.split(":");
        int hour = Integer.parseInt(parts[0]);
        int min = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
        return new GameTime(hour, min);
    }

    public int getHour(){ return hour; }
    public int getMinute(){ return min; }

    public void advanceMinutes(int mins){
        min += mins;
        if (min >= 60) {
            hour += min / 60;
            min %= 60;
        }

        if (hour >= 24) hour %= 24;
    }

    public boolean isBefore(GameTime other){ return hour < other.getHour() || (hour == other.getHour() && min > other.getMinute()); }
    public boolean isAfter(GameTime other){ return hour > other.getHour() || (hour == other.getHour() && min < other.getMinute()); }

    @Override
    public String toString(){ return String.format("Time : %02d:%02d", hour, min); }
}
