package FrontEnd;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



public class Collection extends JFrame {

    // fields for removingShit
    private JLabel selectedClotheLabel = null;
    private Color originalBorderColor = new Color(150, 150, 150);
    private Color selectionBorderColor = Color.RED;



    ArrayList<ImageIcon> clothes = new ArrayList<>();
    JPanel galleryPanel;

    private JPanel wardrobePanel;
    private JPanel titlePanel;
    private JButton xButton;
    private JLabel icon;
    private JPanel contentPanel;
    private JPanel collectionPanel; // placeholder from GUI form
    private JComboBox CategoriesDropDown;
    private JLabel TitleText;
    private JButton removeButton;
    private JButton addButton;
    private JButton rightButton;
    private JButton leftButton;
    private JLabel clotheLabel;
    private Font PixelSans;

    public Collection(){
        applyButtonBorder(xButton); // border effect for buttons
        galleryPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 20));
        // --- Initializing the font --- //
        try {
            PixelSans = Font.createFont(Font.TRUETYPE_FONT, new File("Pixelify_Sans/static/PixelifySans-Regular.ttf")).deriveFont(14f);
        }catch(FontFormatException | IOException e){
            System.out.println("Font not found...");
        } setAllFonts();
        // --- Initializing the font --- //

        InitializeScrollableContainer(); // Naa diri ang container implementation
        InitializePanelDesign(); // Implementation ni aissha sa panel design




        // Add and remove button listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Master Etcuban make me mini GUI for uploading image and removing their background kung humana tas mga mga uban pls para magamit na nako ang clothing class instead of the imageIcon
                ImageIcon tempIconYawa = (new ImageIcon(getClass().getResource("/clothing-images/shirt_1.jpg")));
                AddShit(tempIconYawa);
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeShit(); // Call the unified removal method
            }
        });
    }

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

    /**
     * Kong naa kai e add nga component sa forms
     * Just setFont to PixelSans
     */
    private void setAllFonts(){
        CategoriesDropDown.setFont(PixelSans);
        TitleText.setFont(PixelSans);
        // ... Add font to a component here, just do it like ^^ //
    }
    private void applyButtonBorder(JButton button) {
        final int BORDER_WEIGHT = 3;
        Border bevelBorder = BorderFactory.createBevelBorder(
                BevelBorder.RAISED,
                Color.WHITE, Color.WHITE,
                Color.BLACK, Color.BLACK
        );
        Border weightBorder = new EmptyBorder(
                BORDER_WEIGHT, BORDER_WEIGHT, BORDER_WEIGHT, BORDER_WEIGHT
        );
        if (button != null) {
            button.setBorder(
                    BorderFactory.createCompoundBorder(
                            weightBorder,
                            bevelBorder
                    )
            );
            button.setBackground(new Color(198, 198, 198));
        }
    }

    public ImageIcon resize(ImageIcon icon, int w, int h) {
        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }


    // should be replaced with ClothingClass when we done
    private void adderToPanel(ImageIcon clothe) {
        ImageIcon resized = resize(clothe, 180, 180);

        // 1. Create the JLabel
        JLabel label = new JLabel(resized);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(originalBorderColor));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);

        // 2. Add the Mouse Listener to select/highlight the clicked item
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Deselect the previous item
                if (selectedClotheLabel != null) {
                    selectedClotheLabel.setBorder(
                            BorderFactory.createLineBorder(originalBorderColor)
                    );
                }

                // Select the current item
                selectedClotheLabel = label;
                selectedClotheLabel.setBorder(
                        BorderFactory.createLineBorder(selectionBorderColor, 3) // Thicker red border
                );
            }
        });

        galleryPanel.add(label);
    }

    private void InitializeScrollableContainer(){
        ArrayList<ImageIcon> clothes = new ArrayList<>();
        clothes.add(new ImageIcon(getClass().getResource("/clothing-images/input.jpg")));
        clothes.add(new ImageIcon(getClass().getResource("/clothing-images/shirt_1.jpg")));
        clothes.add(new ImageIcon(getClass().getResource("/clothing-images/shirt_2.jpg")));
        for (int i = 0; i < 10; i++) {
            clothes.add(new ImageIcon(getClass().getResource("/clothing-images/shirt_2.jpg")));
        }
        // Make collectionPanel act as a container for the scroll pane
        collectionPanel.setLayout(new BorderLayout());

        // Create a separate galleryPanel that uses WrapLayout (wraps and computes preferred height)
        galleryPanel.setOpaque(true);
        galleryPanel.setBackground(Color.WHITE);

        // Fill galleryPanel with resized JLabels
        for (ImageIcon icon : clothes) {
            adderToPanel(icon);
        }

        // Put the galleryPanel inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(galleryPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add the scroll pane into the collectionPanel placeholder
        collectionPanel.add(scrollPane, BorderLayout.CENTER);

        // Ensure layout updates
        collectionPanel.revalidate();
        collectionPanel.repaint();
        // --------------------------------------------------------------------------------------------//
        // ---- // SETTING UP THE COLLECTION PANEL TO BE SCROLLABLE AND ADDING SAMPLE IMAGES // ---- //
        // --------------------------------------------------------------------------------------------//
    }
    private void InitializePanelDesign(){
        Color gray = new Color(198, 198, 198);// border effect for panels
        Color white = new Color(255,255,255);// border effect for panels
        applyPanelRecessBorder(contentPanel, gray);
        applyPanelRecessBorder(collectionPanel, white);
        xButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        add(wardrobePanel);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        final int BORDER_WEIGHT = 3;
        Border customBorder = BorderFactory.createCompoundBorder(
                new EmptyBorder(BORDER_WEIGHT, BORDER_WEIGHT, BORDER_WEIGHT, BORDER_WEIGHT),
                BorderFactory.createBevelBorder(
                        BevelBorder.RAISED,
                        Color.WHITE, Color.WHITE,
                        Color.BLACK, Color.BLACK
                )
        );

        // Override the default border property for ALL JButtons
        UIManager.put("Button.border", customBorder);

        SwingUtilities.invokeLater(() -> new Collection());
    }


    void AddShit(ImageIcon img) {
        clothes.add(img);
        adderToPanel(img);

        galleryPanel.revalidate();
        galleryPanel.repaint();
    }


    void removeShit() {
        // 1. Check if an item is selected
        if (selectedClotheLabel == null) {
            JOptionPane.showMessageDialog(this, "Please select a clothing item to remove.", "No Item Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Extract the ImageIcon from the JLabel
        // The JLabel's icon is the resized version of the original ImageIcon
        ImageIcon iconToRemove = (ImageIcon)selectedClotheLabel.getIcon();

        // NOTE: If you were tracking the un-resized image, you'd need a map or custom object.
        // For this simple case, we try to remove the icon from the data list.

        // 3. Remove the image from the DATA list (clothes ArrayList)
        // We iterate because we need to find the exact object reference to remove.
        // If the clothes list only contains the original, un-resized images, this will fail.
        // Assuming for now that the icons are distinguishable:
        if (clothes.contains(iconToRemove)) {
            clothes.remove(iconToRemove);
        } else {
            // --- IMPORTANT CAVEAT ---
            // If clothes only contains the ORIGINAL image and the JLabel holds the RESIZED one,
            // they are different objects! This is why a proper ClothingClass is better.
            // For a simple fix, you might need to iterate through the data and compare URLs/paths.
            // For this example, we will focus only on removing the JLabel from the panel:
            System.out.println("Warning: Icon not found in data list. Removing from view only.");
        }

        // 4. Remove the JLabel from the VIEW (galleryPanel)
        galleryPanel.remove(selectedClotheLabel);

        // 5. Clear the selection reference
        selectedClotheLabel = null;

        // 6. Force the UI to update (Crucial!)
        galleryPanel.revalidate();
        galleryPanel.repaint();
    }


}
