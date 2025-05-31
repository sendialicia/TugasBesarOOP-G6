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

    @Override
    public String toString() {
        return "GameClockSnapshot {" +
            "\n  Time: " + time.getHour() + ":" + String.format("%02d", time.getMinute()) +
            "\n  Date: Day " + date.getDay() + ", Season: " + date.getSeason() +
            "\n  Weather: " + weather.getWeatherToday() +
            "\n}";
    }
}