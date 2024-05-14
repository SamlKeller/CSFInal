import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CartesianPlanePanel extends JPanel implements MouseInputListener {
    private ArrayList<Point> points;
    private double xMod, yMod;
    private double xCenter, yCenter;
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
            JOptionPane.showMessageDialog(this, "Invalid equation format. Please provide an equation in the form 'y = ax^3 + bx^2 + cx + d'", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        //fix clicking on drag start
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
        String[] terms = rhs.split("(?=[+-])");
        ArrayList list = new ArrayList<Double>();

        ArrayList justXList = new ArrayList<String>();

        for (int x = 0; x < terms.length; x++) {
            if (terms[x].toString().contains("x")) {
                justXList.add(terms[x].toString());
            }
        }

        for (String term : terms) {
            list.add(parseCoefficient(term.split("x")[0]));
        }


        for (int x = -100; x <= 100; x += 1) {
            double y = 0;
            for (int i = 0; i < list.size(); i++) {
                //y = x^9 + 2x^8 + x^7 + 5x^6 + 4x^5 - 2x^4 + x^3 + x^2 + x - 3
                if (justXList.size() - i - 1 > 0) {
                    y += (double)list.get(i) * Math.pow(x, justXList.size() - i - 1);
                } else {
                    y += (double)list.get(i);
                }
            }
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
        xCenter = (size.width / 2) + xMod;
        yCenter = (size.height / 2) + yMod;
        g2d.drawLine(0, (int)yCenter, size.width, (int)yCenter);
        g2d.drawLine((int)xCenter, 0, (int)xCenter, size.height);

        // Draw lines
        for (int i = 0; i < points.size() - 1; i++) {
            g2d.setColor(Color.RED);
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            double x1 = xCenter + (int) (p1.x * scale * zoomLevel);
            double y1 = yCenter - (int) (p1.y * scale * zoomLevel);
            double x2 = xCenter + (int) (p2.x * scale * zoomLevel);
            double y2 = yCenter - (int) (p2.y * scale * zoomLevel);
            g2d.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        }
    }
}