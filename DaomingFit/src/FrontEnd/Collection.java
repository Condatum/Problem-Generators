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
import java.util.ArrayList;

public class Collection extends JFrame {
    private JPanel wardrobePanel;
    private JPanel titlePanel;
    private JButton xButton;
    private JLabel icon;
    private JPanel contentPanel;
    private JPanel collectionPanel; // placeholder from GUI form
    private JComboBox CategoriesDropDown;
    private JLabel TitleText;
    private JButton rightButton;
    private JButton leftButton;
    private JLabel clotheLabel;
    private Font PixelSans;

    public Collection(){
        applyButtonBorder(xButton); // border effect for buttons

        // --- Initializing the font --- //
        try {
            PixelSans = Font.createFont(Font.TRUETYPE_FONT, new File("Pixelify_Sans/static/PixelifySans-Regular.ttf")).deriveFont(14f);
        }catch(FontFormatException | IOException e){
            System.out.println("Font not found...");
        } setAllFonts();
        // --- Initializing the font --- //

        InitializeScrollableContainer(); // Naa diri ang container implementation
        InitializePanelDesign(); // Implementation ni aissha sa panel
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
        if (CategoriesDropDown != null) CategoriesDropDown.setFont(PixelSans);
        if (TitleText != null) TitleText.setFont(PixelSans);
        // ... Add font to a component here, just do it like ^^ //
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
        if (button != null) {
            button.setBorder(
                    BorderFactory.createCompoundBorder(
                            weightBorder,
                            bevelBorder
                    )
            );
            button.setBackground(new Color(198, 198, 198));
        }
    }

    public ImageIcon resize(ImageIcon icon, int w, int h) {
        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
    private void InitializeScrollableContainer(){
        ArrayList<ImageIcon> sampleImages = new ArrayList<>();
        sampleImages.add(new ImageIcon(getClass().getResource("/clothing-images/input.jpg")));
        sampleImages.add(new ImageIcon(getClass().getResource("/clothing-images/shirt_1.jpg")));
        sampleImages.add(new ImageIcon(getClass().getResource("/clothing-images/shirt_2.jpg")));
        for (int i = 0; i < 100; i++) {
            sampleImages.add(new ImageIcon(getClass().getResource("/clothing-images/shirt_2.jpg")));
        }
        // Make collectionPanel act as a container for the scroll pane
        collectionPanel.setLayout(new BorderLayout());

        // Create a separate galleryPanel that uses WrapLayout (wraps and computes preferred height)
        JPanel galleryPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 20));
        galleryPanel.setOpaque(true);
        galleryPanel.setBackground(Color.WHITE);

        // Fill galleryPanel with resized JLabels
        for (ImageIcon icon : sampleImages) {
            ImageIcon resized = resize(icon, 180, 180);

            JLabel label = new JLabel(resized);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
            label.setOpaque(true);
            label.setBackground(Color.WHITE);

            galleryPanel.add(label);
        }

        // Put the galleryPanel inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(galleryPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // DON'T force a preferred size on the scroll pane here.
        // If you want the window smaller/larger, adjust frame sizing instead.
        // scrollPane.setPreferredSize(new Dimension(800, 600)); // <-- removed

        // Add the scroll pane into the collectionPanel placeholder
        collectionPanel.add(scrollPane, BorderLayout.CENTER);

        // Ensure layout updates
        collectionPanel.revalidate();
        collectionPanel.repaint();
        // --------------------------------------------------------------------------------------------//
        // ---- // SETTING UP THE COLLECTION PANEL TO BE SCROLLABLE AND ADDING SAMPLE IMAGES // ---- //
        // --------------------------------------------------------------------------------------------//
    }
    private void InitializePanelDesign(){
        Color gray = new Color(198, 198, 198);// border effect for panels
        Color white = new Color(255,255,255);// border effect for panels
        applyPanelRecessBorder(contentPanel, gray);
        applyPanelRecessBorder(collectionPanel, white);
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
