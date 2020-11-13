package lanif;
import java.lang.Math;

public class Term {
    private float polarity, coefficient, exponent;
    private char variable;
    private int id;

    public Term(char var) {
        this.polarity = 0;
        this.coefficient = 0;
        this.variable = var;
        this.exponent = 0;
    }

    public Term(char var, float coeff, float exp, int id) {
        //if((pol == 0) || (pol == 1)) this.polarity = pol;
        if(coeff > 0) this.coefficient = coeff;
        this.variable = var;
        this.exponent = exp;
        this.id = id;
    }

    public float getExponent() {
        return this.exponent;
    }

    public float getCoefficient() {
        return this.coefficient;
    }

    public void setExponent(float val) {
        this.exponent = val;
    }

    public void setCoefficient(float val) {
        if(val > 0) this.coefficient = val;
    }

    public float plugIn(float val) {
        float result = 0;
        result = (float)Math.pow(val, this.exponent);
        result = result * this.coefficient;
        //if(this.polarity == 1) result = result * -1;
        return result;
    }
}
