package Classes;

public class Outfit {
        String shirt, bottom, footwear, outfitName;
        public Outfit(String outfitName, String shirt, String bottom, String footwear) {
            this.shirt = shirt;
            this.bottom = bottom;
            this.footwear = footwear;
            this.outfitName = outfitName;
        }
        public String getOutfitName() { return outfitName; }
        public String getBottom() { return bottom; }
        public String getShirt() { return shirt; }
        public String  getFootwear() { return footwear; }
}
