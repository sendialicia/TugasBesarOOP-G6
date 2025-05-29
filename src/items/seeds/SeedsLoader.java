package items.seeds;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import items.Items;

public class SeedsLoader {

    public static List<Seeds> load() {
        String fullPath = "res/files/seeds.json"; 
        Items items = new Items(null);

        try {
            String jsonContent = Files.readString(Paths.get(fullPath));

            Type listType = new TypeToken<List<SeedsData>>() {}.getType();
            List<SeedsData> seedsDataList = new Gson().fromJson(jsonContent, listType);

            List<Seeds> seedsList = new ArrayList<>();
            for (SeedsData data : seedsDataList) {
                BufferedImage image = items.setup(data.imagePath);
                Seeds seed = new Seeds(data.name, data.season, data.harvestDays, data.buyPrice, image, data.description);
                seedsList.add(seed);
            }

            return seedsList;
        } catch (JsonSyntaxException | IOException e) {
            throw new RuntimeException("Failed to load or parse JSON file at: " + fullPath, e);
        }
    }
}
