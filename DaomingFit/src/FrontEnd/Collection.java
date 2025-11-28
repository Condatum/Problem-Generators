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

public class Collection extends JFrame {
    private JPanel wardrobePanel;
    private JPanel titlePanel;
    private JButton xButton;
    private JLabel icon;
    private JPanel contentPanel;
    private JPanel clothesPanel;
    private JComboBox CategoriesDropDown;
    private JLabel TitleText;
    private JButton rightButton;
    private JButton leftButton;
    private JLabel clotheLabel;
    private Font PixelSans;
    // temporary way of loading shirt. change it once other classes are connected to the gui
    // when adding shirt images, size must be 250 x 250
    public Collection(){
        // border effect for buttons
        applyButtonBorder(xButton);
        try {
            PixelSans = Font.createFont(Font.TRUETYPE_FONT, new File("Pixelify_Sans/static/PixelifySans-Regular.ttf")).deriveFont(14f);
        }catch(FontFormatException | IOException e){
            System.out.println("Font not found...");
        }
        setAllFonts(); // Sets all fonts to PixelSans

        // border effect for panels
        Color gray = new Color(198, 198, 198);
        Color white = new Color(255,255,255);

        applyPanelRecessBorder(contentPanel, gray);
        applyPanelRecessBorder(clothesPanel, white);

        // exit button
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
    private void setAllFonts(){
        CategoriesDropDown.setFont(PixelSans);
        TitleText.setFont(PixelSans);
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

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        weightBorder,
                        bevelBorder
                )
        );

        button.setBackground(new Color(198, 198, 198));
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
}