package lanif;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JFrame;
import javax.swing.*;
import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Lanif implements ActionListener {
    private Polynomial poly;
    private JFrame frame;
    private JPanel graph, polybox, opbox, rangebox;
    private JTextArea output;
    private int termCount = 0;
    private float lowRange = 0;
    private float highRange = 0;
    private float increment = 0;
    private char variable = 'x';

    public Lanif() {
        poly = new Polynomial();
        InitGui();
    }

    public void InitGui() {
        BorderLayout flayout = new BorderLayout();
        frame = new JFrame();
        polybox = new JPanel();
        opbox = new JPanel();
        rangebox = new JPanel();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Lanif");
        frame.getContentPane().setLayout(flayout);

        // add our panes polybox (holds polynomial) and opbox(houses operations)
        frame.add(polybox, BorderLayout.PAGE_START);
        JPanel horiz = new JPanel();
        horiz.setLayout(new BoxLayout(horiz, BoxLayout.Y_AXIS));
        horiz.add(opbox);
        horiz.add(rangebox);
        frame.add(horiz, BorderLayout.PAGE_END);


        // add output
        output = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(output);
        output.setEditable(false);
        frame.add(scrollPane, BorderLayout.CENTER);

        // add operations
        JButton newButton = new JButton("Add Term");
        newButton.setActionCommand("newUITerm");
        newButton.addActionListener(this);
        opbox.add(newButton);

        newButton = new JButton("Add Constant");
        newButton.setActionCommand("newUIConstant");
        newButton.addActionListener(this);
        opbox.add(newButton);

        newButton = new JButton("Clear");
        newButton.setActionCommand("clear");
        newButton.addActionListener(this);
        opbox.add(newButton);

        // add range stuff
        JSpinner spin = new JSpinner();
        spin.addChangeListener(new termChangeListener(this, null, spin, 2));
        spin.setPreferredSize(new Dimension(60, 25));
        rangebox.add(spin);
        rangebox.add(new JLabel("<= " + variable + " <="));

        spin = new JSpinner();
        spin.addChangeListener(new termChangeListener(this, null, spin, 3));
        spin.setPreferredSize(new Dimension(60, 25));
        rangebox.add(spin);

        rangebox.add(new JLabel("Increment: "));
        spin = new JSpinner();
        spin.addChangeListener(new termChangeListener(this, null, spin, 4));
        spin.setPreferredSize(new Dimension(60, 25));
        rangebox.add(spin);

        newButton = new JButton("Eval");
        newButton.setActionCommand("eval");
        newButton.addActionListener(this);
        opbox.add(newButton);

        frame.pack();
        //frame.setPreferredSize(new Dimension(300, 350)); // set default frame size to 300x350
        frame.setVisible(true);
    }

    private void newUITerm() {
        JPanel panel = new JPanel();
        Term myTerm = new Term(variable);
        JLabel var = new JLabel(Character.toString(variable));
        JSpinner coeff, exp;
        //BorderLayout layout = new BorderLayout();

        poly.addTerm(myTerm);
        coeff = new JSpinner();
        exp = new JSpinner();

        if(termCount >= 1) {
            JLabel sep = new JLabel("+");
            panel.add(sep);
        }
        // Coefficient
        //coeff.setValue(Float.toString(myTerm.getCoefficient()));
        coeff.addChangeListener(new termChangeListener(this, myTerm, coeff, 0));
        coeff.setPreferredSize(new Dimension(60, 25));
        panel.add(coeff);

        // Variable
        panel.add(var);

        // Exponent
        //exp.setValue(Float.toString(myTerm.getExponent()));
        exp.addChangeListener(new termChangeListener(this, myTerm, exp, 1));
        panel.add(exp);
        polybox.add(panel);
        polybox.updateUI();
        panel.updateUI();
        frame.pack();
        termCount++;
    }

    private void newUIConstant() {
        JPanel panel = new JPanel();
        Term myTerm = new Term(variable);
        myTerm.setExponent(0);
        JSpinner coeff;
        poly.addTerm(myTerm);
        coeff = new JSpinner();

        if(termCount >= 1) {
            JLabel sep = new JLabel("+");
            panel.add(sep);
        }
        // Coefficient only
        //coeff.setValue(Float.toString(myTerm.getCoefficient()));
        coeff.addChangeListener(new termChangeListener(this, myTerm, coeff, 0));
        coeff.setPreferredSize(new Dimension(60, 25));
        panel.add(coeff);
        polybox.add(panel);
        polybox.updateUI();
        panel.updateUI();
        frame.pack();
        termCount++;
    }

    // Inner pseudo-class that handles changes to the spinners
    // it is being used for both the "term" spinners and the "range" ones
    // the term argument is used for the former, and the lanif argument
    // is used for the latter
    class termChangeListener implements ChangeListener {
        Term myTerm;
        int prop;
        JSpinner spin;
        Lanif obj;
        public termChangeListener(Lanif outer, Term newTerm, JSpinner mySpinner, int newProp) {
            this.myTerm = newTerm;
            this.prop = newProp;
            this.spin = mySpinner;
            this.obj = outer;
        }

        public void doUpdate(float newval) {
            if(this.prop == 0) {
                myTerm.setCoefficient(newval);
            } else if(this.prop == 1) {
                myTerm.setExponent(newval);
            } else if((this.prop >= 2) || (this.prop <= 4)) {
                obj.setRange(newval, this.prop);
            }
        }

        public void stateChanged(ChangeEvent evt) {
            //evt.getValue
            String value = spin.getValue() + "";
            float newval = Float.parseFloat(value);
            System.out.println("termUpdate: " + value);
            doUpdate(newval);
        }
    }

    public void setRange(float val, int w) {
        if(w == 2) {
            lowRange = val;
        } else if(w == 3){
            highRange = val;
        } else if(w == 4){
            increment = val;
        }
    }
    public void doEval() {
        output.setText("");
        System.out.println(Float.toString(lowRange) + " <= " + variable + " <= " + Float.toString(highRange));
        System.out.println(Float.toString(poly.eval(3)));
        if((lowRange < highRange) && (increment >= 1)) {
            for(float x = lowRange; x <= highRange; x += increment) {
                String yval = Float.toString(poly.eval(x));
                output.append(variable + ": " + x + " => " + yval + "\n");
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if ("newUITerm".equals(e.getActionCommand())) {
            newUITerm();
        } else if ("newUIConstant".equals(e.getActionCommand())) {
            newUIConstant();
        } else if ("clear".equals(e.getActionCommand())) {
            poly.clear();
            polybox.removeAll();
            polybox.updateUI();
            termCount = 0;
        } else if ("eval".equals(e.getActionCommand())) {
            doEval();
        }
    }

    public static void main(String args[]) {
    SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Lanif();
            }
        });
    }
}
