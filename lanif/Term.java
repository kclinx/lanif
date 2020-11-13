package lanif;
import java.lang.Math;

public class Term {
    private float polarity, coefficient, exponent;
    private char variable;

    public Term() {
        this.polarity = 0;
        this.coefficient = 1;
        this.variable = 'x';
        this.exponent = 1;
    }

    public Term(float coeff, float exp) {
        //if((pol == 0) || (pol == 1)) this.polarity = pol;
        if(coeff > 0) this.coefficient = coeff;
        //this.variable = var;
        this.exponent = exp;
    }

    public float plugIn(float val) {
        float result = 0;
        result = (float)Math.pow(val, this.exponent);
        result = result * this.coefficient;
        //if(this.polarity == 1) result = result * -1;
        return result;
    }
}
