package FileHandling;

import Classes.Outfit;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;

public class OutfitDataService {
    // Uses user.dir to find the root of the project safely
    private static final String DIRECTORY = System.getProperty("user.dir") + "/DaomingFit/saved_data/";
    private static final String FILE_PATH = DIRECTORY + "outfit_data.csv";
    private static final String TEMP_PATH = DIRECTORY + "temp.csv";
    private static final String DELIMITER = ",";

    public void saveOutfit(Outfit outfit) {
        if (outfit == null) return;

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

        checkAndCleanMissingItems(outfits);

        return outfits;
    }

    private void checkAndCleanMissingItems(ArrayList<Outfit> fits) {
        Iterator<Outfit> iterator = fits.iterator();
        boolean updateFile = false;

        while (iterator.hasNext()) {
            Outfit o = iterator.next();

            // Use Paths.get() to handle the absolute C:\ paths shown in your screenshot
            boolean missingTop = !Files.exists(Paths.get(o.getShirt()));
            boolean missingBottom = !Files.exists(Paths.get(o.getBottom()));
            boolean missingShoes = !Files.exists(Paths.get(o.getFootwear()));

            if (missingTop || missingBottom || missingShoes) {
                // Fixed the syntax for the message dialog
                JOptionPane.showMessageDialog(null,
                        "The outfit '" + o.getOutfitName() + "' contains a deleted clothing item.\n" +
                                "It will be removed from your saved outfits.",
                        "System Update",
                        JOptionPane.WARNING_MESSAGE);

                iterator.remove(); // Removes it from the list
                updateFile = true; // Flags that the file needs a single rewrite later
            }
        }

        if (updateFile) {
            try {
                rewriteFile(fits);
            } catch (IOException e) {
                System.err.println("Error updating files: " + e.getMessage());
            }
        }
    }

    public void removeOutfit(Outfit removeFit, ArrayList<Outfit> currentOutfits) throws IOException {
        currentOutfits.removeIf(o -> o.getOutfitName().equals(removeFit.getOutfitName()));

        rewriteFile(currentOutfits);

        System.out.println("Outfit removal successful and file synchronized.");
    }

    private void rewriteFile(ArrayList<Outfit> validOutfits) throws IOException {
        ensureDirectoryExists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TEMP_PATH))) {
            for (Outfit o : validOutfits) {
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
        Files.copy(tempPath, origPath, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(tempPath);
    }

    // Helper method to stop crashes if folder is missing
    private void ensureDirectoryExists() {
        File folder = new File(DIRECTORY);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}