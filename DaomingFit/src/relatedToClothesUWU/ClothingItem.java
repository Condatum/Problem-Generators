package relatedToClothesUWU;

import javax.swing.*;

public class ClothingItem {
    private ImageIcon clothesImage;
    private Category category;

    public ClothingItem(ImageIcon image, Category category) {
        this.clothesImage = image;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public ImageIcon getClothesImage() {
        return clothesImage;
    }
}