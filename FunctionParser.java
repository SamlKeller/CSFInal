import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionParser {
    private FunctionValidator validator;
    private CoefficientParser coefficientParser;
    private ExponentParser exponentParser;

    public FunctionParser() {
        validator = new FunctionValidator();
        coefficientParser = new CoefficientParser();
        exponentParser = new ExponentParser();
    }

    public boolean isValidEquation(String equation) {
        return validator.isValidEquation(equation);
    }

    public ArrayList<Double> parseCoefficients(String equation) {
        ArrayList<Double> coefficients = new ArrayList<>();
        equation = equation.replaceAll("\\s", ""); // Remove whitespace
        String[] parts = equation.split("=");
        String rhs = parts[1];

        //Unclear why we're using regex here or what it does
        Pattern pattern = Pattern.compile(("([+-]?\\d*\\.?\\d*)(x\\^?(\\d*))?"));
        Matcher matcher = pattern.matcher(rhs);

        while (matcher.find()) {
            String coeffStr = matcher.group(1);
            double coefficient = coefficientParser.parseCoefficient(coeffStr);
            coefficients.add(coefficient);
        }

        coefficients.remove(coefficients.size() - 1);
    
        return coefficients;
    }

    public ArrayList<Integer> parseExponents(String equation) {
        ArrayList<Integer> exponents = new ArrayList<>();
        equation = equation.replaceAll("\\s", ""); // Remove whitespace
        String[] parts = equation.split("=");
        String rhs = parts[1];

        //same here
        Pattern pattern = Pattern.compile("([+-]?\\d*\\.?\\d*)(x\\^?(\\d*))?");
        Matcher matcher = pattern.matcher(rhs);

        while (matcher.find()) {
            exponents.add(exponentParser.parseExponent(matcher.group(3)));
        }

        exponents.remove(exponents.size() - 1);
        return exponents;
    }
}
