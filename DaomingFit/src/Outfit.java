import javax.swing.ImageIcon;

public class Outfit {

    // Attributes (Encapsulation)
    private String outfitName;

    // The 3 slots for the outfit items
    private ClothingItem currentTop;
    private ClothingItem currentBottom;
    private ClothingItem currentFootwear;

    /**
     * Constructor: Initializes the outfit with a name.
     * The clothing slots start as null (empty).
     */
    public Outfit(String outfitName) {
        this.outfitName = outfitName;
        this.currentTop = null;
        this.currentBottom = null;
        this.currentFootwear = null;
    }

    // --- SMART MANAGEMENT METHODS (Logic to handle placement) ---

    /**
     * Adds OR Replaces an item in the outfit.
     * The method automatically detects the category of the item 
     * and places it in the correct slot.
     */
    public void addClothingItem(ClothingItem item) {
        if (item == null) return; // Safety check

        switch (item.getCategory()) {
            case TOP:
                this.currentTop = item;
                break;
            case BOTTOM:
                this.currentBottom = item;
                break;
            case FOOTWEAR:
                this.currentFootwear = item;
                break;
        }
    }

    /**
     * Removes an item based on the category provided.
     * Useful when the user clicks a "Clear" button for a specific slot.
     */
    public void removeClothingItem(Category category) {
        switch (category) {
            case TOP:
                this.currentTop = null;
                break;
            case BOTTOM:
                this.currentBottom = null;
                break;
            case FOOTWEAR:
                this.currentFootwear = null;
                break;
        }
    }

    // --- GUI HELPER METHODS (Lessening MainGUI's Job) ---

    /**
     * Retrieves the ImageIcon for a specific category.
     * Handles null checks internally so the GUI doesn't crash if a slot is empty.
     * * @param category The slot to check.
     * @return The ImageIcon if the item exists, or null if the slot is empty.
     */
    public ImageIcon getIconFor(Category category) {
        ClothingItem item = getItem(category);

        if (item != null) {
            return item.getImage();
        }
        return null; // GUI can check for null to show a "placeholder" image
    }

    /**
     * Retrieves the ClothingItem object for a specific category.
     */
    public ClothingItem getItem(Category category) {
        switch (category) {
            case TOP: return currentTop;
            case BOTTOM: return currentBottom;
            case FOOTWEAR: return currentFootwear;
            default: return null;
        }
    }

    // --- BASIC GETTERS/SETTERS ---

    public String getOutfitName() {
        return outfitName;
    }

    public void setOutfitName(String outfitName) {
        this.outfitName = outfitName;
    }

    /**
     * Checks if the outfit is fully complete (has all 3 items).
     * Useful for enabling/disabling a "Save" button in the GUI.
     */
    public boolean isComplete() {
        return currentTop != null && currentBottom != null && currentFootwear != null;
    }

    // --- PERSISTENCE ---

    /**
     * Formats the outfit data for saving to a file.
     * Saves the Outfit Name and the IDs of the items used.
     * Format: OUTFIT_NAME,TOP_ID,BOTTOM_ID,FOOTWEAR_ID
     */
    public String toFileString() {
        String topID = (currentTop != null) ? currentTop.getItemID() : "null";
        String botID = (currentBottom != null) ? currentBottom.getItemID() : "null";
        String shoeID = (currentFootwear != null) ? currentFootwear.getItemID() : "null";

        return String.format("%s,%s,%s,%s", outfitName, topID, botID, shoeID);
    }
}