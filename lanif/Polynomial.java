package lanif;
import java.util.ArrayList;

public class Polynomial {
    private ArrayList<Term> terms;
    private int termCount;

    public Polynomial() {
        terms = new ArrayList<Term>();
        termCount = 0;
    }

    public void addTerm(Term newTerm) {
        terms.add(newTerm);
        termCount++;
    }

    public void clear() {
        terms.clear();
        termCount = 0;
    }

    public float eval(float val) {
        float value = 0;
        for(int i=0; i < termCount; i++) {
            value += this.terms.get(i).plugIn(val);
        }
        return value;
    }
}
