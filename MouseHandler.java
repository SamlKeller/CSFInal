import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class MouseHandler implements MouseInputListener {
    private CartesianPlanePanel panel;
    private int prevX, prevY;

    public MouseHandler(CartesianPlanePanel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int deltaX = e.getX() - prevX;
        int deltaY = e.getY() - prevY;

        prevX = e.getX();
        prevY = e.getY();

        double newXMod = panel.getXMod() + deltaX;
        double newYMod = panel.getYMod() + deltaY;

        panel.setModifiers(newXMod, newYMod);
        panel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevX = e.getX();
        prevY = e.getY();
        panel.setClickOrigin(prevX, prevY);
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
}
