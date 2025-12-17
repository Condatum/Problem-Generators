package FrontEnd;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JFrame {
    private JPanel menuPanel;
    private JPanel titlePanel;
    private JButton xButton;
    private JPanel contentPanel;
    private JLabel icon;
    private JButton wardrobeButton;
    private JButton exitButton;
    private JButton collectionsButton;
    private JLabel iconLabel;
    private JLabel daomingTitle;
    private JLabel descLabel;

    public Menu() {
        applyRetroStyle(wardrobeButton);
        applyRetroStyle(exitButton);
        applyRetroStyle(xButton);
        applyRetroStyle(collectionsButton);

        MouseAdapter exitAction = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        };
        // Add listener to both X button and Exit button
        xButton.addMouseListener(exitAction);
        exitButton.addMouseListener(exitAction);

        // To switch tabs
        wardrobeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // redirect to wardrobe panel
                Wardrobe nextWindow = new Wardrobe();
                nextWindow.setVisible(true);
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(wardrobeButton);

                // close menu panel
                currentFrame.dispose();
            }
        });
        collectionsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // redirect to wardrobe panel
                Collection nextWindow = new Collection();
                nextWindow.setVisible(true);
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(collectionsButton);
                // close menu panel
                currentFrame.dispose();
            }
        });

        // --- 4. Window Setup ---
        if (menuPanel != null) {
            add(menuPanel);
        } else {
            System.out.println("Warning: menuPanel is null. Check GUI Designer.");
        }

        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);

        RetroFactory.applyGlobalFont(this);

        // Setting font sizes
        daomingTitle.setFont(RetroFactory.getPixelFont(35F));
        descLabel.setFont(RetroFactory.getPixelFont(18F));
        wardrobeButton.setFont(RetroFactory.getPixelFont(20F));
        collectionsButton.setFont(RetroFactory.getPixelFont(20F));
        exitButton.setFont(RetroFactory.getPixelFont(20F));

        setVisible(true);
    }

    private void applyRetroStyle(JButton button) {
        if (button == null) return;

        button.setBackground(new Color(198, 198, 198));
        button.setFocusPainted(false); // Removes the focus square

        // Create the 3D Bevel Border
        final int BORDER_WEIGHT = 3;
        Border bevelBorder = BorderFactory.createBevelBorder(
                BevelBorder.RAISED,
                Color.WHITE, Color.WHITE,
                Color.BLACK, Color.BLACK
        );
        Border weightBorder = new EmptyBorder(
                BORDER_WEIGHT, BORDER_WEIGHT, BORDER_WEIGHT, BORDER_WEIGHT
        );

        button.setBorder(BorderFactory.createCompoundBorder(weightBorder, bevelBorder));
    }

    public static void main(String[] args) {
        // Launch the Menu
        SwingUtilities.invokeLater(() -> new Menu());
    }
}