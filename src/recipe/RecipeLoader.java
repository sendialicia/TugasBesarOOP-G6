package recipe;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import items.Items;
import items.ItemFactory;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeLoader {
    private static final String RECIPE_FILE_PATH = "res/files/recipes.json";
    private ItemFactory itemFactory = new ItemFactory();

    public RecipeLoader() {
        this.itemFactory.loadAll();
    }

    public List<Recipe> loadRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(RECIPE_FILE_PATH)) {
            Type listType = new TypeToken<List<RecipeData>>() {}.getType();
            List<RecipeData> recipeDataList = gson.fromJson(reader, listType);

            for (RecipeData data : recipeDataList) {
                Map<Items, Integer> ingredients = new HashMap<>();
                for (Map.Entry<String, Integer> entry : data.ingredients.entrySet()) {
                    Items item = itemFactory.get(entry.getKey());
                    if (item != null) {
                        ingredients.put(item, entry.getValue());
                    } else {
                        System.out.println("Unknown ingredient item: " + entry.getKey());
                    }
                }

                Items resultItem = itemFactory.get(data.result);
                if (resultItem != null) {
                    recipes.add(new Recipe(data.name, ingredients, resultItem));
                } else {
                    System.out.println("Unknown result item: " + data.result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipes;
    }
}
