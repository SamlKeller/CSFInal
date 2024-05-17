import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ZoomHandler extends KeyAdapter implements MouseWheelListener {
    private CartesianPlanePanel panel;

    public ZoomHandler(CartesianPlanePanel panel) {
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
            panel.zoomIn();
        } else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
            panel.zoomOut();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            panel.zoomIn();
        } else {
            panel.zoomOut();
        }
    }
}
