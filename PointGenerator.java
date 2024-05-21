import java.awt.Point;
import java.util.ArrayList;

public class PointGenerator {
    private FunctionParser parser;
    private PointCalculator calculator;

    public PointGenerator() {
        parser = new FunctionParser();
        calculator = new PointCalculator();
    }

    public ArrayList<Point> generatePoints(String equation) {
        ArrayList<Double> coefficients = parser.parseCoefficients(equation);
        ArrayList<Integer> exponents = parser.parseExponents(equation);
        return calculator.calculatePoints(coefficients, exponents);
    }
}
