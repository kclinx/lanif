package lanif;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JFrame;
import javax.swing.*;
import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Lanif implements ActionListener, DocumentListener {
    private Polynomial poly;
    private JFrame frame;
    private JPanel graph, pane;
    private JButton[] button;
    private JLabel output;
    private JLabel[] label;
    private JTextField[] inbox;

    public Lanif() {
        poly = new Polynomial();
        InitGui();
    }

    public void actionPerformed(ActionEvent e) {
        if ("add".equals(e.getActionCommand())) {
        }
    }

    public void insertUpdate(DocumentEvent ev) {
    }

    public void removeUpdate(DocumentEvent ev) {
    }

    public void changedUpdate(DocumentEvent ev) {
    }

    public void InitGui() {
        frame = new JFrame();
        pane = new JPanel();
        GridBagLayout pLayout = new GridBagLayout();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Lanif");
        pane.setLayout(pLayout);
        GridBagConstraints c = new GridBagConstraints();
        // set some defaults for our gridbag constraint
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        inbox = new JTextField[4];
        label = new JLabel[4];
        button = new JButton[2];
        // labels
        label[0] = new JLabel("Coeff");
        c.gridx = 0; c.gridy = 0;
        pane.add(label[0], c);

        label[1] = new JLabel("Exp");
        c.gridx = 1; c.gridy = 0;
        pane.add(label[1], c);

        inbox[0] = new JTextField();
        c.gridx = 0; c.gridy = 1;
        pane.add(inbox[0], c);

        inbox[1] = new JTextField();
        c.gridx = 1; c.gridy = 1;
        pane.add(inbox[1], c);

        button[0] = new JButton("add");
        button[0].setActionCommand("add");
        button[0].addActionListener(this);
        c.gridx = 2; c.gridy = 1;
        pane.add(button[0], c);

        frame.add(pane);
        frame.pack();
        frame.setPreferredSize(new Dimension(300, 350)); // set default frame size to 300x350
        frame.setVisible(true);
    }

    private Term parseText(String input) {
        String[] tokens = input.split("+|-");
        Term newTerm;
        newTerm = new Term();
        //for(int i=0; i < )
        return newTerm;
    }
    private void newTerm() {
        float coeff = Float.parseFloat(inbox[0].getText());
        float exp = Float.parseFloat(inbox[1].getText());
        poly.addTerm(new Term(coeff, exp));
    }

    public static void main(String args[]) {
    SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Lanif();
            }
        });
    }
}
