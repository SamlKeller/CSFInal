import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionParser {
    public boolean isValidEquation(String equation) {
        equation = equation.replaceAll("\s", ""); // Remove whitespace
        String[] parts = equation.split("=");
        return parts.length == 2 && parts[0].equalsIgnoreCase("y");
    }

    public double parseCoefficient(String coefficient) {
        if ((coefficient == null || coefficient.isEmpty() || coefficient.equals("+"))) {
            return 1.0; // Default coefficient for missing term
        } else if (coefficient.equals("-")) {
            return -1.0; // Coefficient is -1
        }

        try {
            return Double.parseDouble(coefficient);
        } catch (NumberFormatException e) {
            return 0.0; // Fallback to 0 for invalid coefficients
        }
    }

    public int parseExponent(String exponent) {
        if (exponent == null || exponent.isEmpty()) {
            return 1; // Default exponent is 1 for 'x'
        }

        try {
            return Integer.parseInt(exponent);
        } catch (NumberFormatException e) {
            return 0; // Fallback to 0 for invalid exponents, which means 'x' is just a constant term
        }
    }
}
