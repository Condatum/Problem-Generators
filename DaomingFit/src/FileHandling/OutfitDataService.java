package FileHandling; // Make sure this matches the folder name in your src!

import Classes.Outfit;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class OutfitDataService {
    // Uses user.dir to find the root of the project safely
    private static final String DIRECTORY = System.getProperty("user.dir") + "/DaomingFit/saved_data/";
    private static final String FILE_PATH = DIRECTORY + "outfit_data.csv";
    private static final String TEMP_PATH = DIRECTORY + "temp.csv";
    private static final String DELIMITER = ",";

    public void saveOutfit(Outfit outfit) {
        // Validation: Don't save if any data is missing
        if (outfit == null) return;

        // 1. Ensure the directory exists before writing
        ensureDirectoryExists();

        String line = outfit.getOutfitName() + DELIMITER +
                outfit.getShirt() + DELIMITER +
                outfit.getBottom() + DELIMITER +
                outfit.getFootwear();

        try (FileWriter file = new FileWriter(FILE_PATH, true)) {
            file.write(line);
            file.write("\n");
        } catch (IOException e) {
            System.out.println("Outfit save failed: " + e.getMessage());
        }
    }

    public ArrayList<Outfit> loadOutfits() {
        ArrayList<Outfit> outfits = new ArrayList<>();
        Path filePath = Paths.get(FILE_PATH);

        if (!Files.exists(filePath)) {
            return outfits; // Return empty list if file doesn't exist yet
        }

        try (BufferedReader br = new BufferedReader(Files.newBufferedReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] outfitParts = line.split(DELIMITER);

                // Ensure we have exactly 4 parts (Name, Top, Bottom, Shoe)
                if (outfitParts.length == 4) {
                    outfits.add(new Outfit(outfitParts[0], outfitParts[1], outfitParts[2], outfitParts[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("Outfits loading unsuccessful: " + e.getMessage());
        }

        return outfits;
    }

    public void removeOutfit(Outfit removeFit, ArrayList<Outfit> currentOutfits) throws IOException {
        ensureDirectoryExists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TEMP_PATH))) {
            for (Outfit o : currentOutfits) {
                // Skip the outfit we want to remove
                if (o.getOutfitName().equals(removeFit.getOutfitName())) {
                    continue;
                }

                String line = o.getOutfitName() + DELIMITER +
                        o.getShirt() + DELIMITER +
                        o.getBottom() + DELIMITER +
                        o.getFootwear();
                bw.write(line);
                bw.write("\n");
            }
        }

        Path origPath = Paths.get(FILE_PATH);
        Path tempPath = Paths.get(TEMP_PATH);

        // Replace original with temp
        Files.copy(tempPath, origPath, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(tempPath);
        System.out.println("Outfit removal successful");
    }

    // Helper method to stop crashes if folder is missing
    private void ensureDirectoryExists() {
        File folder = new File(DIRECTORY);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}