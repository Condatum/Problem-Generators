package Classes;

import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class ClothingManager {

    // The Singleton Instance
    private static ClothingManager instance;

    // The Lists
    private ArrayList<ClothingItem> tops;
    private ArrayList<ClothingItem> bottoms;
    private ArrayList<ClothingItem> shoes;

    // Constructor
    private ClothingManager() {
        tops = new ArrayList<>();
        bottoms = new ArrayList<>();
        shoes = new ArrayList<>();
        loadFromDisk(); // Load images immediately when app starts
    }

    // Public Access Point
    public static ClothingManager getInstance() {
        if (instance == null) {
            instance = new ClothingManager();
        }
        return instance;
    }

    // scans liveImage folder and loads it to wardrobe
    public void loadFromDisk() {
        // Clear lists to prevent duplicates if called multiple times
        tops.clear();
        bottoms.clear();
        shoes.clear();

        File folder = new File(System.getProperty("user.dir") + "/liveImages/");
        if (!folder.exists()) {
            folder.mkdirs(); // Create folder if it doesn't exist
            return;
        }

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.isFile()) continue;

                // Identify category from file name (e.g., "shirt#TOP.png")
                Category cat = identifyCategory(file.getName());

                if (cat != null) {
                    // Create the item
                    ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                    ClothingItem item = new ClothingItem(file.getAbsolutePath(), icon, cat);

                    // Add to the correct list
                    addToList(item);
                }
            }
        }
    }

    private void addToList(ClothingItem item) {
        switch (item.getCategory()) {
            case TOP -> tops.add(item);
            case BOTTOM -> bottoms.add(item);
            case FOOTWEAR -> shoes.add(item);
        }
    }

    // Reads which category its from
    private Category identifyCategory(String filename) {
        if (filename.contains("#" + Category.TOP.name())) return Category.TOP;
        if (filename.contains("#" + Category.BOTTOM.name())) return Category.BOTTOM;
        if (filename.contains("#" + Category.FOOTWEAR.name())) return Category.FOOTWEAR;
        return null;
    }

    // Getters for Wardrobe.java
    public ArrayList<ClothingItem> getTops() { return tops; }
    public ArrayList<ClothingItem> getBottoms() { return bottoms; }
    public ArrayList<ClothingItem> getShoes() { return shoes; }
}