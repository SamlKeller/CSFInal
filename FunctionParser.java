import java.util.ArrayList;

public class FunctionParser {
    private CoefficientParser coefficientParser;
    private ExponentParser exponentParser;

    public FunctionParser() {
        coefficientParser = new CoefficientParser();
        exponentParser = new ExponentParser();
    }

    public ArrayList<Double> parseCoefficients(String equation) {
        ArrayList<Double> coefficients = new ArrayList<>();
        equation = equation.replaceAll("\\s", ""); // Remove whitespace
        String[] parts = equation.split("=");
        if (parts.length != 2) {
            return coefficients; // Invalid equation format
        }
        String rhs = parts[1];

        int i = 0;
        while (i < rhs.length()) {
            StringBuilder coeffStr = new StringBuilder();
            boolean hasX = false;

            // Read the sign
            if (i < rhs.length() && (rhs.charAt(i) == '+' || rhs.charAt(i) == '-')) {
                coeffStr.append(rhs.charAt(i));
                i++;
            }

            // Read the coefficient
            while (i < rhs.length() && (Character.isDigit(rhs.charAt(i)) || rhs.charAt(i) == '.')) {
                coeffStr.append(rhs.charAt(i));
                i++;
            }

            // Check for 'x'
            if (i < rhs.length() && rhs.charAt(i) == 'x') {
                hasX = true;
                i++;
            }

            double coefficient = coefficientParser.parseCoefficient(coeffStr.toString());
            if (!hasX && coeffStr.length() == 0) {
                coefficient = 1.0; // If no coefficient, assume it's 1
            }
            coefficients.add(coefficient);

            // Skip exponent part
            if (i < rhs.length() && rhs.charAt(i) == '^') {
                i++;
                while (i < rhs.length() && Character.isDigit(rhs.charAt(i))) {
                    i++;
                }
            }
        }

        // Remove the last coefficient which is not part of a valid term
    
        return coefficients;
    }

    public ArrayList<Integer> parseExponents(String equation) {
        ArrayList<Integer> exponents = new ArrayList<>();
        equation = equation.replaceAll("\\s", ""); // Remove whitespace
        String[] parts = equation.split("=");
        if (parts.length != 2) {
            return exponents; // Invalid equation format
        }
        String rhs = parts[1];

        int i = 0;
        while (i < rhs.length()) {
            // Skip coefficient part
            if (i < rhs.length() && (rhs.charAt(i) == '+' || rhs.charAt(i) == '-')) {
                i++;
            }
            while (i < rhs.length() && (Character.isDigit(rhs.charAt(i)) || rhs.charAt(i) == '.')) {
                i++;
            }

            // Check for 'x'
            if (i < rhs.length() && rhs.charAt(i) == 'x') {
                i++;
                // Check for exponent part
                if (i < rhs.length() && rhs.charAt(i) == '^') {
                    i++;
                    StringBuilder exponentStr = new StringBuilder();
                    while (i < rhs.length() && Character.isDigit(rhs.charAt(i))) {
                        exponentStr.append(rhs.charAt(i));
                        i++;
                    }
                    int exponent = exponentParser.parseExponent(exponentStr.toString());
                    exponents.add(exponent);
                } else {
                    exponents.add(1); // If no exponent, assume it's 1
                }
            } else {
                exponents.add(0); // If no 'x', the exponent is 0
            }
        }

        // Remove the last exponent which is not part of a valid term
        

        System.out.println(exponents);
        return exponents;
    }
}
