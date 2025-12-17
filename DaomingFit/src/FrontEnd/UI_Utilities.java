package FrontEnd;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class UI_Utilities {

    // For resizing the image
    public static ImageIcon resize(ImageIcon icon, int width, int height) {
        if (icon == null) return null;

        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(scaled);
    }


    // to apply the retro look for the ui
    public static void applyRetroScrollBarStyle(JScrollPane scrollPane) {
        Color light = new Color(255, 255, 255);
        Color mid   = new Color(198, 198, 198);
        Color dark  = new Color(0, 0, 0);

        JScrollBar vBar = scrollPane.getVerticalScrollBar();
        vBar.setPreferredSize(new Dimension(18, 18));

        vBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = mid;
                this.trackColor = new Color(230, 230, 230);
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createRetroArrowButton(mid, light, dark);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createRetroArrowButton(mid, light, dark);
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(mid);
                g2.fillRect(r.x, r.y, r.width, r.height);

                g2.setColor(light); // Highlight (Top/Left)
                g2.drawLine(r.x, r.y, r.x + r.width, r.y);
                g2.drawLine(r.x, r.y, r.x, r.y + r.height);

                g2.setColor(dark); // Shadow (Bottom/Right)
                g2.drawLine(r.x, r.y + r.height - 1, r.x + r.width, r.y + r.height - 1);
                g2.drawLine(r.x + r.width - 1, r.y, r.x + r.width - 1, r.y + r.height);
            }
        });
    }

    // Private helper for the scrollbar buttons
    private static JButton createRetroArrowButton(Color bg, Color light, Color dark) {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(16, 16));
        btn.setBackground(bg);
        btn.setBorder(BorderFactory.createBevelBorder(
                BevelBorder.RAISED, light, light, dark, dark
        ));
        return btn;
    }
}