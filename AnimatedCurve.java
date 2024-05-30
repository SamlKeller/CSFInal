import java.awt.*;
import java.awt.geom.Path2D;

public class AnimatedCurve extends AnimatedLine {
    private int[][] curvePoints;

    public AnimatedCurve(int x1, int y1, int x2, int y2, int x3, int y3, int maxSteps) {
        super(x1, y1, x2, y2, maxSteps);
        this.curvePoints = new int[maxSteps + 1][2];

        // Calculate intermediate points for the quadratic Bézier curve and store in the 2D array
        for (int i = 0; i <= maxSteps; i++) {
            double t = (double) i / maxSteps;
            curvePoints[i][0] = (int) ((1 - t) * (1 - t) * x1 + 2 * (1 - t) * t * x2 + t * t * x3);
            curvePoints[i][1] = (int) ((1 - t) * (1 - t) * y1 + 2 * (1 - t) * t * y2 + t * t * y3);
        }
    }

    @Override
    public void drawMe(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Path2D path = new Path2D.Double();
        path.moveTo(curvePoints[0][0], curvePoints[0][1]);

        for (int i = 1; i <= step; i++) {
            path.lineTo(curvePoints[i][0], curvePoints[i][1]);
        }

        g2d.draw(path);
    }

    // Getter and Setter for curvePoints (optional, if you want to access or modify the points array externally)
    public int[][] getCurvePoints() {
        return curvePoints;
    }

    public void setCurvePoints(int[][] curvePoints) {
        this.curvePoints = curvePoints;
    }
}
