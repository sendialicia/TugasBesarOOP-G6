package org.example;

class Player {
    private String name;
    private String gender;
    private int energy = 0; //{Max = 100, Min = -20}
    private String farmName = null;
    private NPC partner = null;
    private int gold = 0; //{Min = 0}
    private Inventory inventory = new Inventory();
    private Point playerLoc = new Point(0, 0);

    // Constructor
    public Player(String name, String gender){
        this.name = name;
        this.gender = gender;
    }

    // Getters
    public String getName(){
        return name;
    }

    public String getGender(){
        return gender;
    }

    public int getEnergy(){
        return energy;
    }

    public String getFarmName(){
        return farmName;
    }

    public NPC getPartner(){
        return partner;
    }

    public int getGold(){
        return gold;
    }

    public Inventory getInventory(){
        return inventory;
    }

    public Point getLocation(){
        return playerLoc;
    }

    // Setters
    public void setName(String newName){
        this.name = newName;
    }

    public void setGender(String newGender){
        this.gender = newGender;
    }

    public void setEnergy(int newEnergy){
        if (newEnergy > 100){
            this.energy = 100;
        } 
        else if (newEnergy < -20){
            this.energy = -20;
        } 
        else{
            this.energy = newEnergy;
        }
    }

    public void setFarmName(String newFarmName){
        this.farmName = newFarmName;
    }

    public void setPartner(NPC partner){
        this.partner = partner;
    }

    public void setGold(int newGold){
        if (newGold >= 0){
            this.gold = newGold;
        }
    }

    public void setInventory(Inventory newInventory){
        this.inventory = newInventory;
    }

    public void setLocation(Point newLocation){
        this.playerLoc = newLocation;
    }
}