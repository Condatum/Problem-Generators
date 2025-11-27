package TestFolder;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color; // Needed for Color.WHITE and Color.BLACK
import javax.swing.border.Border; // Needed for Border type
import javax.swing.border.EmptyBorder; // Needed for explicit border weight
import javax.swing.border.BevelBorder; // Needed for the 3D effect

public class Menu extends JFrame {
    private JPanel menuPanel;
    private JPanel titlePanel;
    private JButton xButton;
    private JPanel contentPanel;
    private JLabel icon;
    private JButton startButton;
    private JLabel logoIcon;
    private JButton exitButton;

    public Menu(){
        // applying custom borders for selected buttons
        applyRetroBorder(startButton);
        applyRetroBorder(exitButton);

        // command to close the program
        MouseAdapter exitAction = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // This single line executes when either button is clicked
                System.exit(0);
            }
        };

        // exit buttons
        xButton.addMouseListener(exitAction);
        exitButton.addMouseListener(exitAction);

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // redirect to wardrobe panel
                Wardrobe nextWindow = new Wardrobe();
                nextWindow.setVisible(true);
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(startButton);

                // close menu panel
                currentFrame.dispose();
            }
        });


        // do not edit
        add(menuPanel);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void applyRetroBorder(JButton button) {
        final int BORDER_WEIGHT = 3;

        // 1. Create the Bevel Border (The 3D Color Effect)
        Border bevelBorder = BorderFactory.createBevelBorder(
                BevelBorder.RAISED,
                Color.WHITE,        // Highlight Outer (Top/Left)
                Color.WHITE,        // Highlight Inner (Top/Left)
                Color.BLACK,        // Shadow Outer (Bottom/Right)
                Color.BLACK         // Shadow Inner (Bottom/Right)
        );

        // 2. Create the Empty Border (The Weight/Padding)
        Border weightBorder = new EmptyBorder(
                BORDER_WEIGHT, BORDER_WEIGHT, BORDER_WEIGHT, BORDER_WEIGHT
        );

        // 3. Combine them and apply
        button.setBorder(
                BorderFactory.createCompoundBorder(
                        weightBorder,
                        bevelBorder
                )
        );
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

        // Then launch your GUI
        SwingUtilities.invokeLater(() -> new Menu());
    }
}