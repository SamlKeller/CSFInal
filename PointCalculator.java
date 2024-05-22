import java.awt.Point;
import java.util.ArrayList;

public class PointCalculator {
    private FunctionEvaluator evaluator;

    public PointCalculator() {
        evaluator = new FunctionEvaluator();
    }

    public ArrayList<Point> calculatePoints(ArrayList<Double> coefficients, ArrayList<Integer> exponents) {
        ArrayList<Point> points = new ArrayList<>();
        for (int x = -10000; x <= 10000; x++) {
            points.add(new Point(x, (int) evaluator.evaluate(coefficients, exponents, x)));
        }
        return points;
    }
}