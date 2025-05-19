package org.example;

import java.util.Map;

public class Recipe {
    private String id;
    private String name;
    private Map<Item, Integer> ingredients;
    private String desc;

    // Constructor
    public Recipe(String id, String name, Map<Item, Integer> ingredients, String desc){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.desc = desc;
    }

    // Methods
    public String getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Map<Item, Integer> getIngredients(){
        return ingredients;
    }

    public String getDesc(){
        return desc;
    }
}