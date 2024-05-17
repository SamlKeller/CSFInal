import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class MouseHandler implements MouseInputListener {
    private CartesianPlanePanel panel;

    public MouseHandler(CartesianPlanePanel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        double xMod = e.getX() - panel.getClickOriginX();
        double yMod = e.getY() - panel.getClickOriginY();
        panel.setModifiers(xMod, yMod);
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
        panel.setClickOrigin(e.getX(), e.getY());
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
