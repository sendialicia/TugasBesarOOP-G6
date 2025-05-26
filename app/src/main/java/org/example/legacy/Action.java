package org.example.legacy;

import java.util.List;

public abstract class Action {
    protected String name;
    protected String desc;

    // Constructor
    public Action(String name, String desc){
        this.name = name;
        this.desc = desc;
    }

    // Getters
    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }

    // Abstract Methods
    public abstract boolean canPerform(Player player);
    public abstract int getTime();
    public abstract int getEnergyRequired(Player player);
    public abstract List<Item> getItemsRequired();
    public abstract void doAction(Player player);
}