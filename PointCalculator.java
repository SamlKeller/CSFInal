import java.awt.Point;
import java.util.ArrayList;

public class PointCalculator {

    public ArrayList<Point> calculatePoints(ArrayList<Double> coefficients, ArrayList<Integer> exponents, double zoomLevel) {
        ArrayList<Point> points = new ArrayList<>();

        for (double x = -100; x <= 100; x += 1) {
            double y = calculateY(coefficients, exponents, x);
            points.add(new Point((int)x, (int)y));
        }

        return points;
    }

    private double calculateY(ArrayList<Double> coefficients, ArrayList<Integer> exponents, double x) {
        double y = 0;
        for (int i = 0; i < coefficients.size(); i++) {
            y += coefficients.get(i) * Math.pow(x, exponents.get(i));
        }
        return y;
    }
}
