package relatedToClothesUWU;

import Classes.ClothingItem;

public class Outfit {
    private ClothingItem top;
    private ClothingItem bottom;
    private ClothingItem footwear;

    public String outfitName;

    public Outfit(ClothingItem top, ClothingItem bottom, ClothingItem footwear, String outfitName) {
        this.outfitName = outfitName;
        this.top = top;
        this.bottom = bottom;
        this.footwear = footwear;
    }




    public ClothingItem getTop() {
        return top;
    }

    public void setTop(ClothingItem top) {
        this.top = top;
    }

    public ClothingItem getBottom() {
        return bottom;
    }

    public void setBottom(ClothingItem bottom) {
        this.bottom = bottom;
    }

    public ClothingItem getFootwear() {
        return footwear;
    }

    public void setFootwear(ClothingItem footwear) {
        this.footwear = footwear;
    }
}
