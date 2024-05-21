public class ExponentParser {
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
