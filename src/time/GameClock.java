package time;

public class GameClock implements Runnable {
    private static GameClock instance;

    private final GameTime time = new GameTime(6,0);
    private final GameDate date = new GameDate();
    private final WeatherGenerator weather = new WeatherGenerator(date);

    private boolean running = true;
    private boolean paused = false;

    private GameClock(){}
    public static synchronized GameClock getInstance(){
        if (instance == null) instance = new GameClock();
        return instance;
    }

    @Override
    public void run(){
        while (running){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            synchronized (this) {
                if(!paused) advanceTime(5);
            }
        }
    }

    public synchronized void advanceTime(int minutes) {
        for (int i = 0; i < minutes; i++) {
            int oldHour = time.getHour();
            int oldMinute = time.getMinute();

            time.advanceMinutes(1);

            if (passedMidnight(oldHour, oldMinute, time.getHour(), time.getMinute())) {
                date.nextDay();
                if (date.getDay() == 1) weather.seasonChange();
            }
        }
    }

    private boolean passedMidnight(int oldH, int oldM, int newH, int newM) { return (oldH > newH) || (oldH == 23 && oldM > newM); }

    public GameTime getTime() { return time; }
    public GameDate getDate() { return date; }
    public WeatherGenerator getWeather(){ return weather; }

    public synchronized boolean isNight() { return (time.getHour() >= 18 || time.getHour() < 6); }
    public synchronized int getCurrentNightCycle() { return time.getHour() < 6 ? date.getDay() - 1 : date.getDay(); }

    public synchronized int getMinutesSince(GameClockSnapshot snapshot) {
        int currentTotalMinutes = getTotalMinutesSinceOrigin(this.getDate(), this.getTime());
        int snapshotTotalMinutes = getTotalMinutesSinceOrigin(snapshot.getDate(), snapshot.getTime());
        return currentTotalMinutes - snapshotTotalMinutes;
    }

    private int getTotalMinutesSinceOrigin(GameDate date, GameTime time) {
        return (date.getSeason() * 10 + date.getOriginDay()) * 24 * 60 + time.getHour() * 60 + time.getMinute();
    }

    public synchronized String getFormattedTime() { return time.toString(); }
    public synchronized String getFormattedDate() { return date.toString(); }
    public synchronized String getFormattedWeather() { return weather.toString(); }
    
    public synchronized void pause() { paused = true; }
    public synchronized void resume() { paused = false; }
    public void stopClock() { running = false; }
}
