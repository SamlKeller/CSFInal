public class CoefficientParser {
    public double parseCoefficient(String coefficient) {
        if ((coefficient == null || coefficient.isEmpty() || coefficient.equals("+"))) {
            return 1.0; // Default coefficient for missing term
        } else if (coefficient.equals("-")) {
            return -1.0; // Coefficient is -1
        }

        try {
            return Double.parseDouble(coefficient);
        } catch (NumberFormatException e) {
            return 0.0; // Fallout to 0 for invalid coefficients
        }
    }
}
