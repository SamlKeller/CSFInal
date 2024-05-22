public class FunctionValidator {
    public boolean isValidEquation(String equation) {
        equation = equation.replaceAll("\\s", ""); // Remove whitespace
        String[] parts = equation.split("=");
        return parts.length == 2 && parts[0].equalsIgnoreCase("y");
    }
}
