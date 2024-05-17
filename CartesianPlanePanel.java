import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class CartesianPlanePanel extends JPanel {
    private ArrayList<Point> points;
    private double xMod, yMod;
    private double xCenter, yCenter;
    private int scale = 425; // Adjust this value to change the initial scale
    private double zoomLevel = 1.0; // Initial zoom level
    private double maxZoomLevel = 1000; // Maximum zoom level
    private double minZoomLevel = 0.1; // Minimum zoom level
    public int clickOriginx, clickOriginy;

    public CartesianPlanePanel(String equation) {
        points = new ArrayList<>();
        FunctionParser parser = new FunctionParser();
        PointGenerator generator = new PointGenerator();

        if (parser.isValidEquation(equation)) {
            points = generator.generatePoints(equation);

            ZoomHandler zoomHandler = new ZoomHandler(this);
            MouseHandler mouseHandler = new MouseHandler(this);

            addKeyListener(zoomHandler);
            addMouseWheelListener(zoomHandler);
            addMouseMotionListener(mouseHandler);
            addMouseListener(mouseHandler);
        } else {
            EquationValidator validator = new EquationValidator();
            try {
                JOptionPane.showMessageDialog(this, validator.eval(equation.trim()));
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, "Error evaluating function");
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

    public void zoomIn() {
        if (zoomLevel < maxZoomLevel) {
            zoomLevel *= 1.2;
            repaint();
        }
    }

    public void zoomOut() {
        if (zoomLevel > minZoomLevel) {
            zoomLevel /= 1.2; // Adjust the zoom factor as needed
            repaint();
        }
    }

    public void setModifiers(double xMod, double yMod) {
        this.xMod = xMod;
        this.yMod = yMod;
    }

    public int getClickOriginX() {
        return clickOriginx;
    }

    public int getClickOriginY() {
        return clickOriginy;
    }

    public void setClickOrigin(int x, int y) {
        this.clickOriginx = x;
        this.clickOriginy = y;
    }
}
