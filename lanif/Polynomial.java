package lanif;
import java.util.ArrayList;

public class Polynomial {
    private ArrayList<Term> terms;
    private int termCount;
    private char variable = 'x';

    public Polynomial() {
        terms = new ArrayList<Term>();
        termCount = 0;
    }

    public void addTerm(Term newTerm) {
        terms.add(newTerm);
        termCount++;
        // keep all variables up to date
        this.changeVariable(variable);
    }

    public void clear() {
        terms.clear();
        termCount = 0;
    }

    public void changeVariable(char var) {
        // when we change variables, update all underlying terms
        this.variable = var;
        for(int i=0; i < this.terms.size(); i++) {
            this.terms.get(i).setVariable(var);
        }
    }

    public char getVariable() {
        return this.variable;
    }

    public float eval(float val) {
        float value = 0;
        for(int i=0; i < termCount; i++) {
            value += this.terms.get(i).plugIn(val);
        }
        return value;
    }
}
