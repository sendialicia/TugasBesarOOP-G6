package time;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class WeatherGenerator {
    private final GameDate gameDate;
    private Set<Integer> rain;
    
    private final Random random = new Random();

    public WeatherGenerator(GameDate gameDate){
        this.gameDate = gameDate;
        generateRain();
    }

    public WeatherGenerator(WeatherGenerator other){
        this.gameDate = other.getGameDate();
        this.rain = other.getRain();
    }
    
    public GameDate getGameDate(){ return gameDate; }
    public Set<Integer> getRain(){ return rain; }
    public String getWeatherToday() { return rain.contains(gameDate.getDay()) ? "Rainy" : "Sunny"; }

    public void generateRain(){
        rain = new HashSet<>();
        while (rain.size() < 2) rain.add(random.nextInt(10) + 1);
    }

    public void seasonChange(){ generateRain(); }
}
