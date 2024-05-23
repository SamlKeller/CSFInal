import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private SwitchablePanel titleScreenPanel;
    private SwitchablePanel finalProjectPanel;

    public MainFrame() {
        setTitle("Graphing Calculator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        titleScreenPanel = new TitleScreenPanel(this);
        add(titleScreenPanel.getPanel());
        
    }

    public void showControlPanel() {
        remove(titleScreenPanel.getPanel());
        finalProjectPanel = new FinalProjectPanel();
        add(finalProjectPanel.getPanel());
        revalidate();
        repaint();
        finalProjectPanel.getPanel().requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            ImageIcon img = new ImageIcon("./icon.png");
            frame.setIconImage(img.getImage());
            frame.setVisible(true);
        });
    }
}
