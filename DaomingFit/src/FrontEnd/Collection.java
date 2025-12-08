package FrontEnd;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;


// classes related to clothing is in here
import relatedToClothesUWU.*;

public class Collection extends JFrame {
    //field for POTANGINAAAAAAAAAAAA i mean, arraylist of Clothing
    ArrayList<ClothingItem> clothesData;
    // fields for removingShit
    private JLabel selectedClotheLabel = null;
    private final Color originalBorderColor = new Color(150, 150, 150);
    private final Color selectionBorderColor = Color.RED;
    JPanel galleryPanel;
    private JPanel wardrobePanel;
    private JPanel titlePanel;
    private JButton xButton;
    private JLabel icon;
    private JPanel contentPanel;
    private JPanel collectionPanel; // placeholder from GUI form
    private JLabel TitleText;
    private JButton removeButton;
    private JButton addButton;
    private JButton rightButton;
    private JButton leftButton;
    private JLabel clotheLabel;
    private JButton topButton;
    private JButton bottomButton;
    private JButton footwearButton;
    private JPanel Categories;
    private Category selectedCategory = Category.TOP;
    private Font PixelSans;
    private File storageFolder;
    public Collection() {
        // --- Initialize font first ---
        try {
            InputStream is = getClass().getResourceAsStream("/PixelifySans-Regular.ttf");
            PixelSans = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(is)).deriveFont(14f);
        } catch (FontFormatException | IOException e) {
            System.out.println("Font not found...");
            PixelSans = TitleText != null ? TitleText.getFont() : new Font("Arial", Font.PLAIN, 14);
        }
        // --- Set font safely ---
        setAllFonts();
        // --- Initialize data and gallery panel ---
        clothesData = new ArrayList<>();
        galleryPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 20));
        // --- Run your existing setup methods ---
        storageFolder = new File(System.getProperty("user.dir") + "/liveImages/");
        storageFolder.mkdirs();
        InitializeScrollableContainer();
        InitializePanelDesign();
        // --- Button listeners ---
        addButton.addActionListener(e -> {
            File selected = selectImageFromFileExplorer();
            if (selected != null) {
                AddClothing(selected, new ImageIcon(selected.getAbsolutePath()));
            }
        });
        if (removeButton != null) {
            removeButton.addActionListener(e -> RemoveClothing());
        }
        /*
        Initializing the categories panel to hold all buttons...
         */
        categoriesDesignInit();
        // Make sure window is visible
        setVisible(true);
        // Initialize Storage folder
