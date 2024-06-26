public class ExponentParser {
    public int parseExponent(String exponent) {
        if (exponent == null || exponent.isEmpty()) {
            return 1; // Default exponent for x is 1
        }
        try {
            int parsedExponent = Integer.parseInt(exponent.trim());
            return parsedExponent;
        } catch (NumberFormatException e) {
            System.out.println("Invalid exponent format: '" + exponent + "'. Returning exponent 1 for constants.");
            return 1; // Exponent is 1 for constants
        }
    }
}


