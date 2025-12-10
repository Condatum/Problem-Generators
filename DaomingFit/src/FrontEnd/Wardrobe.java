package FrontEnd;

import Classes.ClothingManager;
import Classes.ClothingItem;
import Classes.Outfit;
import FileHandling.OutfitDataService;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class Wardrobe extends JFrame {
    private JPanel wardrobePanel;
    private JPanel titlePanel;
    private JButton xButton;
    private JLabel icon;
    private JPanel contentPanel;
    private JPanel clothesPanel;

    // Navigation Buttons
    private JButton rightTopButton;
    private JButton leftTopButton;
    private JLabel topLabel;

    private JButton rightBottomButton;
    private JButton leftBottomButton;
    private JLabel bottomLabel;

    private JButton rightShoeButton;
    private JButton leftShoeButton;
    private JLabel shoesLabel;

    private JPanel createOutfitPanel;
    private JPanel settingsPanel;
    private JList<String> outfitList; // Typed JList
    private JScrollPane outfitListPane;
    private JPanel tabsPanel;
    private JLabel wardrobeLabel;
    private JLabel collectionLabel;
    private JButton loadOutfitButton;
    private JButton removeOutfitButton;
    private JTextField inputOutfitField;
    private JButton saveButton;
    private JLabel outfitDisplayLabel;
    private JLabel outfitDetailsLabel;
    private JLabel saveNewFitLabel;

    // --- Data Management ---
    private ArrayList<Outfit> Outfits = new ArrayList<>();
    private OutfitDataService outfitData = new OutfitDataService();
    private DefaultListModel<String> outfitNames = new DefaultListModel<>();

    // Indices to track which item is currently showing
    private int shirt_i = 0;
    private int bottom_i = 0;
    private int shoe_i = 0;

    public Wardrobe() {
        // 1. Load Saved Outfits
        Outfits.addAll(outfitData.loadOutfits());
        for (Outfit o : Outfits) {
            outfitNames.addElement(o.getOutfitName());
        }
        outfitList.setModel(outfitNames);

        // Refresh Images from the Manager
        updateDisplayImages();

        // Setup Button Listeners
        setupListeners();

        // 5. Window Setup
        if (wardrobePanel != null) {
            add(wardrobePanel);
        }
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);

        RetroFactory.applyGlobalFont(this);
        applyRetroStyles();

        setVisible(true);
    }

    // --- Core Logic: Linking to ClothingManager ---

    private void updateDisplayImages() {
        ClothingManager cm = ClothingManager.getInstance();

        // --- Update Top ---
        ArrayList<ClothingItem> tops = cm.getTops();
        if (!tops.isEmpty()) {
            // Ensure index loops around
            if (shirt_i >= tops.size()) shirt_i = 0;
            if (shirt_i < 0) shirt_i = tops.size() - 1;

            topLabel.setIcon(UI_Utilities.resize(tops.get(shirt_i).getClothesImage(), 200, 210));
            topLabel.setText(""); // Remove text if image exists
        } else {
            topLabel.setIcon(null);
            topLabel.setText("No Tops Added");
        }

        // --- Update Bottom ---
        ArrayList<ClothingItem> bottoms = cm.getBottoms();
        if (!bottoms.isEmpty()) {
            if (bottom_i >= bottoms.size()) bottom_i = 0;
            if (bottom_i < 0) bottom_i = bottoms.size() - 1;

            bottomLabel.setIcon(UI_Utilities.resize(bottoms.get(bottom_i).getClothesImage(), 200, 220));
            bottomLabel.setText("");
        } else {
            bottomLabel.setIcon(null);
            bottomLabel.setText("No Bottoms");
        }

        // --- Update Shoes ---
        ArrayList<ClothingItem> shoes = cm.getShoes();
        if (!shoes.isEmpty()) {
            if (shoe_i >= shoes.size()) shoe_i = 0;
            if (shoe_i < 0) shoe_i = shoes.size() - 1;

            shoesLabel.setIcon(UI_Utilities.resize(shoes.get(shoe_i).getClothesImage(), 200, 150));
            shoesLabel.setText("");
        } else {
            shoesLabel.setIcon(null);
            shoesLabel.setText("No Shoes");
        }
    }

    private void setupListeners() {
        xButton.addActionListener(e -> { System.exit(0);});

        // Navigation
        leftTopButton.addActionListener(e -> { shirt_i--; updateDisplayImages(); });
        rightTopButton.addActionListener(e -> { shirt_i++; updateDisplayImages(); });

        leftBottomButton.addActionListener(e -> { bottom_i--; updateDisplayImages(); });
        rightBottomButton.addActionListener(e -> { bottom_i++; updateDisplayImages(); });

        leftShoeButton.addActionListener(e -> { shoe_i--; updateDisplayImages(); });
        rightShoeButton.addActionListener(e -> { shoe_i++; updateDisplayImages(); });

        // Operations
        saveButton.addActionListener(e -> saveOutfitAction());
        loadOutfitButton.addActionListener(e -> loadOutfitAction());
        removeOutfitButton.addActionListener(e -> removeOutfitAction());

        // Tab switching
        collectionLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // redirect to collection panel
                Collection nextWindow = new Collection();
                nextWindow.setVisible(true);
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(collectionLabel);

                // close wardrobe panel
                currentFrame.dispose();
            }
        });
    }

    // --- Actions ---

    private void saveOutfitAction() {
        ClothingManager cm = ClothingManager.getInstance();

        // Validation: Prevent saving if lists are empty
        if (cm.getTops().isEmpty() || cm.getBottoms().isEmpty() || cm.getShoes().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cannot save incomplete outfit!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String outfitName = inputOutfitField.getText();
        if (outfitName.isBlank()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (outfitNames.contains(outfitName)) {
            JOptionPane.showMessageDialog(this, "Outfit name already exists!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save the FILE PATHS of the current selection
        String topPath = cm.getTops().get(shirt_i).getImagePath();
        String botPath = cm.getBottoms().get(bottom_i).getImagePath();
        String shoePath = cm.getShoes().get(shoe_i).getImagePath();

        Outfit newFit = new Outfit(outfitName, topPath, botPath, shoePath);
        outfitData.saveOutfit(newFit);

        Outfits.add(newFit);
        outfitNames.addElement(outfitName);
        inputOutfitField.setText("");
        JOptionPane.showMessageDialog(this, outfitName + " Saved Successfully!");
    }

    private void loadOutfitAction() {
        int index = outfitList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "No outfit selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Outfit outfit = Outfits.get(index);

        // Find the index of the saved file path in our current Manager
        // This ensures the visuals update to match the saved outfit
        shirt_i = findIndexByPath(ClothingManager.getInstance().getTops(), outfit.getShirt());
        bottom_i = findIndexByPath(ClothingManager.getInstance().getBottoms(), outfit.getBottom());
        shoe_i = findIndexByPath(ClothingManager.getInstance().getShoes(), outfit.getFootwear());

        updateDisplayImages();
        outfitList.clearSelection();
    }

    private void removeOutfitAction() {
        int index = outfitList.getSelectedIndex();
        if (index != -1) {
            Outfit outfitToRemove = Outfits.get(index);
            try {
                outfitData.removeOutfit(outfitToRemove, Outfits);
                outfitNames.remove(index);
                Outfits.remove(index);
                JOptionPane.showMessageDialog(this, "Removed successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error removing outfit file.", "File Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No outfit selected");
        }
        outfitList.clearSelection();
    }

    // --- Helpers ---

    private int findIndexByPath(ArrayList<ClothingItem> list, String path) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getImagePath().equals(path)) {
                return i;
            }
        }
        return 0; // Default to 0 if the original file was deleted
    }

    private void applyRetroStyles() {
        // Apply styles using RetroFactory
        RetroFactory.styleRecessedPanel(contentPanel);

        JButton[] buttons = {
                xButton, saveButton, loadOutfitButton, removeOutfitButton,
                rightTopButton, leftTopButton, rightBottomButton, leftBottomButton, rightShoeButton, leftShoeButton
        };

        // Style all buttons
        for(JButton btn : buttons){
            RetroFactory.styleRetroButton(btn);
        }

        // --- Font Sizing ---
        outfitDisplayLabel.setFont(RetroFactory.getPixelFont(28F));
        outfitDetailsLabel.setFont(RetroFactory.getPixelFont(28F));
        saveNewFitLabel.setFont(RetroFactory.getPixelFont(28F));

        // Set font size for buttons
        saveButton.setFont(RetroFactory.getPixelFont(20F));
        loadOutfitButton.setFont(RetroFactory.getPixelFont(20F));
        removeOutfitButton.setFont(RetroFactory.getPixelFont(20F));

        // Set font size in JList
        outfitList.setFont(RetroFactory.getPixelFont(20F));

        // Set font size in JTextField
        inputOutfitField.setFont(RetroFactory.getPixelFont(20F));
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Wardrobe());
    }
}