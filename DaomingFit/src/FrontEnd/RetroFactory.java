package FrontEnd;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.InputStream;

public class RetroFactory {

    public static Font pixelFont;

    // Define the Windows 95 Gray explicitly
    private static final Color RETRO_GRAY = new Color(192, 192, 192);

    static {
        try {
            InputStream is = RetroFactory.class.getResourceAsStream("/PixelifySans-Regular.ttf");
            if (is != null) {
                // setting custom font and default size of font
                pixelFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(14f);

                // Register it so the system knows about it
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(pixelFont);
            } else {
                System.out.println("Font file not found! Using default.");
                pixelFont = new Font("Arial", Font.BOLD, 14);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pixelFont = new Font("Arial", Font.BOLD, 14);
        }
    }

    // For fonts with specific sizes
    public static Font getPixelFont(float size) {
        if (pixelFont != null) {
            return pixelFont.deriveFont(size);
        } else {
            return new Font("Arial", Font.BOLD, (int) size);
        }
    }

    public static void applyGlobalFont(Container container) {
        for (Component c : container.getComponents()) {
            c.setFont(pixelFont);
            if (c instanceof Container) {
                applyGlobalFont((Container) c); // Go deeper into panels
            }
        }
    }

    public static void styleRetroButton(JButton btn) {
        if (btn == null) return;
        btn.setFont(pixelFont);
        btn.setBackground(RETRO_GRAY);
        btn.setFocusPainted(false);
        btn.setBorder(createRetroBorder(BevelBorder.RAISED));
    }

    public static void styleRecessedPanel(JPanel panel) {
        if (panel == null) return;
        panel.setBackground(RETRO_GRAY);
        panel.setBorder(createRetroBorder(BevelBorder.LOWERED));
    }

    // -- Helper functions for creating new elements ---

    public static JButton createButton(String text) {
        JButton btn = new JButton(text);
        styleRetroButton(btn);
        return btn;
    }

    public static JPanel createPanel() {
        JPanel panel = new JPanel();
        styleRecessedPanel(panel);
        return panel;
    }

    // For border
    private static Border createRetroBorder(int bevelType) {
        return BorderFactory.createCompoundBorder(
                new EmptyBorder(3, 3, 3, 3),
                BorderFactory.createBevelBorder(bevelType, Color.WHITE, Color.WHITE, Color.BLACK, Color.BLACK)
        );
    }
}