/* CSCS2420 (Java) Final project
 * By: Kaesi (@kcpu on discord)
 * Lanif is a polynomial evaluator. It is still in an early stage of development
 * Thanks to @scoutchorton for the idea of using JSpinners instead of JTextFields
 */

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
import java.util.ArrayList;


public class Lanif implements ActionListener {
    private Polynomial poly;
    private JFrame frame;
    private JPanel graph, polybox;
    private JTextArea output;
    private JLabel rangelabel;
    private ArrayList<JLabel> varlabels;
    private JTextField varfield;

    // range stuff
    private int termCount = 0;
    private float lowRange = 0;
    private float highRange = 0;
    private float increment = 0;

    public Lanif() {
        poly = new Polynomial();
        varlabels = new ArrayList<JLabel>();
        InitGui();
    }

    public void InitGui() {
        BorderLayout flayout = new BorderLayout();
        frame = new JFrame();
        polybox = new JPanel();
        JPanel opbox = new JPanel();
        JPanel rangebox = new JPanel();
        JPanel vchangebox = new JPanel();
        varfield = new JTextField("" + poly.getVariable());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Lanif");
        frame.getContentPane().setLayout(flayout);

        // add our panes polybox (holds polynomial) and opbox(houses operations)
        frame.add(polybox, BorderLayout.PAGE_START);
        JPanel horiz = new JPanel();
        horiz.setLayout(new BoxLayout(horiz, BoxLayout.Y_AXIS));
        horiz.add(opbox);
        horiz.add(rangebox);
        horiz.add(vchangebox);
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

        newButton = new JButton("Eval");
        newButton.setActionCommand("eval");
        newButton.addActionListener(this);
        opbox.add(newButton);

        // add range stuff
        JSpinner spin = new JSpinner();
        spin.addChangeListener(new termChangeListener(this, null, spin, 2));
        spin.setPreferredSize(new Dimension(60, 25));
        rangebox.add(spin);
        rangebox.add(new JLabel("<= "));
        // keep track of all JLabels holding a variable
        rangelabel = new JLabel("" + poly.getVariable());
        rangebox.add(rangelabel);
        rangebox.add(new JLabel("<= "));


        spin = new JSpinner();
        spin.addChangeListener(new termChangeListener(this, null, spin, 3));
        spin.setPreferredSize(new Dimension(60, 25));
        rangebox.add(spin);

        rangebox.add(new JLabel("Increment: "));
        spin = new JSpinner();
        spin.addChangeListener(new termChangeListener(this, null, spin, 4));
        spin.setPreferredSize(new Dimension(60, 25));
        rangebox.add(spin);

        rangebox.add(new JLabel("Var: "));
        varfield.setPreferredSize(new Dimension(25, 25));
        rangebox.add(varfield);
        newButton = new JButton("set");
        newButton.setActionCommand("setvar");
        newButton.addActionListener(this);
        rangebox.add(newButton);


        frame.pack();
        //frame.setPreferredSize(new Dimension(300, 350)); // set default frame size to 300x350
        frame.setVisible(true);
    }

    private void newUITerm() {
        char variable = poly.getVariable();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        Term myTerm = new Term();
        JLabel var = new JLabel(Character.toString(variable));
        JSpinner coeff, exp;

        // GridBag stuff
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        poly.addTerm(myTerm);
        coeff = new JSpinner();
        exp = new JSpinner();

        if(termCount >= 1) polybox.add(new JLabel("+"));

        // Coefficient
        coeff.addChangeListener(new termChangeListener(this, myTerm, coeff, 0));
        coeff.setPreferredSize(new Dimension(60, 25));
        c.gridx = 0; c.gridy = 1;
        panel.add(coeff, c);

        // Variable
        c.gridx = 1; c.gridy = 1;
        panel.add(var, c);
        varlabels.add(var);

        // Exponent
        exp.addChangeListener(new termChangeListener(this, myTerm, exp, 1));
        c.gridx = 2; c.gridy = 0;
        panel.add(exp, c);
        polybox.add(panel);
        polybox.updateUI();
        panel.updateUI();
        frame.pack();
        termCount++;
    }

    private void newUIConstant() {
        char variable = poly.getVariable();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        Term myTerm = new Term();
        myTerm.setExponent(0);
        JSpinner coeff;
        poly.addTerm(myTerm);
        coeff = new JSpinner();
        // GridBag stuff
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        if(termCount >= 1) polybox.add(new JLabel("+"));

        // add vertical gap
        panel.add(Box.createVerticalStrut(20));
        c.gridx=0; c.gridy=0;
        panel.add(coeff, c);

        // Coefficient only
        coeff.addChangeListener(new termChangeListener(this, myTerm, coeff, 0));
        coeff.setPreferredSize(new Dimension(60, 25));
        c.gridx = 0; c.gridy = 1;
        panel.add(coeff, c);
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
            String value = spin.getValue() + "";
            float newval = Float.parseFloat(value);
            doUpdate(newval);
        }
    }

    // set our evaluation range
    public void setRange(float val, int w) {
        if(w == 2) {
            lowRange = val;
        } else if(w == 3){
            highRange = val;
        } else if(w == 4){
            increment = val;
        }
    }

    // this updates all of the variable labels, and changes our variable
    // the default variable is 'x'
    public void changeVariable(char var) {
        // change backend variable
        poly.changeVariable(var);
        // change all labels
        rangelabel.setText("" + var);
        for(int i=0; i < varlabels.size(); i++) {
            this.varlabels.get(i).setText("" + var);
        }
    }

    // evaluate our polynomial for all numbers in our range
    public void doEval() {
        char variable = poly.getVariable();
        output.setText("");
        if((lowRange < highRange) && (increment >= 1)) {
            for(float x = lowRange; x <= highRange; x += increment) {
                String yval = Float.toString(poly.eval(x));
                output.append(variable + ": " + x + " => " + yval + "\n");
            }
        }
    }

    // actionPerformed is used for all of the JButtons
    public void actionPerformed(ActionEvent e) {
        if ("newUITerm".equals(e.getActionCommand())) {
            newUITerm();
        } else if ("newUIConstant".equals(e.getActionCommand())) {
            newUIConstant();
        } else if ("clear".equals(e.getActionCommand())) {
            output.setText("");
            poly.clear();
            polybox.removeAll();
            polybox.updateUI();
            termCount = 0;
        } else if ("eval".equals(e.getActionCommand())) {
            doEval();
        } else if ("setvar".equals(e.getActionCommand())) {
            String newvar = varfield.getText();
            if(newvar.length() == 1) {
                changeVariable(newvar.charAt(0));
            }
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
