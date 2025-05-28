package time;

public class TimeOfDayRange {
    private final GameTime startTime;
    private final GameTime endTime;

    public TimeOfDayRange(GameTime startTime, GameTime endTime) {
        if (startTime.isAfter(endTime)) throw new IllegalArgumentException("Start time must not be after end time.");
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static TimeOfDayRange ofInGameHours(int startHourInclusive, int endHourInclusive) {
        if (startHourInclusive < 0 || startHourInclusive > 23 || endHourInclusive < 0 || endHourInclusive > 23) throw new IllegalArgumentException("Hours must be between 0 and 23.");
        return new TimeOfDayRange(new GameTime(startHourInclusive, 0), new GameTime(endHourInclusive, 0));
    }

    public GameTime getStartTime() { return startTime; }

    public GameTime getEndTime() { return endTime; }

    public boolean isInRange(int hour) {
        if (hour < 0 || hour > 23) throw new IllegalArgumentException("In-game hour must be between 0 and 23.");
        return hour >= this.startTime.getHour() && hour <= this.endTime.getHour();
    }

    public boolean isAny() { return startTime.equals(0) && endTime.equals(23); }
}