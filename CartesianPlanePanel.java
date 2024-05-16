import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;


public class CartesianPlanePanel extends JPanel implements MouseInputListener {
    private ArrayList<Point> points;
    private double xMod, yMod;
    private double xCenter, yCenter;
    private int scale = 425; // Adjust this value to change the initial scale
    private double zoomLevel = 1.0; // Initial zoom level
    private int numericScale = 5; // Scale of numeric values on the axes
    private double maxZoomLevel = 1000; // Maximum zoom level
    private double minZoomLevel = 0.1; // Minimum zoom level

    // Done: Fixed functions like x^5
    // TODO: Fix negative functions like y=-x
    
    public CartesianPlanePanel(String equation) {
        points = new ArrayList<>();
        if (generatePoints(equation)) {
            // Add keyboard shortcuts for zooming
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
                        zoomIn();
                    } else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                        zoomOut();
                    }
                }
            });

            addMouseMotionListener(this);
            addMouseListener(this);

            // Add mouse wheel zooming
            addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    if (e.getWheelRotation() < 0) {
                        zoomIn();
                    } else {
                        zoomOut();
                    }
                }
            });
        } else {
            try {
                JOptionPane.showMessageDialog(this, eval(equation.trim()));
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, "Error evaluating function");
            }
        }
    }

    public static double eval(final String str) throws RuntimeException {
        return new Object() {
            int pos = -1, ch;
  
            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }
  
            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }
  
            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }
  
            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number
            //        | functionName `(` expression `)` | functionName factor
            //        | factor `^` factor
  
            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }
  
            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }
  
            double parseFactor() {
                if (eat('+')) return +parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus
  
                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                    } else {
                        x = parseFactor();
                    }
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }
  
                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
  
                return x;
            }
        }.parse();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Fix clicking on drag start
        xMod = (e.getX() - clickOriginx);
        yMod = (e.getY() - clickOriginy);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    public int clickOriginx, clickOriginy;
    @Override
    public void mousePressed(MouseEvent e) {
        clickOriginx = e.getX();
        clickOriginy = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void zoomIn() {
        if (zoomLevel < maxZoomLevel) {
            zoomLevel *= 1.2;
            repaint();
        }
    }

    private void zoomOut() {
        if (zoomLevel > minZoomLevel) {
            zoomLevel /= 1.2; // Adjust the zoom factor as needed
            repaint();
        }
    }

    private Point getCartesianPoint(Point point) {
        int x = (int) ((point.x - xCenter) / (scale * zoomLevel));
        int y = (int) ((yCenter - point.y) / (scale * zoomLevel));
        return new Point(x, y);
    }

    private boolean generatePoints(String equation) {
        equation = equation.replaceAll("\\s", ""); // Remove whitespace
        String[] parts = equation.split("=");
        if (parts.length != 2 || !parts[0].equalsIgnoreCase("y")) {
            return false; // Invalid equation format
        }

        String rhs = parts[1];
        ArrayList<Double> coefficients = new ArrayList<>();
        ArrayList<Integer> exponents = new ArrayList<>();

        // Regular expression to parse terms in the form ax^b, ax, -ax^b, -ax, or a
        Pattern pattern = Pattern.compile("([+-]?\\d*\\.?\\d*)(x\\^?(\\d*))?");
        Matcher matcher = pattern.matcher(rhs);

        while (matcher.find()) {
            String coeffStr = matcher.group(1);
            System.out.println(coeffStr);
            System.out.println(coeffStr.length());
            String exponentStr = matcher.group(3);

            double coefficient = parseCoefficient(coeffStr);
            int exponent = parseExponent(exponentStr);

            coefficients.add(coefficient);
            exponents.add(exponent);
        }

        coefficients.remove(coefficients.size()- 1);
        System.out.println(coefficients.toString());

        for (int x = -1000; x <= 1000; x += 1) {
            double y = 0;
            for (int i = 0; i < coefficients.size(); i++) {
                y += coefficients.get(i) * Math.pow(x, exponents.get(i));
            }
            points.add(new Point(x, (int) y));
        }

        return true;
    }

    private double parseCoefficient(String coefficient) {
        if ((coefficient == null || coefficient.isEmpty() || coefficient.equals("+"))) {
            System.out.println("pos coeff");
            return 1.0; // Default coefficient for missing term
        } else if (coefficient.equals("-")) {
            System.out.println("neg coeff");
            return -1.0; // Coefficient is -1
        }

        try {
            return Double.parseDouble(coefficient);
        } catch (NumberFormatException e) {
            return 0.0; // Fallback to 0 for invalid coefficients
        }
    }

    private int parseExponent(String exponent) {
        if (exponent == null || exponent.isEmpty()) {
            return 1; // Default exponent is 1 for 'x'
        }
    
        try {
            return Integer.parseInt(exponent);
        } catch (NumberFormatException e) {
            return 0; // Fallback to 0 for invalid exponents, which means 'x' is just a constant term
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw axes
        Dimension size = getSize();
        xCenter = (size.width / 2) + xMod;
        yCenter = (size.height / 2) + yMod;
        g2d.drawLine(0, (int)yCenter, size.width, (int)yCenter);
        g2d.drawLine((int)xCenter, 0, (int)xCenter, size.height);

        // Draw smooth line
        if (!points.isEmpty()) {
            Path2D path = new Path2D.Double();
            Point firstPoint = points.get(0);
            double startX = xCenter + firstPoint.x * scale * zoomLevel;
            double startY = yCenter - firstPoint.y * scale * zoomLevel;
            path.moveTo(startX, startY);

            for (int i = 1; i < points.size(); i++) {
                Point p = points.get(i);
                double x = xCenter + p.x * scale * zoomLevel;
                double y = yCenter - p.y * scale * zoomLevel;
                path.lineTo(x, y);
            }

            g2d.setColor(Color.RED);
            g2d.draw(path);
        }
    }
}
