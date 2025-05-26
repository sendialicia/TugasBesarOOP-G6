package org.example.legacy;

import java.util.List;

public class NPC {
    private String name;
    private String bachelorStatus;
    private int maxHeartPoints;
    private int heartPoints;
    private List<Item> lovedItems;
    private List<Item> likedItems;
    private List<Item> hatedItems;
    private String relationshipStatus;
    private String job;
    private Location lived;

    // Constructor
    public NPC(
        String name,
        String bachelorStatus,
        List<Item> lovedItems,
        List<Item> likedItems,
        List<Item> hatedItems,
        String relationshipStatus,
        String job,
        Location lived
    ) {}

    // Getter
    public String getName() { return null; }
    public int getHeartPoints() { return 0; }
    public List<Item> getLovedItems() { return null; }
    public List<Item> getLikedItems() { return null; }
    public List<Item> getHatedItems() { return null; }
    public String getRelationshipStatus() { return null; }
    public String getJob() { return null; }

    // Setter
    public void setName(String name) {}
    public void setHeartPoints(int heartPoints) {}
    public void setRelationshipStatus(String relationshipStatus) {}
}