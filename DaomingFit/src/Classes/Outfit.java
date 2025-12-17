package Classes;

public class Outfit {
    // Applied encapsulation by setting the fields private and adding getters
    private String shirt;
    private String bottom;
    private String footwear;
    private String outfitName;

    public Outfit(String outfitName, String shirt, String bottom, String footwear) {
        this.outfitName = outfitName;
        this.shirt = shirt;
        this.bottom = bottom;
        this.footwear = footwear;
    }

    public String getOutfitName() { return outfitName; }
    public String getShirt() { return shirt; }
    public String getBottom() { return bottom; }
    public String getFootwear() { return footwear; }

    @Override
    public String toString() {
        return outfitName;
    }
}