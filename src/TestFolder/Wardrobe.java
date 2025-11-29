package TestFolder;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Wardrobe extends JFrame {
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
    private JList list1;
    private JScrollPane outfitListPane;
    private JPanel tabsPanel;
    private JLabel wardrobeLabel;
    private JLabel collectionLabel;
    private JButton loadOutfitButton;
    private JButton removeOutfitButton;
    private JTextField inputOutfitField;

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

    public Wardrobe(){
        // border effect for panels
        Color gray = new Color(198, 198, 198);
        Color white = new Color(255,255,255);

        applyPanelRecessBorder(contentPanel, gray);
        applyPanelRecessBorder(clothesPanel, white);

        createOutfitPanel.setBackground(gray);
        createOutfitPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        settingsPanel.setBackground(gray);
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        // exit button
        xButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        // for switching outfits --
        rightTopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                topLabel.setIcon(new ImageIcon(getClass().getResource(shirts[1])));
                topLabel.revalidate();
                topLabel.repaint();
            }
        });

        leftTopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                topLabel.setIcon(new ImageIcon(getClass().getResource(shirts[0])));
                topLabel.revalidate();
                topLabel.repaint();
            }
        });

        rightBottomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bottomLabel.setIcon(new ImageIcon(getClass().getResource(bottoms[1])));
                bottomLabel.revalidate();
                bottomLabel.repaint();
            }
        });

        leftBottomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bottomLabel.setIcon(new ImageIcon(getClass().getResource(bottoms[0])));
                bottomLabel.revalidate();
                bottomLabel.repaint();
            }
        });
        // ----------

        // for tab switching and hovering ----
        wardrobeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Test");
            }
        });


        // -----------

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