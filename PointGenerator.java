import java.awt.Point;
import java.util.ArrayList;

public class PointGenerator {
    private FunctionParser parser;

    public PointGenerator() {
        parser = new FunctionParser();
    }

    public ArrayList<Point> generatePoints(String equation) {
        ArrayList<Double> coefficients = parser.parseCoefficients(equation);
        ArrayList<Integer> exponents = parser.parseExponents(equation);
        ArrayList<Point> points = new ArrayList<>();

        for (int x = -10000; x <= 10000; x += 1) {
            double y = 0;
            for (int i = 0; i < coefficients.size(); i++) {
                y += coefficients.get(i) * Math.pow(x, exponents.get(i));
            }
            points.add(new Point(x, (int) y));
        }

        return points;
    }
}
