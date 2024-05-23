import java.awt.*;

public class AnimatedLine implements Animatable {
    private int x1, y1, x2, y2;
    private int step;
    private int maxSteps;

    public AnimatedLine(int x1, int y1, int x2, int y2, int maxSteps) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.step = 0;
        this.maxSteps = maxSteps;
    }

    @Override
    public void step() {
        if (step < maxSteps) {
            step++;
        }
    }

    @Override
    public void drawMe(Graphics g) {
        int currentX = x1 + (x2 - x1) * step / maxSteps;
        int currentY = y1 + (y2 - y1) * step / maxSteps;
        g.drawLine(x1, y1, currentX, currentY);
    }
}
