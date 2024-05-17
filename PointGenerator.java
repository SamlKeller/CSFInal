import java.awt.Point;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PointGenerator {
    public ArrayList<Point> generatePoints(String equation) {
        equation = equation.replaceAll("\s", ""); // Remove whitespace
        String[] parts = equation.split("=");
        String rhs = parts[1];

        ArrayList<Double> coefficients = new ArrayList<>();
        ArrayList<Integer> exponents = new ArrayList<>();
        
        FunctionParser parser = new FunctionParser();

        Pattern pattern = Pattern.compile("([+-]?\\d*\\.?\\d*)(x\\^?(\\d*))?");
        Matcher matcher = pattern.matcher(rhs);

        while (matcher.find()) {
            String coeffStr = matcher.group(1);
            String exponentStr = matcher.group(3);

            double coefficient = parser.parseCoefficient(coeffStr);
            int exponent = parser.parseExponent(exponentStr);

            coefficients.add(coefficient);
            exponents.add(exponent);
        }

        coefficients.remove(coefficients.size() - 1);        
        

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
