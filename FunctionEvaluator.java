import java.util.ArrayList;

public class FunctionEvaluator {
    public double evaluate(ArrayList<Double> coefficients, ArrayList<Integer> exponents, int x) {
        double y = 0;
        for (int i = 0; i < coefficients.size(); i++) {
            y += coefficients.get(i) * Math.pow(x, exponents.get(i));
        }
        return y;
    }
}
