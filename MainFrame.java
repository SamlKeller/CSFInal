import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private TitleScreenPanel titleScreenPanel;
    private FinalProjectPanel finalProjectPanel;

    public MainFrame() {
        setTitle("Graphing  Calculator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        titleScreenPanel = new TitleScreenPanel(this);
        add(titleScreenPanel);
    }

    public void showControlPanel() {
        remove(titleScreenPanel);
        finalProjectPanel = new FinalProjectPanel();
        add(finalProjectPanel);
        revalidate();
        repaint();
        finalProjectPanel.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
