package FrontEnd;

import Classes.Category;
import Classes.ClothingItem;
import Classes.ClothingManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class Collection extends JFrame {
    // --- GUI Components ---
    private JPanel titlePanel;
    private JButton xButton;
    private JLabel icon;
    private JPanel collectionPanel;
    private JPanel tabsPanel;
    private JLabel wardrobeLabel;
    private JLabel collectionLabel;
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JPanel displayPanel;
    private JButton topButton;
    private JButton bottomButton;
    private JButton shoesButton;
    private JButton addClothesButton;
    private JButton removeClothesButton;
    private JPanel categoryNamePanel;
    private JLabel categoryNameLabel;

    // --- Logic Fields ---
    private JPanel galleryContent; // The panel INSIDE the displayPanel
    private JLabel selectedClotheLabel = null; // Tracks what user clicked
    private Category currentCategory = Category.TOP;
    private File storageFolder;

    // Colors for selection
    private final Color selectionColor = Color.RED;
    private final Color normalColor = new Color(150, 150, 150);

    public Collection() {
        // 1. Setup Data Folder
        storageFolder = new File(System.getProperty("user.dir") + "/liveImages/");
        if (!storageFolder.exists()) storageFolder.mkdirs();

        // 2. Initialize GUI Styling & Layouts
        setupCustomLayouts();
        applyRetroStyling();
        setupCategoryIcons();

        // 3. Initialize Listeners
        setupListeners();

        // 4. Load Default View (Tops)
        refreshGallery(Category.TOP);

        // 5. Final Window Setup
        if (collectionPanel != null) {
            add(collectionPanel);
        }
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);

        // Apply Global Font (PixelSans)
        RetroFactory.applyGlobalFont(this);
        setVisible(true);
    }

    /**
     * Injects the ScrollPane and WrapLayout into the displayPanel
     */
    private void setupCustomLayouts() {
        // Configure the displayPanel to hold a ScrollPane
        displayPanel.setLayout(new BorderLayout());

        // Create the inner panel that actually holds the images
        // Uses WrapLayout so images flow to the next line
        galleryContent = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 20));
        galleryContent.setOpaque(true);
        galleryContent.setBackground(Color.WHITE);

        // Create ScrollPane
        JScrollPane scrollPane = new JScrollPane(galleryContent,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Remove default border to avoid "double border" effect with displayPanel
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Apply Retro Scrollbar Look
        UI_Utilities.applyRetroScrollBarStyle(scrollPane);

        // Add to the GUI
        displayPanel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Applies Box Shadows and Retro Button styles using RetroFactory
     */
    private void applyRetroStyling() {
        // --- Box Shadow (Recessed Effect) ---
        RetroFactory.styleRecessedPanel(contentPanel);
        RetroFactory.styleRecessedPanel(categoryNamePanel);

        // Style displayPanel using factory, then force background to white for images
        RetroFactory.styleRecessedPanel(displayPanel);
        displayPanel.setBackground(Color.WHITE);

        // --- Retro Buttons ---
        JButton[] buttons = {
                topButton, bottomButton, shoesButton,
                addClothesButton, removeClothesButton, xButton
        };

        for (JButton btn : buttons) {
            RetroFactory.styleRetroButton(btn);
        }
    }

    // Applies Image Icons for Category Buttons
    private void setupCategoryIcons() {
        setBtnIcon(topButton, "/CategoryButtons/shirt1.png");
        setBtnIcon(bottomButton, "/CategoryButtons/pants2logo1.png");
        setBtnIcon(shoesButton, "/CategoryButtons/shoes2logo1.png");
    }

    private void setBtnIcon(JButton btn, String path) {
        if (btn == null) return;
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                ImageIcon original = new ImageIcon(imgURL);
                btn.setIcon(UI_Utilities.resize(original, 25, 25));
            } else {
                System.out.println("Icon not found: " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupListeners() {
        // --- Navigation Buttons ---
        topButton.addActionListener(e -> refreshGallery(Category.TOP));
        bottomButton.addActionListener(e -> refreshGallery(Category.BOTTOM));
        shoesButton.addActionListener(e -> refreshGallery(Category.FOOTWEAR));

        // --- Action Buttons ---
        addClothesButton.addActionListener(e -> {
            File file = selectImageModern();
            if (file != null) {
                addClothingLogic(file);
            }
        });

        // Switching Tabs
        wardrobeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // redirect to collection panel
                Wardrobe nextWindow = new Wardrobe();
                nextWindow.setVisible(true);
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(collectionLabel);

                // close wardrobe panel
                currentFrame.dispose();
            }
        });

        removeClothesButton.addActionListener(e -> removeClothingLogic());

        // --- Exit Button ---
        xButton.addActionListener(e -> { System.exit(0);});
    }

    /**
     * Clears the gallery and loads items for the specific category.
     */
    private void refreshGallery(Category category) {
        this.currentCategory = category;

        // Update the Label
        String catText = switch (category) {
            case TOP -> "Tops";
            case BOTTOM -> "Bottoms";
            case FOOTWEAR -> "Footwear";
        };
        categoryNameLabel.setText("Current Display: " + catText);

        // Clear previous items
        galleryContent.removeAll();
        selectedClotheLabel = null; // Reset selection

        // Load from Manager
        ClothingManager cm = ClothingManager.getInstance();
        ArrayList<ClothingItem> items = switch (category) {
            case TOP -> cm.getTops();
            case BOTTOM -> cm.getBottoms();
            case FOOTWEAR -> cm.getShoes();
        };

        // Create Labels
        for (ClothingItem item : items) {
            galleryContent.add(createClothingLabel(item));
        }

        // Refresh UI
        galleryContent.revalidate();
        galleryContent.repaint();
    }

    /**
     * Creates a clickable image label for a clothing item.
     */
    private JLabel createClothingLabel(ClothingItem item) {
        // Resize image to 150x150
        ImageIcon icon = UI_Utilities.resize(item.getClothesImage(), 150, 150);

        JLabel label = new JLabel(icon);
        label.putClientProperty("item", item); // Store data inside label
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(normalColor, 1));

        // Click Logic
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Deselect old
                if (selectedClotheLabel != null) {
                    selectedClotheLabel.setBorder(BorderFactory.createLineBorder(normalColor, 1));
                }
                // Select new
                selectedClotheLabel = label;
                selectedClotheLabel.setBorder(BorderFactory.createLineBorder(selectionColor, 3));
            }
        });

        return label;
    }

    // --- Logic: Add / Remove ---

    private File selectImageModern() {
        // Modern OS File Picker
        FileDialog fd = new FileDialog(this, "Upload Clothes", FileDialog.LOAD);
        fd.setFilenameFilter((dir, name) -> {
            String n = name.toLowerCase();
            return n.endsWith(".png") || n.endsWith(".jpg") || n.endsWith(".jpeg");
        });
        fd.setVisible(true);

        if (fd.getFile() != null) {
            return new File(fd.getDirectory(), fd.getFile());
        }
        return null;
    }

    private void addClothingLogic(File sourceFile) {
        try {
            // Create destination file with #TAG
            String ext = sourceFile.getName().substring(sourceFile.getName().lastIndexOf("."));
            String newName = sourceFile.getName().replace(ext, "") + "#" + currentCategory.name() + ext;
            File destFile = new File(storageFolder, newName);

            // Copy File
            java.nio.file.Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Notify Manager
            ClothingManager.getInstance().loadFromDisk();

            // Refresh UI
            refreshGallery(currentCategory);
            JOptionPane.showMessageDialog(this, "Item added successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding image: " + e.getMessage());
        }
    }

    private void removeClothingLogic() {
        if (selectedClotheLabel == null) {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.");
            return;
        }

        ClothingItem item = (ClothingItem) selectedClotheLabel.getClientProperty("item");
        File file = new File(item.getImagePath());

        if (file.exists() && file.delete()) {
            // Notify Manager
            ClothingManager.getInstance().loadFromDisk();
            refreshGallery(currentCategory);
            JOptionPane.showMessageDialog(this, "Item removed.");
        } else {
            JOptionPane.showMessageDialog(this, "Could not delete file from disk.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Collection();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error starting application: " + e.getMessage());
            }
        });
    }
}