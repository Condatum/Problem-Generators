package Classes;

import javax.swing.*;

public class ClothingItem {
    private String imagePath;
    private ImageIcon clothesImage;
    private Category category;

    public ClothingItem(String imagePath, ImageIcon image, Category category) {
        this.imagePath = imagePath;
        this.clothesImage = image;
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }
    public Category getCategory() {
        return category;
    }
    public ImageIcon getClothesImage() {
        return clothesImage;
    }
}