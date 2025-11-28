package DaoMingFit;

import java.util.ArrayList;
import java.util.List;

public class Wardrobe {
    private ArrayList<String> tops = new ArrayList<>(List.of("top 1", "top 2", "top 3"));
    private ArrayList<String> bottoms = new ArrayList<>(List.of("bottom 1", "bottom 2", "bottom 3"));
    private ArrayList<String> footwears = new ArrayList<>(List.of("footwear 1", "footwear 2", "footwear 3"));

    private ArrayList<Outfit> outfits = new ArrayList<>();

    private int top_i = 0, bot_i = 0, fw_i = 0;

    public String browseTop(int i)
    {   top_i += i;
        if (top_i >= tops.size()) top_i = 0;
        else if (top_i < 0) top_i = tops.size()-1;
        return tops.get(top_i); }
    public String browseBottom(int i)
    {   bot_i += i;
        if (bot_i >= bottoms.size()) bot_i = 0;
        else if (bot_i < 0) bot_i = bottoms.size()-1;
        return bottoms.get(bot_i); }
    public String browseFW(int i)
    {   fw_i += i;
        if (fw_i >= footwears.size()) fw_i = 0;
        else if (fw_i < 0) fw_i = footwears.size()-1;
        return footwears.get(fw_i); }
    public ArrayList<Outfit> getOutfits(){ return outfits; }

    public void addOutfit(Outfit newOutfit){
        outfits.add(newOutfit);
        System.out.println(outfits.size());}
    public Outfit loadOutfit(int outfitIndex){
        return outfits.get(outfitIndex);
    }
}
