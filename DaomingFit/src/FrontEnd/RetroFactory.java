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

    // 1. Static Block: Loads the font immediately when the app starts
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

    // 2. HELPER: Get font with specific size (Crucial for Titles)
    public static Font getPixelFont(float size) {
        if (pixelFont != null) {
            return pixelFont.deriveFont(size);
        } else {
            return new Font("Arial", Font.BOLD, (int) size);
        }
    }

    // 3. HELPER: Recursively apply font to EVERYTHING in a panel
    public static void applyGlobalFont(Container container) {
        for (Component c : container.getComponents()) {
            c.setFont(pixelFont);
            if (c instanceof Container) {
                applyGlobalFont((Container) c); // Go deeper into panels
            }
        }
    }

    // --- Styling Helpers for EXISTING elements (from GUI Designer) ---

    public static void styleRetroButton(JButton btn) {
        if (btn == null) return;
        btn.setFont(pixelFont);
        btn.setBackground(RETRO_GRAY);
        btn.setFocusPainted(false);
        // Matches old Wardrobe: Outer Padding + Inner Bevel
        btn.setBorder(createRetroBorder(BevelBorder.RAISED));
    }

    public static void styleRecessedPanel(JPanel panel) {
        if (panel == null) return;
        panel.setBackground(RETRO_GRAY);
        // Matches old Wardrobe: Outer Padding + Inner Bevel
        panel.setBorder(createRetroBorder(BevelBorder.LOWERED));
    }

    // --- Factory Methods for creating NEW elements ---

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

    // --- Internal Border Logic ---
    private static Border createRetroBorder(int bevelType) {
        // This matches your original Wardrobe logic:
        // 1. EmptyBorder (Outer) -> Creates the spacing around the element
        // 2. BevelBorder (Inner) -> Creates the 3D effect inside that spacing
        return BorderFactory.createCompoundBorder(
                new EmptyBorder(3, 3, 3, 3),
                BorderFactory.createBevelBorder(bevelType, Color.WHITE, Color.WHITE, Color.BLACK, Color.BLACK)
        );
    }
}