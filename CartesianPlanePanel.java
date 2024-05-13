import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/*
 * The CartesianPlanePanel class extends JPanel and is responsible for rendering a Cartesian plane
 * with a curve drawn based on a given equation. It handles user input for zooming in and out,
 * and generates points for the curve based on the equation. The class has various fields to store
 * the points, center coordinates, scale, zoom level, and other related properties.
 */
public class CartesianPlanePanel extends JPanel {
    private ArrayList<Point> points;
    private int xCenter, yCenter;
    private int scale = 50;
    private double zoomLevel = 1.0;
    private double maxZoomLevel = 10.0;
    private double minZoomLevel = 0.1;

    /*
     * The constructor takes an equation as a string and initializes the panel. It generates points
     * for the curve based on the equation and adds key and mouse wheel listeners for zooming functionality.
     * If the equation format is invalid, it displays an error message.
     */
    public CartesianPlanePanel(String equation) {
        points = new ArrayList<>();
        if (generatePoints(equation)) {
            addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_EQUALS) zoomIn();
                    else if (e.getKeyCode() == KeyEvent.VK_MINUS) zoomOut();
                }
            });

            addMouseWheelListener(e -> {
                if (e.getWheelRotation() < 0) zoomIn();
                else zoomOut();
            });
        } else {
            JOptionPane.showMessageDialog(this, "Invalid equation format. Please provide an equation in the form 'y = ax^3 + bx^2 + cx + d' or 'y = sqrt(ax^3 + bx^2 + cx + d)'", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * The zoomIn method increases the zoom level if it is below the maximum zoom level, and
     * repaints the panel to reflect the changes.
     */
    private void zoomIn() {
        if (zoomLevel < maxZoomLevel) {
            zoomLevel *= 1.2;
            repaint();
        }
    }

    /*
     * The zoomOut method decreases the zoom level if it is above the minimum zoom level, and
     * repaints the panel to reflect the changes.
     */
    private void zoomOut() {
        if (zoomLevel > minZoomLevel) {
            zoomLevel /= 1.2;
            repaint();
        }
    }
   
    /*
     * The generatePoints method parses the given equation string and generates points for the
     * curve based on the coefficients of the equation. It returns false if the equation format
     * is invalid, and true otherwise. It now supports square root equations as well.
     */
    
     private boolean generatePoints(String equation) {
        equation = equation.replaceAll("\\s", "");
        String[] parts = equation.split("=");
        if (parts.length != 2 || !parts[0].equalsIgnoreCase("y")) return false;

        String rhs = parts[1];
        boolean isSquareRoot = rhs.startsWith("sqrt(");
        boolean isCubeRoot = rhs.startsWith("cbrt(");
        if (isSquareRoot) {
            rhs = rhs.substring(5, rhs.length() - 1); // Remove "sqrt(" and ")"
        } else if (isCubeRoot) {
            rhs = rhs.substring(5, rhs.length() - 1); // Remove "cbrt(" and ")"
        }
        String[] terms = rhs.split("(?=[+-])");
        double a = 0, b = 0, c = 0, d = 0;

        for (String term : terms) {
            if (term.endsWith("x^3")) a = parseCoefficient(term.substring(0, term.length() - 3));
            else if (term.endsWith("x^2")) b = parseCoefficient(term.substring(0, term.length() - 3));
            else if (term.endsWith("x")) c = parseCoefficient(term.substring(0, term.length() - 1));
            else d = parseCoefficient(term);
        }

        for (int x = -100; x <= 100; x++) {
            double y = a * Math.pow(x, 3) + b * Math.pow(x, 2) + c * x + d;
            if (isSquareRoot) y = Math.sqrt(y);
            else if (isCubeRoot) y = Math.cbrt(y);
            points.add(new Point(x, (int) y));
        }

        return true;
    }


    /*
     * The parseCoefficient method converts a string representing a coefficient into a double value.
     * If the string is empty, it returns 1.0 (the default coefficient for missing terms). If the
     * string cannot be parsed as a double, it returns 0.0.
     */
    private double parseCoefficient(String coefficient) {
        if (coefficient.isEmpty()) return 1.0;
        try {
            return Double.parseDouble(coefficient);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /*
     * The paintComponent method is responsible for rendering the Cartesian plane and the curve.
     * It draws the axes, sets the color of the curve to red, and then draws line segments
     * connecting the points generated for the curve.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Dimension size = getSize();
        xCenter = size.width / 2;
        yCenter = size.height / 2;
        g2d.drawLine(0, yCenter, size.width, yCenter);
        g2d.drawLine(xCenter, 0, xCenter, size.height);

        g2d.setColor(Color.RED);
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            int x1 = xCenter + (int) (p1.x * scale * zoomLevel);
            int y1 = yCenter - (int) (p1.y * scale * zoomLevel);
            int x2 = xCenter + (int) (p2.x * scale * zoomLevel);
            int y2 = yCenter - (int) (p2.y * scale * zoomLevel);
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    /*
     * The main method creates a JFrame, sets its content pane to a CartesianPlanePanel instance
     * with a given equation, sets the frame size and visibility, and handles the program's exit.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cartesian Plane Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new CartesianPlanePanel("y = cbrt(x-1)")); // Example square root equation
            frame.setSize(600, 600);
            frame.setVisible(true);
        });
    }
}