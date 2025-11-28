package DaoMingFit;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoadOutfitGUI extends JFrame implements ActionListener {
    private JPanel panel1;
    private JList list1;
    private JButton openOutfitButton;
    private JButton cancelButton;
    private WardrobeGUI owner;
    private int selectedItem = -1;
    private ArrayList<Outfit> outfits;
    public LoadOutfitGUI(WardrobeGUI owner, ArrayList<Outfit> outfits){

        super(); //<-- make this frame as dialog or pwede rapd magdialog form nalang jd daan para way hassle
        this.owner = owner;
        this.outfits = outfits;
        DefaultListModel<String> outfitNames = new DefaultListModel<>();

        for (Outfit o : outfits) outfitNames.addElement(o.getOutfitName());
        list1.setModel(outfitNames);

        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedItem = list1.getSelectedIndex();
                System.out.println("selected item: " + selectedItem);
            }
        });

        openOutfitButton.addActionListener(this);
        cancelButton.addActionListener(this);

        setContentPane(panel1);
        setBounds(0,0, 260,450);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton){ selectedItem = -1; dispose(); }
        if (e.getSource() == openOutfitButton){
            Outfit outfit = outfits.get(selectedItem);
            owner.setCurrentOutfit(outfit);
            dispose(); }
    }
}
