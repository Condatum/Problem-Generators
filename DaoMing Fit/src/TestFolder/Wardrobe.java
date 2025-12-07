package TestFolder;

import Classes.Outfit;
import CSV.OutfitDataService;

import java.io.IOException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Wardrobe extends JFrame{
    private JPanel wardrobePanel;
    private JPanel titlePanel;
    private JButton xButton;
    private JLabel icon;
    private JPanel contentPanel;
    private JPanel clothesPanel;
    private JButton rightTopButton;
    private JButton leftTopButton;
    private JLabel bottomLabel;
    private JLabel topLabel;
    private JLabel shoesLabel;
    private JPanel createOutfitPanel;
    private JButton rightBottomButton;
    private JButton rightShoeButton;
    private JButton leftBottomButton;
    private JButton leftShoeButton;
    private JPanel settingsPanel;
    private JList outfitList;
    private JScrollPane outfitListPane;
    private JPanel tabsPanel;
    private JLabel wardrobeLabel;
    private JLabel collectionLabel;
    private JButton loadOutfitButton;
    private JButton removeOutfitButton;
    private JTextField inputOutfitField;
    private JButton saveButton;

    // ----------------------
    // temporary way of loading shirt. change it once other classes are connected to the gui
    // when adding shirt images, size must be 250 x 250
    private String[] shirts = {
            "/TestFolder/images/Tops/shirt_1.jpg",
            "/TestFolder/images/Tops/shirt_2.jpg"
    };

    private String[] bottoms = {
            "/TestFolder/images/Bottoms/pants_1.png",
            "/TestFolder/images/Bottoms/pants_2.png"
    };

    private String[] shoes = {
            "/TestFolder/images/Shoes/shoes_1.png",
            "/TestFolder/images/Shoes/shoes_2.png"
    };

    //---------------

    private ArrayList<Outfit> Outfits = new ArrayList<>();
    private OutfitDataService outfitData = new OutfitDataService();
    private DefaultListModel<String> outfitNames = new DefaultListModel<>();

    private int shirt_i = 0, bottom_i = 0, shoe_i = 0;

    private String browseShirts(int i){
        shirt_i += i;
        if (shirt_i >= shirts.length) shirt_i = 0;
        else if (shirt_i < 0) shirt_i = shirts.length-1;
        return shirts[shirt_i];
    }
    private String browseBottom(int i){
        bottom_i += i;
        if (bottom_i >= bottoms.length) bottom_i = 0;
        else if (bottom_i < 0) bottom_i = bottoms.length-1;
        return bottoms[bottom_i];
    }

    private String browseShoe(int i){
        shoe_i += i;
        if (shoe_i >= shoes.length) shoe_i = 0;
        else if (shoe_i < 0) shoe_i = shoes.length-1;
        return shoes[shoe_i];
    }

    private int findCloth(String clothType, String imgPath){
        switch(clothType){
            case "shoe": for(int i = 0; i<shoes.length; i++) if(shoes[i].matches(imgPath)) return i; break;
            case "top": for(int i = 0; i<shirts.length; i++) if(shirts[i].matches(imgPath)) return i; break;
            case "bottom": for(int i = 0; i<bottoms.length; i++) if(bottoms[i].matches(imgPath)) return i; break;
        }
        return -1;
    }

    private void setOutfit(int outfitIndex){
        Outfit outfit = Outfits.get(outfitIndex);
        setShirtLabel(outfit.getShirt());
        setBottomLabel(outfit.getBottom());
        setShoeLabel(outfit.getFootwear());

        //para maapil ug update ang buttons
        shoe_i = findCloth("shoe", outfit.getFootwear());
        shirt_i = findCloth("top", outfit.getShirt());
        bottom_i = findCloth("bottom", outfit.getBottom());
    }

    private void setShirtLabel(String shirtImgPath){
        topLabel.setIcon(new ImageIcon(getClass().getResource(shirtImgPath)));
        topLabel.revalidate();
        topLabel.repaint();
    }
    private void setBottomLabel(String bottomImgPath){
        bottomLabel.setIcon(new ImageIcon(getClass().getResource(bottomImgPath)));
        bottomLabel.revalidate();
        bottomLabel.repaint();
    }

    private void setShoeLabel(String shoeImgPath){
        shoesLabel.setIcon(new ImageIcon(getClass().getResource(shoeImgPath)));
        shoesLabel.revalidate();
        shoesLabel.repaint();
    }

    public Wardrobe(){
        // load outfits from file on startup
        Outfits.addAll(outfitData.loadOutfits());

        // border effect for panels
        for(Outfit o : Outfits) outfitNames.addElement(o.getOutfitName());
        outfitList.setModel(outfitNames);

        Color gray = new Color(198, 198, 198);
        Color white = new Color(255,255,255);

        applyPanelRecessBorder(contentPanel, gray);
        applyPanelRecessBorder(clothesPanel, white);

        createOutfitPanel.setBackground(gray);
        createOutfitPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        settingsPanel.setBackground(gray);
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        setShirtLabel(shirts[shirt_i]);
        setBottomLabel(bottoms[bottom_i]);
        setShoeLabel(shoes[shoe_i]);

        // ----------- SETTING BUTTONS (switched to ActionListener tungod sa setter function nako na add

        xButton.addActionListener(e -> { System.exit(0);});
        leftTopButton.addActionListener(e -> { setShirtLabel(browseShirts(-1)); });
        leftBottomButton.addActionListener(e -> { setBottomLabel(browseBottom(-1)); });
        leftShoeButton.addActionListener(e -> { setShoeLabel(browseShoe(-1)); }); // <-- remove comment & implement func when naa nay file para shoe
        rightTopButton.addActionListener(e -> { setShirtLabel(browseShirts(1)); });
        rightBottomButton.addActionListener(e -> { setBottomLabel(browseBottom(1)); });
        rightShoeButton.addActionListener(e -> { setShoeLabel(browseShoe(1)); }); // <-- remove comment & implement func when naa nay file para shoe
        saveButton.addActionListener(e -> {
            String outfitName = inputOutfitField.getText();
            if (outfitName.isBlank()) JOptionPane.showMessageDialog(
                    null, // Parent component (null for a default frame)
                    "Field is empty!", // The message to display
                    "Input Error", // The title of the dialog box
                    JOptionPane.ERROR_MESSAGE // The type of message (determines the icon)
            );
            else if (outfitNames.contains(outfitName)) JOptionPane.showMessageDialog(
                    null, // Parent component (null for a default frame)
                    "Outfit name already exists!", // The message to display
                    "Input Error", // The title of the dialog box
                    JOptionPane.ERROR_MESSAGE // The type of message (determines the icon)
            );
            else {
                Outfit newFit = new Outfit(outfitName, shirts[shirt_i], bottoms[bottom_i], shoes[shoe_i]);
                outfitData.saveOutfit(newFit);

                Outfits.add(newFit);
                outfitNames.addElement(outfitName);

                inputOutfitField.setText("");
                JOptionPane.showMessageDialog(null,  outfitName + " Saved Successfully!");
            }
        });

        loadOutfitButton.addActionListener(e -> {
            try{ setOutfit(outfitList.getSelectedIndex()); }
            catch(IndexOutOfBoundsException n){ JOptionPane.showMessageDialog( //para way error ig load outfit
                    null, // Parent component (null for a default frame)
                    "No outfit selected", // The message to display
                    "Selection Error", // The title of the dialog box
                    JOptionPane.ERROR_MESSAGE // The type of message (determines the icon)
            );}
            outfitList.clearSelection();
            outfitList.repaint();
        });

        removeOutfitButton.addActionListener(e -> {
            int selectedIndex = outfitList.getSelectedIndex();

            // Check if an item is actually selected
            if (selectedIndex != -1) {
                // 1. Get the data before removing anything
                Outfit outfitToRemove = Outfits.get(selectedIndex);

                try {
                    // 2. Perform the critical file operation first
                    outfitData.removeOutfit(outfitToRemove, Outfits);

                    // 3. If file operation succeeds, update the in-memory data structures
                    outfitNames.remove(selectedIndex); // Removes from the ListModel, which updates the JList
                    Outfits.remove(selectedIndex); // Removes from the underlying ArrayList

                    // 4. Show success message
                    JOptionPane.showMessageDialog(null, outfitToRemove.getOutfitName() + " removed successfully!");

                } catch (IOException ex) {
                    // 5. If the file operation failed, show the user what happened
                    JOptionPane.showMessageDialog(null, "Error removing outfit: Could not update the data file.", "File Error", JOptionPane.ERROR_MESSAGE);
                    // Print the stack trace for debugging purposes
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No outfit selected");
            }

            // Clear selection regardless of success/failure
            outfitList.clearSelection();
        });
        outfitList.addListSelectionListener(e -> {});

        // do not edit
        add(wardrobePanel);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //ignore this chat
    private void applyPanelRecessBorder(JPanel panel, Color color) {
        final int BORDER_WEIGHT = 3;

        Border bevelBorder = BorderFactory.createBevelBorder(
                BevelBorder.LOWERED,
                Color.WHITE,        // Highlight Outer (Top/Left) - White still looks good here
                Color.WHITE,        // Highlight Inner (Top/Left)
                Color.BLACK,        // Shadow Outer (Bottom/Right) - Black still looks good here
                Color.BLACK         // Shadow Inner (Bottom/Right)
        );

        Border weightBorder = new EmptyBorder(
                BORDER_WEIGHT, BORDER_WEIGHT, BORDER_WEIGHT, BORDER_WEIGHT
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        weightBorder,
                        bevelBorder
                )
        );
        // Optional: Set the retro background color
        panel.setBackground(color);
    }
    //ignore this too chat
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
        } catch (Exception e) {
            e.getMessage();
        }

        Color retroGray = new Color(192, 192, 192); // Standard Windows 95 gray
        Color highlight = Color.WHITE;

        // Set the overall color theme for the scrollbar components
        UIManager.put("ScrollBar.background", retroGray);
        UIManager.put("ScrollBar.track", highlight);      // The area behind the scrollbar thumb
        UIManager.put("ScrollBar.thumb", retroGray);


        final int BORDER_WEIGHT = 3;
        Border customBorder = BorderFactory.createCompoundBorder(
                new EmptyBorder(BORDER_WEIGHT, BORDER_WEIGHT, BORDER_WEIGHT, BORDER_WEIGHT),
                BorderFactory.createBevelBorder(
                        BevelBorder.RAISED,
                        Color.WHITE, Color.WHITE,
                        Color.BLACK, Color.BLACK
                )
        );

        SwingUtilities.invokeLater(() -> new Wardrobe());
    }
}