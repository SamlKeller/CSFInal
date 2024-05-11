import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CartesianPlanePanel extends JPanel {
    private ArrayList<Point> points;
    private int xCenter, yCenter;
    private int scale = 50; // Adjust this value to change the initial scale
    private double zoomLevel = 1.0; // Initial zoom level
    private int numericScale = 5; // Scale of numeric values on the axes
    private double maxZoomLevel = 10.0; // Maximum zoom level
    private double minZoomLevel = 0.1; // Minimum zoom level

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
            JOptionPane.showMessageDialog(this, "Invalid equation format. Please provide an equation in the form 'y = ax^3 + bx^2 + cx + d'", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        String[] terms = rhs.split("(?=[+-])");
        double a = 0, b = 0, c = 0, d = 0;

        for (String term : terms) {
            if (term.endsWith("x^3")) {
                a = parseCoefficient(term.substring(0, term.length() - 3));
            } else if (term.endsWith("x^2")) {
                b = parseCoefficient(term.substring(0, term.length() - 3));
            } else if (term.endsWith("x")) {
                c = parseCoefficient(term.substring(0, term.length() - 1));
            } else {
                d = parseCoefficient(term);
            }
        }

        for (int x = -100; x <= 100; x++) {
            double y = a * Math.pow(x, 3) + b * Math.pow(x, 2) + c * x + d;
            points.add(new Point(x, (int) y));
        }

        return true;
    }

    private double parseCoefficient(String coefficient) {
        if (coefficient.isEmpty()) {
            return 1.0; // Default coefficient for missing term
        }

        try {
            return Double.parseDouble(coefficient);
        } catch (NumberFormatException e) {
            return 0.0; // Fallback to 0 for invalid coefficients
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw axes
        Dimension size = getSize();
        xCenter = size.width / 2;
        yCenter = size.height / 2;
        g2d.drawLine(0, yCenter, size.width, yCenter);
        g2d.drawLine(xCenter, 0, xCenter, size.height);

        // Draw lines
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Cartesian Plane Panel");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(new CartesianPlanePanel("y = x^3 - 2x^2 + x - 3")); // Provide the equation here
                frame.setSize(600, 600);
                frame.setVisible(true);
            }
        });
    }
}