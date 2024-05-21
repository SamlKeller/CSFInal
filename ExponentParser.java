public class ExponentParser {
    public int parseExponent(String exponent) {
        if (exponent == null || exponent.isEmpty()) {
            System.out.println("Input is null or empty. Returning default exponent 1.");
            return 1; // Default exponent for x is 1
        }
        try {
            int parsedExponent = Integer.parseInt(exponent.trim());
            System.out.println("Parsed exponent: " + parsedExponent);
            return parsedExponent;
        } catch (NumberFormatException e) {
            System.out.println("Invalid exponent format: '" + exponent + "'. Returning exponent 0 for constants.");
            return 0; // Exponent is 0 for constants
        }
    }
}