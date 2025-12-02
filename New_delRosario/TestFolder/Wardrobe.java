package TestFolder;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private class Outfit{
        String shirt, bottom, outfitName;

        int shirt_index, bottom_index;
        public Outfit(String outfitName, String shirt, String bottom, int shirt_index, int bottom_index) {
            this.shirt = shirt; this.bottom = bottom; this.outfitName = outfitName;
        }
        public String getOutfitName() { return outfitName; }
        public String getBottom() { return bottom; }
        public String getShirt() { return shirt; }
        public int getShirtIndex() { return shirt_index; }
        public int getBottomIndex() { return bottom_index; }
    }

    //---------------

    private ArrayList<Outfit> Outfits = new ArrayList<>();
    private DefaultListModel<String> outfitNames = new DefaultListModel<>();

    private int shirt_i = 0, bottom_i = 0, footwear_i = 0;

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

    private void setOutfit(int outfitIndex){
        Outfit outfit = Outfits.get(outfitIndex);
        setShirtLabel(outfit.getShirt());
        setBottomLabel(outfit.getBottom());
        shirt_i = outfit.getShirtIndex(); // para maapil ug update ang buttons
        bottom_i = outfit.getBottomIndex();;
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

    public Wardrobe(){
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

        // ----------- SETTING BUTTONS (switched to ActionListener tungod sa setter function nako na add

        xButton.addActionListener(e -> { System.exit(0);});
        leftTopButton.addActionListener(e -> { setShirtLabel(browseShirts(-1)); });
        leftBottomButton.addActionListener(e -> { setBottomLabel(browseBottom(-1)); });
        //leftShoeButton.addActionListener(e -> { setShoeLabel(browseFootwear(-1)); }); // <-- remove comment & implement func when naa nay file para shoe
        rightTopButton.addActionListener(e -> { setShirtLabel(browseShirts(1)); });
        rightBottomButton.addActionListener(e -> { setBottomLabel(browseBottom(-1)); });
        //rightShoeButton.addActionListener(e -> { setShoeLabel(browseFootwear(-1)); }); // <-- remove comment & implement func when naa nay file para shoe
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
                Outfits.add(new Outfit(outfitName, shirts[shirt_i], bottoms[bottom_i], shirt_i, bottom_i));
                outfitNames.addElement(outfitName);
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
            if (selectedIndex!=-1) {
                outfitNames.remove(selectedIndex);
                Outfits.remove(selectedIndex);
            }
        });
        outfitList.addListSelectionListener(e -> {});

        // do not edit
        add(wardrobePanel);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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