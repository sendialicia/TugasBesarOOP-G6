package recipe;

import items.Inventory;
import items.Items;
import java.util.Map;

public class Recipe {
    private String name;
    private Map<Items, Integer> ingredients;
    private Items result;

    public Recipe(String name, Map<Items, Integer> ingredients, Items result) {
        this.name = name;
        this.ingredients = ingredients;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public Map<Items, Integer> getIngredients() {
        return ingredients;
    }

    public Items getResult() {
        return result;
    }

    public boolean canCraftRecipe(Recipe recipe, Inventory playerInventory) {
        Map<Items, Integer> required = recipe.getIngredients();

        for (Map.Entry<Items, Integer> entry : required.entrySet()) {
            Items ingredient = entry.getKey();
            int requiredQty = entry.getValue();

            int playerQty = playerInventory.getItemQuantity(ingredient);
            if (playerQty < requiredQty) {
                return false;
            }
        }

        return true;
    }
}

