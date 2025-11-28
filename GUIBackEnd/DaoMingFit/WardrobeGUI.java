package DaoMingFit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WardrobeGUI extends JFrame implements ActionListener {
    private JPanel mainPanel;

    private JButton btnTopPrev;
    private JButton btnTopNext;
    private JButton btnBotPrev;
    private JButton btnFWPrev;
    private JButton btnBotNext;
    private JButton btnFWNext;

    private JLabel displayTop;
    private JLabel displayFW;
    private JLabel displayBot;
    private JButton saveButton;
    private JButton loadButton;

    private Wardrobe wardrobe;

    public WardrobeGUI(){
        setTitle("DaoMing Fit Wardrobe");
        setContentPane(mainPanel);
        setBounds(0,0, 350,450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        wardrobe = new Wardrobe();

        displayTop.setText(wardrobe.browseTop(0));
        displayBot.setText(wardrobe.browseBottom(0));
        displayFW.setText(wardrobe.browseFW(0));

        btnTopNext.addActionListener(this);
        btnTopPrev.addActionListener(this);
        btnBotNext.addActionListener(this);
        btnBotPrev.addActionListener(this);
        btnFWNext.addActionListener(this);
        btnFWPrev.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
    }

    public Outfit getCurrentOutfit(){
        String top = displayTop.getText();
        String bottom = displayBot.getText();
        String footwear = displayFW.getText();
        return new Outfit("outfit " + (wardrobe.getOutfits().size() + 1), top, bottom, footwear);
    }

    public void setCurrentOutfit(Outfit outfit){
        displayTop.setText(outfit.getTop());
        displayBot.setText(outfit.getBottom());
        displayFW.setText(outfit.getFootwear());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnTopPrev){ displayTop.setText(wardrobe.browseTop(-1)); return; }
        if (o == btnTopNext){ displayTop.setText(wardrobe.browseTop(1)); return; }
        if (o == btnBotPrev){ displayBot.setText(wardrobe.browseBottom(-1)); return; }
        if (o == btnBotNext){ displayBot.setText(wardrobe.browseBottom(1)); return; }
        if (o == btnFWPrev){ displayFW.setText(wardrobe.browseFW(-1)); return; }
        if (o == btnFWNext){ displayFW.setText(wardrobe.browseFW(1)); return; }

        if (o == saveButton){ wardrobe.addOutfit(getCurrentOutfit()); }
        if (o == loadButton){ new LoadOutfitGUI(this, wardrobe.getOutfits()); }
    }

    public static void main(String[] args) {
        new WardrobeGUI();
    }
}

