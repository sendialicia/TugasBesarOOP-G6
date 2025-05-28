package entity.npc;

import entity.Entity;
import items.Items;
import java.util.HashMap;
import java.util.Map;
import main.GamePanel;
import time.GameClock;
import time.GameClockSnapshot;

public abstract class NPC extends Entity {
    private String name;
    private int heartPoints;
    private final Items[] lovedItems;
    private final Items[] likedItems;
    private final Items[] hatedItems;
    private String relationshipStatus;
    private Map<String, GameClockSnapshot> changeLog = new HashMap<>();

    protected final Map<String, Items> itemsMap;
    private static final int MIN_HEART_POINTS = 0;
    private static final int MAX_HEART_POINTS = 150;

    private GameClockSnapshot gameClockSnapshot = new GameClockSnapshot(GameClock.getInstance());

    public NPC(String name, GamePanel gp) {
        super(gp);
        this.itemsMap = new HashMap<>();
        this.name = name;
        this.heartPoints = 0;
        this.relationshipStatus = "Single";
        this.lovedItems = mapLovedItems(itemsMap);
        this.likedItems = mapLikedItems(itemsMap);
        this.hatedItems = mapHatedItems(itemsMap);
        this.changeLog.put(relationshipStatus, gameClockSnapshot);
    }

    public String getName() { return name; }
    public Items[] getLovedItems() { return lovedItems; }
    public Items[] getLikedItems() { return likedItems; }
    public Items[] getHatedItems() { return hatedItems; }
    public String getRelationshipStatus() { return relationshipStatus; }
    public int getHeartPoints() { return heartPoints; }
    public Map<String, GameClockSnapshot> getChangeLog() { return changeLog; }

    public void setName(String name) { this.name = name; }
    public void setRelationshipStatus(String status) { 
        this.relationshipStatus = status;
        changeLog.put(status, gameClockSnapshot);
    }

    public void setHeartPoints(int points) {
        if (points < MIN_HEART_POINTS) this.heartPoints = MIN_HEART_POINTS;
        else if (points > MAX_HEART_POINTS) this.heartPoints = MAX_HEART_POINTS;
        else this.heartPoints = points;
    }

    public void addHeartPoints(int points) { setHeartPoints(this.heartPoints + points); }
    public void removeHeartPoints(int points) { setHeartPoints(this.heartPoints - points); }

    protected abstract Items[] mapLovedItems(Map<String, Items> itemsMap);
    protected abstract Items[] mapLikedItems(Map<String, Items> itemsMap);
    protected abstract Items[] mapHatedItems(Map<String, Items> itemsMap);
}
