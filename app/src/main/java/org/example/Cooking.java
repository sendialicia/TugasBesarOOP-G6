package org.example;

import java.util.List;
import java.util.Map;

public class Cooking extends Action {
    private Map<Recipe, Boolean> recipes;
    
    // Constructor
    public Cooking(String name, String desc){
        super(name, desc);
    }

    // Methods
    @Override
    public String getName(){
        return name;
    }

    @Override
    public String getDesc(){
        return desc;
    }

    @Override
    public boolean canPerform(Player player){
        return false;
    }

    @Override
    public int getTime(){
        return 0;
    }

    @Override
    public int getEnergyRequired(Player player){
        return 0;
    }

    @Override
    public List<Item> getItemsRequired(){
        return null;
    }

    @Override
    public void doAction(Player player){
    }

    public void setRecipe(boolean isAccessible){
    }
}