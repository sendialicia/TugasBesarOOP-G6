package items.crops;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import items.Items;

public class CropsLoader {

    public static List<Crops> load() {
        String fullPath = "res/files/crops.json";
        Items items = new Items(null);

        try {
            String jsonContent = Files.readString(Paths.get(fullPath));

            Type listType = new TypeToken<List<CropsData>>() {}.getType();
            List<CropsData> cropsDataList = new Gson().fromJson(jsonContent, listType);

            List<Crops> cropsList = new ArrayList<>();
            for (CropsData data : cropsDataList) {

                BufferedImage image = items.setup(data.imagePath);
                Crops crop = new Crops(data.name, data.harvestedAmount, data.sellPrice, data.buyPrice, image, data.description);
                cropsList.add(crop);
            }

            return cropsList;
        } catch (JsonSyntaxException | IOException e) {
            throw new RuntimeException("Failed to load or parse JSON file at: " + fullPath, e);
        }
    }
}
