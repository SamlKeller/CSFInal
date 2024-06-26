import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class CartesianPlanePanel extends JPanel {
    public ArrayList<Point> points;
    private double xMod, yMod;
    private double xCenter, yCenter;
    private int scale = 425; // Adjust this value to change the initial scale
    private double zoomLevel = 0.3; // Initial zoom level
    public double resolution = 1; // Resolution factor
    private double maxZoomLevel = 1; // Maximum zoom level
    private double minZoomLevel = 0.01; // Minimum zoom level
    public int clickOriginx, clickOriginy;
    private String equation;

    public CartesianPlanePanel(String equation) {
        this.equation = equation;
        points = new ArrayList<>();
        FunctionValidator parser = new FunctionValidator();
        PointGenerator generator = new PointGenerator();
        EquationValidator validator = new EquationValidator();

        if (parser.isValidEquation(equation)) {
            points = generator.generatePoints(equation, resolution);

            ZoomHandler zoomHandler = new ZoomHandler(this);
            MouseHandler mouseHandler = new MouseHandler(this);

            addKeyListener(zoomHandler);
            addMouseWheelListener(zoomHandler);
            addMouseMotionListener(mouseHandler);
            addMouseListener(mouseHandler);
            setFocusable(true);
            requestFocusInWindow();
        } else {
            try {
                JOptionPane.showMessageDialog(this, validator.eval(equation.trim()));
            } catch (RuntimeException e) {
                //JOptionPane.showMessageDialog(this, "Error evaluating function"); Clearly, something is wrong with our rendering pipeline -- this results in an infinite error loop
                //Are we running this every frame?  That would be ... bad
            }
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
        g2d.drawLine(0, (int) yCenter, size.width, (int) yCenter);
        g2d.drawLine((int) xCenter, 0, (int) xCenter, size.height);

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

    public void zoomIn() {
        if (zoomLevel < maxZoomLevel) {
            zoomLevel *= 1.2;
            resolution /= 1.2; // Increase resolution as we zoom in
            updatePoints();
            repaint();
        }
    }

    public void zoomOut() {
        if (zoomLevel > minZoomLevel) {
            zoomLevel /= 1.2; // Adjust the zoom factor as needed
            resolution *= 1.2; // Decrease resolution as we zoom out
            updatePoints();
            repaint();
        }
    }

    private void updatePoints() {
        PointGenerator generator = new PointGenerator();
        points = generator.generatePoints(equation, resolution);
    }

    public void setModifiers(double xMod, double yMod) {
        this.xMod = xMod;
        this.yMod = yMod;
    }

    public double getXMod() {
        return xMod;
    }

    public double getYMod() {
        return yMod;
    }

    public int getClickOriginX() {
        return clickOriginx;
    }

    public int getClickOriginY() {
        return clickOriginy;
    }

    public void setPoints(ArrayList<Point> x) {
        points = x;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setClickOrigin(int x, int y) {
        this.clickOriginx = x;
        this.clickOriginy = y;
    }
}
