package items.miscellaneous;

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

public class MiscellaneousLoader {

    public static List<Miscellaneous> load() {
        String fullPath = "res/files/miscellaneous.json"; 
        Items items = new Items(null);

        try {
            String jsonContent = Files.readString(Paths.get(fullPath));

            Type listType = new TypeToken<List<MiscellaneousData>>() {}.getType();
            List<MiscellaneousData> miscellaneousDataList = new Gson().fromJson(jsonContent, listType);

            List<Miscellaneous> miscellaneousList = new ArrayList<>();
            for (MiscellaneousData data : miscellaneousDataList) {
                BufferedImage image = items.setup(data.imagePath);
                Miscellaneous miscellaneous = new Miscellaneous(data.name, data.sellPrice, data.buyPrice, image, data.description);
                miscellaneousList.add(miscellaneous);
            }

            return miscellaneousList;
        } catch (JsonSyntaxException | IOException e) {
            throw new RuntimeException("Failed to load or parse JSON file at: " + fullPath, e);
        }
    }
}