//        storageFolder = new File("liveImages/");
//        storageFolder.mkdirs();// create if missing

    }
    private void categoriesDesignInit(){
        Categories.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(600, 1));
        Categories.add(spacer, 3);  // index 3 = before removeButton
        Categories.revalidate();
        Categories.repaint();
        Categories.setOpaque(false);
        ImageIcon topIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/CategoryButtons/shirt1.png")));
        ImageIcon bottomIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/CategoryButtons/pants2logo1.png")));
        ImageIcon footIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/CategoryButtons/shoes2logo1.png")));
        Image scaled = topIcon.getImage().getScaledInstance(33, 33, Image.SCALE_SMOOTH);
        Image scaled2 = bottomIcon.getImage().getScaledInstance(33, 33, Image.SCALE_SMOOTH);
        Image scaled3 = footIcon.getImage().getScaledInstance(33, 33, Image.SCALE_SMOOTH);
        topButton.setIcon(new ImageIcon(scaled));
        bottomButton.setIcon(new ImageIcon(scaled2));
        footwearButton.setIcon(new ImageIcon(scaled3));
        applyFixedButtonSizes();  // Change button size in this method // Specifically just for the buttons under categories panel
    }

    private void applyPanelRecessBorder(JPanel panel, Color color) {
        if (panel == null) return;
        final int BORDER_WEIGHT = 3;
        Border bevelBorder = BorderFactory.createBevelBorder(
                BevelBorder.LOWERED,
                Color.WHITE,        // Highlight Outer (Top/Left)
                Color.WHITE,        // Highlight Inner (Top/Left)
                Color.BLACK,        // Shadow Outer (Bottom/Right)
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
        panel.setBackground(color);
    }

    /**
     * Kong naa kai e add nga component sa forms
     * Just setFont to PixelSans
     */
    private void setAllFonts(){
        if (TitleText != null) TitleText.setFont(PixelSans);
        if (addButton != null) addButton.setFont(PixelSans);
        if (removeButton != null) removeButton.setFont(PixelSans);
        if (topButton != null) topButton.setFont(PixelSans);
        if (bottomButton != null) bottomButton.setFont(PixelSans);
        if (footwearButton != null) footwearButton.setFont(PixelSans);
        // ... Add font to a component here, just do it like ^^ //
    }

    private void applyButtonBorder(JButton button) {
        if (button == null) return; // ADDED NULL CHECK

        final int BORDER_WEIGHT = 3;
        Border bevelBorder = BorderFactory.createBevelBorder(
                BevelBorder.RAISED,
                Color.WHITE, Color.WHITE,
                Color.BLACK, Color.BLACK
        );
        Border weightBorder = new EmptyBorder(
                BORDER_WEIGHT, BORDER_WEIGHT, BORDER_WEIGHT, BORDER_WEIGHT
        );
        button.setBorder(
                BorderFactory.createCompoundBorder(
                        weightBorder,
                        bevelBorder
                )
        );
        button.setBackground(new Color(198, 198, 198));
    }

    public ImageIcon resize(ImageIcon icon, int w, int h) {
        if (icon == null) return null; // ADDED NULL CHECK

        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
    private void adderToPanel(ClothingItem item) {
        if (item == null || item.getClothesImage() == null) return; // ADDED NULL CHECK
        ImageIcon resized = resize(item.getClothesImage(), 180, 180);
        if (resized == null) return;
        // 1. Create the JLabel
        JLabel label = new JLabel(resized);
        label.putClientProperty("ClothingItem", item);        // --- END ATTACHMENT ---
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(originalBorderColor));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Deselect the previous item
                if (selectedClotheLabel != null) {
                    selectedClotheLabel.setBorder(
                            BorderFactory.createLineBorder(originalBorderColor)
                    );
                }

                selectedClotheLabel = label;
                selectedClotheLabel.setBorder(
                        BorderFactory.createLineBorder(selectionBorderColor, 3)
                );
            }
        });

        galleryPanel.add(label);
    }

    private void InitializeScrollableContainer(){
        preloadExistingImages();

        if (collectionPanel == null) {
            System.out.println("Error: collectionPanel is null!");
            return;
        }

        // Make collectionPanel act as a container for the scroll pane
        collectionPanel.setLayout(new BorderLayout());

        // Create a separate galleryPanel that uses WrapLayout (wraps and computes preferred height)
        galleryPanel.setOpaque(true);
        galleryPanel.setBackground(Color.WHITE);

        // Fill galleryPanel with resized JLabels

        // Put the galleryPanel inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(galleryPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                applyRetroScrollBarStyle(scrollPane); // Applie retro ScrollPane

        // Add the scroll pane into the collectionPanel placeholder
        collectionPanel.removeAll();
        collectionPanel.add(scrollPane, BorderLayout.CENTER);

        // Ensure layout updates
        collectionPanel.revalidate();
        collectionPanel.repaint();
        // --------------------------------------------------------------------------------------------//
        // ---- // SETTING UP THE COLLECTION PANEL TO BE SCROLLABLE AND ADDING SAMPLE IMAGES // ---- //
        // --------------------------------------------------------------------------------------------//
    }
    private void InitializePanelDesign(){
        if (contentPanel == null || collectionPanel == null) {
            System.out.println("Error: Panels are null. Check your form initialization.");
            return;
        }
        Color gray = new Color(198, 198, 198);// border effect for panels
        Color white = new Color(255,255,255);// border effect for panels
        applyPanelRecessBorder(contentPanel, gray);
        applyPanelRecessBorder(collectionPanel, white);

        //polished category system to be dynamic and not hardcoded.
        if (topButton != null) {
            topButton.addActionListener(e -> {
                selectedCategory = Category.TOP;
                categoryFilter(Category.TOP);
            });
            applyButtonBorder(topButton);
        }

        if (bottomButton != null) {
            bottomButton.addActionListener(e -> {
                selectedCategory = Category.BOTTOM;
                categoryFilter(Category.BOTTOM);
            });
            applyButtonBorder(bottomButton);
        }

        if (footwearButton != null) {
            footwearButton.addActionListener(e -> {
                selectedCategory = Category.FOOTWEAR;
                categoryFilter(Category.FOOTWEAR);
            });
            applyButtonBorder(footwearButton);
        }

        if (addButton != null) applyButtonBorder(addButton);
        if (removeButton != null) applyButtonBorder(removeButton);

        if (xButton != null) {
            xButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }
            });
            applyButtonBorder(xButton);
        }

        if (wardrobePanel != null) {
            add(wardrobePanel);
        }

        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Collection collection = new Collection();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error starting application: " + e.getMessage());
            }
        });
    }
    private File selectImageFromFileExplorer() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif", "bmp"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
                String taggedName = appendCategoryTag(selectedFile.getName());/* Basically append the category in the file name*/
                File destination = new File(storageFolder, taggedName);
            try {
                java.nio.file.Files.copy(
                        selectedFile.toPath(), // The file
                        destination.toPath(), // Destination
                        StandardCopyOption.REPLACE_EXISTING // Replace the existing file attribute
                );
                return destination; // Return as an icon for da gui
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error loading image: " + e.getMessage(),
                        "Load Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
    // method name changed to AddClothing and RemoveClothing
    void AddClothing(File sourceFile, ImageIcon img) {
        //Replaced conditional statements for a more dynamic system.
        Category categoryDecider = categoryIdentifierViaFileName(sourceFile);
        if (img == null) {
            System.out.println("Cannot add null image");
            return;
        }
        ClothingItem newItem = new ClothingItem(
                sourceFile.getAbsolutePath(),   // real path
                img,                             // real loaded icon
                categoryDecider
        );
        clothesData.add(newItem);
        adderToPanel(newItem);
        galleryPanel.revalidate();
        galleryPanel.repaint();
    }
    void RemoveClothing() {
        if (selectedClotheLabel == null) {
            JOptionPane.showMessageDialog(this, "Please select a clothing item to remove.", "No Item Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ClothingItem itemToRemove = (ClothingItem)selectedClotheLabel.getClientProperty("ClothingItem");
        if (itemToRemove != null) {
            // 1. Remove the item from the DATA list (Model)
            clothesData.remove(itemToRemove);

            // 2. Remove the JLabel from the VIEW (galleryPanel)
            galleryPanel.remove(selectedClotheLabel);

            // 3. Clear the selection reference
            selectedClotheLabel = null;

            // 4. Force the UI to update
            galleryPanel.revalidate();
            galleryPanel.repaint();
        } else {
            System.out.println("Error: Could not retrieve ClothingItem data from selected label.");
        }
    }

    private void categoryFilter (Category category){
        galleryPanel.removeAll();
        for(ClothingItem item : clothesData){
            if(item.getCategory() == category){
                adderToPanel(item);
            }
        }
        galleryPanel.revalidate();
        galleryPanel.repaint();
    }

    /**
    Sets the design for the ScrollBar
     */
    private void applyRetroScrollBarStyle(JScrollPane scrollPane) {
        Color light = new Color(255, 255, 255);
        Color mid   = new Color(198, 198, 198);
        Color dark  = new Color(0, 0, 0);
        JScrollBar vBar = scrollPane.getVerticalScrollBar();
        vBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = mid;
                this.trackColor = new Color(230, 230, 230);
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createRetroArrowButton();
            }
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createRetroArrowButton();
            }
            private JButton createRetroArrowButton() {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(16, 16));
                btn.setBackground(mid);
                btn.setBorder(BorderFactory.createBevelBorder(
                        BevelBorder.RAISED,
                        light, light,
                        dark, dark
                ));
                return btn;
            }
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(mid);
                g2.fillRect(r.x, r.y, r.width, r.height);

                g2.setColor(light); // highlight
                g2.drawLine(r.x, r.y, r.x + r.width, r.y);

                g2.setColor(dark); // shadow
                g2.drawLine(r.x, r.y + r.height - 1, r.x + r.width, r.y + r.height - 1);
            }
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
                g.setColor(new Color(240, 240, 240));
                g.fillRect(r.x, r.y, r.width, r.height);
            }
        });
        vBar.setPreferredSize(new Dimension(18, 18));
    }
    // --- Apply exact retro button sizes ---
    private void applyFixedButtonSizes() {
        Dimension catSize = new Dimension(54, 54);   // Top/Bottom/Footwear
        Dimension actionSize = new Dimension(130, 40); // Add/Remove buttons

        // Category buttons
        setButtonSize(topButton, catSize);
        setButtonSize(bottomButton, catSize);
        setButtonSize(footwearButton, catSize);

        // Action buttons
        setButtonSize(addButton, actionSize);
        setButtonSize(removeButton, actionSize);
    }

    // Helper
    private void setButtonSize(JButton btn, Dimension size) {
        if (btn == null) return;
        btn.setPreferredSize(size);
        btn.setMinimumSize(size);
        btn.setMaximumSize(size);
    }
    private String appendCategoryTag(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        String baseName;
        String extension;
        if (dotIndex == -1) {
            baseName = filename;
            extension = "";
        } else {
            baseName = filename.substring(0, dotIndex);
            extension = filename.substring(dotIndex); // includes the dot
        }
        return baseName + "#" + selectedCategory.name() + extension;
    }

    /**
     * Basically an enum identifier via file name
     * @param e - Enter a file
     * @return - Returns which category of enum "Category.class" it is
     */
    private Category categoryIdentifierViaFileName(File e){
        String name = e.getName();
        int hashIndex = name.lastIndexOf('#');
        int dotIndex  = name.lastIndexOf('.');
        if (hashIndex == -1) {
            return null;
        }
        if (dotIndex == -1 || dotIndex < hashIndex) {
            dotIndex = name.length();
        }
        String categoryText = name.substring(hashIndex + 1, dotIndex);
        try {
            return Category.valueOf(categoryText); // Determines which enum is that file name
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
    private void preloadExistingImages() {
        if (storageFolder == null || !storageFolder.exists()) {
            System.out.println("Storage folder not found: " + storageFolder);
            return;
        }
        File[] files = storageFolder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No existing files found in: " + storageFolder);
            return;
        }
        for (File file : files) {
            if (!file.isFile()) continue; // Skip subfolders
            try {
                Category category = categoryIdentifierViaFileName(file);
                if (category == null) {
                    System.out.println("Skipping file without category tag: " + file.getName());
                    continue;
                }

                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                ClothingItem item = new ClothingItem(
                        file.getAbsolutePath(),
                        icon,
                        category
                );

                clothesData.add(item);
                adderToPanel(item); // display in gallery
            } catch (Exception e) {
                System.out.println("Failed to load image: " + file.getName() + " -> " + e.getMessage());
            }
        }
        // Refresh gallery panel
        galleryPanel.revalidate();
        galleryPanel.repaint();
    }

}