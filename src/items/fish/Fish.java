package items.fish;

import entity.Player;
import items.Edible;
import items.Items;
import java.awt.image.BufferedImage;
import time.TimeOfDayRange;

public class Fish extends Items implements Edible {
    private String rarity;
    private String[] seasons;
    private String[] weathers;
    private String[] locations;
    private TimeOfDayRange[] timeOfDayRanges;

    private static final int ENERGY = 1;

    public Fish(String name, String rarity, String[] seasons, String[] weathers, String[] locations, TimeOfDayRange[] timeOfDayRanges, BufferedImage image, String description){
        super(name, null, null, image, description);
        this.rarity = rarity;
        this.seasons = seasons;
        this.weathers = weathers;
        this.locations = locations;
        this.timeOfDayRanges = timeOfDayRanges;
        calculatePrice();
    }

    public String getRarity(){ return rarity; }
    public String[] getSeasons(){ return seasons; }
    public String[] getWeathers(){ return weathers; }
    public String[] getLocations(){ return locations; }
    public TimeOfDayRange[] getTimeOfDayRanges(){ return timeOfDayRanges; }

    private void calculatePrice() {
        int seasonCount = isAny(seasons) ? 4 : seasons.length;
        int weatherCount = isAny(weathers) ? 2 : weathers.length;
        int locationCount = isAny(locations) ? 4 : locations.length;
        int timeRangeCount = isAnyTime() ? 1 : timeOfDayRanges.length;

        if (seasonCount == 0 || weatherCount == 0 || locationCount == 0 || timeRangeCount == 0) super.setSellPrice(0);

        int rarityPrice;
        if (rarity.equals("Regular")) rarityPrice = 5;
        else if (rarity.equals("Common")) rarityPrice = 10;
        else rarityPrice = 25;

        double price = (4.0 / seasonCount) * (24.0 / timeRangeCount) * (2.0 / weatherCount) * (4.0 / locationCount) * rarityPrice;
        super.setSellPrice((int) price);
    }

    private boolean isAny(String[] array){
        for (String s : array) if (s.equalsIgnoreCase("any")) return true;
        return false;
    }

    private boolean isAnyTime(){
        for (TimeOfDayRange t : timeOfDayRanges) if (t.isAny()) return true;
        return false;
    }

    private boolean containsIgnoreCase(String[] array, String value) {
        for (String s : array) if (s.equalsIgnoreCase(value)) return true;
        return false;
    }

    private boolean isInTimeOfDay(int hour) {
        for (TimeOfDayRange range : timeOfDayRanges) if (range.isAny() || range.isInRange(hour)) return true;
        return false;
    }

    private boolean matches(String[] array, String value) { return isAny(array) || containsIgnoreCase(array, value); }

    public boolean isAvailable(String season, String weather, String location, int hour) {
        return matches(seasons, season) && matches(weathers, weather) && matches(locations, location) && isInTimeOfDay(hour);
    }

    @Override
    public void onEat(Player player){ player.addEnergy(ENERGY);}

    @Override
    public int getEnergy(){ return ENERGY; }
}
