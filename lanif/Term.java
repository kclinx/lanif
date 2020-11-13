package lanif;
import java.lang.Math;

public class Term {
    private float coefficient, exponent;
    private char variable;

    public Term(char var) {
        this.coefficient = 0;
        this.variable = var;
        this.exponent = 0;
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
        this.coefficient = val;
    }

    public float plugIn(float val) {
        float result = 0;
        result = (float)Math.pow(val, this.exponent);
        result = result * this.coefficient;
        return result;
    }
}
