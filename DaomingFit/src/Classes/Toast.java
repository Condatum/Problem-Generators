package Classes;

import FrontEnd.RetroFactory;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class Toast extends JWindow {

    public Toast(String message) {
        Color classicBeige = new Color(223, 225, 229); // The exact hex #D4D0C8 used in Win98/2000
        Color darkText = Color.BLACK;

        setBackground(classicBeige);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(classicBeige);

        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createBevelBorder(
                        BevelBorder.RAISED,
                        Color.WHITE,       // Highlight
                        Color.GRAY         // Shadow
                )
        ));

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setForeground(darkText);

        label.setFont(RetroFactory.getPixelFont(12));

        label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        contentPanel.add(label, BorderLayout.CENTER);
        add(contentPanel);

        pack();

        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());

        int x = scrSize.width - getWidth() - 20;
        int y = scrSize.height - getHeight() - toolHeight.bottom - 20;

        setLocation(x, y);
        setAlwaysOnTop(true);
    }

    public void showToast() {
        setVisible(true);
    }

    public void hideToast() {
        setVisible(false);
        dispose();
    }
}