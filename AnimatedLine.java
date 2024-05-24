import java.awt.*;

public class AnimatedLine implements Animatable {
    private int[][] points;
    private int step;
    private int maxSteps;

    public AnimatedLine(int x1, int y1, int x2, int y2, int maxSteps) {
        this.step = 0;
        this.maxSteps = maxSteps;
        this.points = new int[maxSteps + 1][2];

        // Calculate intermediate points and store in the 2D array
        for (int i = 0; i <= maxSteps; i++) {
            points[i][0] = x1 + (x2 - x1) * i / maxSteps;
            points[i][1] = y1 + (y2 - y1) * i / maxSteps;
        }
    }

    @Override
    public void step() {
        if (step < maxSteps) {
            step++;
        }
    }

    @Override
    public void drawMe(Graphics g) {
        g.drawLine(points[0][0], points[0][1], points[step][0], points[step][1]);
    }

    // Getter and Setter for points (optional, if you want to access or modify the points array externally)
    public int[][] getPoints() {
        return points;
    }

    public void setPoints(int[][] points) {
        this.points = points;
    }
}
