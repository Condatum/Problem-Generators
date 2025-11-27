import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.UUID;
//
/**
 * Defines the complete structure, image handling, and file persistence format
 * for all clothing items, designed as a standalone class.
 */
public class ClothingItem { // *** NO LONGER ABSTRACT ***

    // Core Attributes (Encapsulation)
    private String itemName;
    private String itemID;
    private String imagePath;
    private Category category;
    private ImageIcon image;

    // --- CONSTRUCTOR OVERLOADING ---

    /**
     * Constructor 1: Used when creating a NEW item (generates a unique ID).
     */
    public ClothingItem(String itemName, String imagePath, Category category) {
        // Generates a resource-intensive unique ID on creation
        this.itemID = UUID.randomUUID().toString();
        this.itemName = itemName;
        this.imagePath = imagePath;
        this.category = category;
        this.image = loadImage(imagePath);
    }

    /**
     * Constructor 2: Used when loading data from a file (accepts existing ID).
     */
    public ClothingItem(String itemName, String itemID, String imagePath, Category category) {
        this.itemName = itemName;
        this.itemID = itemID;
//        this.imagePath = imagePath;
        this.category = category;
        this.image = loadImage(imagePath);
    }

    // --- Accessor Methods (Getters) ---
    public String getItemName() { return itemName; }
    public String getItemID() { return itemID; }
    public String getImagePath() { return imagePath; }
    public Category getCategory() { return category; }
    public ImageIcon getImage() { return image; }

    public ImageIcon getScaledImage(int width, int height) {
        if (this.image != null) {
            Image scaled = this.image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }
        return null;
    }

    // --- MUTATOR METHOD (For image path changes) ---

    /**
     * Updates the stored imagePath and attempts to reload the image resource.
     * @param newPath The new file path for the image.
     * @return true if the image loaded successfully, false otherwise.
     */
    public boolean updateImagePathAndLoad(String newPath) {
        this.imagePath = newPath;
        ImageIcon tempImage = loadImage(newPath);

        if (tempImage != null) {
            this.image = tempImage;
            return true;
        } else {
            return false;
        }
    }

    // --- Private Image Loading Logic (TRY-CATCH Implementation) ---

    private ImageIcon loadImage(String path) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            if (img == null) {
                throw new IOException("File exists but is not a valid image format: " + path);
            }
            return new ImageIcon(img);
        } catch (IOException e) {
            System.err.println("❌ ERROR: Failed to load image for " + this.itemName + " at path: " + path);
            System.err.println("Reason: " + e.getMessage());
            return null;
        }
    }

    // --- CRUD/File Handling Method (CONCRETE IMPLEMENTATION) ---

    /**
     * Defines how this item's data should be formatted for saving to a file.
     */
    public String toFileString() {
        // Example format: CATEGORY,Item Name,ItemID,Path/To/Image.png
        return String.format("%s,%s,%s,%s",
                getCategory(),
                getItemName(),
                getItemID(),
                getImagePath());
    }
}