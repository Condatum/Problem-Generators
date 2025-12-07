package CSV;

import Classes.Outfit;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class OutfitDataService {
    private static String filename = "src/CSV/outfit_data.csv";
    private static String delimiter = ",";

    public void saveOutfit(Outfit outfit){
        String line = outfit.getOutfitName() + delimiter + outfit.getShirt() + delimiter + outfit.getBottom() + delimiter + outfit.getFootwear();

        try(FileWriter file = new FileWriter(filename, true)) {
            file.write(line);
            file.write("\n");
        } catch (IOException e) {
            System.out.println("Outfit saved unsuccessful");
        }

    }

    public ArrayList<Outfit> loadOutfits() {
        ArrayList<Outfit> outfits = new ArrayList<>();
        Path filePath = Paths.get(filename);

        if (!Files.exists(filePath)) {
            return outfits; // Return empty list if file doesn't exist yet
        }

        try(BufferedReader br = new BufferedReader(Files.newBufferedReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String outfitParts[] = line.split(delimiter);
                if(line.trim().isEmpty()) continue;

                if(outfitParts.length == 4){
                    outfits.add(new Outfit(outfitParts[0], outfitParts[1], outfitParts[2], outfitParts[3]));
                }
            }
        } catch(IOException e){
            System.out.println("Outfits loading unsuccessful");
        }

        return outfits;
    }

    public void removeOutfit(Outfit removeFit, ArrayList<Outfit> currentOutfits) throws IOException {
        String tempFile = "src/CSV/temp.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            for(Outfit o : currentOutfits){
                if(o.getOutfitName().equals(removeFit.getOutfitName())){
                    continue; // Skip the outfit to be removed
                } else {
                    String line = o.getOutfitName() + delimiter + o.getShirt() + delimiter + o.getBottom() + delimiter + o.getFootwear();
                    bw.write(line);
                    bw.write("\n");
                }
            }
        }


        Path origpath = Paths.get(filename);
        Path temppath = Paths.get(tempFile);

        Files.copy(temppath, origpath, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(temppath);
        System.out.println("Outfit removal successful");
    }

}
