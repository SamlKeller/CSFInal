public class ExponentParser {
    public int parseExponent(String exponent) {
        if (exponent == null || exponent.isEmpty()) {
            return 1; // Default exponent for x is 1
        }
        try {
            return Integer.parseInt(exponent);
        } catch (NumberFormatException e) {
            return 0; // Exponent is 0 for constants
        }
    }
}
