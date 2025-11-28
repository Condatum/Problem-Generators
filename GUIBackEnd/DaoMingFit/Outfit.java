package DaoMingFit;

public class Outfit {
    private String outfitName;
    private String top, bottom, footwear;
    public Outfit(String outfitName, String top, String bottom, String footwear)
    { this.outfitName = outfitName; this.top = top; this.bottom = bottom; this.footwear = footwear;
        System.out.println(outfitName + " " + top + " " + bottom + " " + footwear);
    }
    public String getTop(){ return top; }
    public String getBottom(){ return bottom; }
    public String getFootwear(){ return footwear; }
    public String getOutfitName(){ return outfitName; } // for customization & labels
}
