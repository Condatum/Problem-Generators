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
    private JButton rightButton;
    private JButton leftButton;
    private JLabel clotheLabel;

    // temporary way of loading shirt. change it once other classes are connected to the gui
    // when adding shirt images, size must be 250 x 250
    private String[] shirts = {
            "/TestFolder/images/shirt_1.jpg",
            "/TestFolder/images/shirt_2.jpg"
    };

    public Wardrobe(){
        // border effect for buttons
        applyButtonBorder(xButton);

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

        // for switching outfits --
        rightButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clotheLabel.setIcon(new ImageIcon(getClass().getResource(shirts[1])));
                clotheLabel.revalidate();
                clotheLabel.repaint();
            }
        });

        leftButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clotheLabel.setIcon(new ImageIcon(getClass().getResource(shirts[0])));
                clotheLabel.revalidate();
                clotheLabel.repaint();
            }
        });
        // ----------

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

        SwingUtilities.invokeLater(() -> new Wardrobe());
    }
}