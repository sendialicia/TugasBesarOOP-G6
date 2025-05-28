package time;

public class GameClockSnapshot {
    private final GameTime time;
    private final GameDate date;
    private final WeatherGenerator weather;

    public GameClockSnapshot(GameClock clock) {
        this.time = new GameTime(clock.getTime());
        this.date = new GameDate(clock.getDate());
        this.weather = new WeatherGenerator(clock.getWeather());
    }

    public GameTime getTime() { return time; }
    public GameDate getDate() { return date; }
    public WeatherGenerator getWeather() { return weather; }
}
