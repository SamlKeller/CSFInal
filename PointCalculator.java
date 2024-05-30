import java.awt.Point;
import java.util.ArrayList;

public class PointCalculator {

    public ArrayList<Point> calculatePoints(ArrayList<Double> coefficients, ArrayList<Integer> exponents, double zoomLevel) {
        ArrayList<Point> points = new ArrayList<>();
        double stepSize = 0.01 / zoomLevel; // Finer step size for higher zoom levels

        for (double x = -100; x <= 100; x += stepSize) {
            double y = calculateY(coefficients, exponents, x);
            points.add(new Point((int) (x * 100), (int) (y * 100))); // Scale for better visibility
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
