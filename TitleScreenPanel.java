import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TitleScreenPanel extends JPanel implements SwitchablePanel {
    @SuppressWarnings("unused")
    private MainFrame mainFrame;
    private Timer timer;
    private List<Animatable> animations;
    public int panelHeight = 600; // Assume a fixed height for the panel
    public int panelWidth = 800; // Assume a fixed width for the panel

    public TitleScreenPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Graphing Calculator", JLabel.CENTER);
        JLabel subtitle = new JLabel("Click anywhere to start", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        subtitle.setFont(new Font("Arial", Font.PLAIN, 28));
        setLayout(null);  // Use absolute positioning

        // Center the square
        int squareSize = 100;
        int squareX = (panelWidth - squareSize) / 2;
        int squareY = ((panelHeight - squareSize) / 2) - 100;

        // Position the labels below the square
        int labelY = squareY + squareSize + 20;
        titleLabel.setBounds((panelWidth - 500) / 2, labelY, 500, 50);  // Center the label
        subtitle.setBounds((panelWidth - 500) / 2, labelY + 50, 500, 50);
        add(titleLabel);
        add(subtitle);

        animations = new ArrayList<>();
        // Outer square
        animations.add(new AnimatedLine(squareX, squareY, squareX + squareSize, squareY, 20)); // Top side
        animations.add(new AnimatedLine(squareX + squareSize, squareY, squareX + squareSize, squareY + squareSize, 20)); // Right side
        animations.add(new AnimatedLine(squareX + squareSize, squareY + squareSize, squareX, squareY + squareSize, 20)); // Bottom side
        animations.add(new AnimatedLine(squareX, squareY + squareSize, squareX, squareY, 20)); // Left side

        // Inner curve
        int curvePadding = 20; // Padding from the edge of the box for the curve
        int curveX1 = squareX + curvePadding;
        int curveY1 = squareY + squareSize / 2;
        int curveX2 = squareX + squareSize / 2;
        int curveY2 = squareY + curvePadding;
        int curveX3 = squareX + squareSize - curvePadding;
        int curveY3 = squareY + squareSize / 2;
        animations.add(new AnimatedCurve(curveX1, curveY1, curveX2, curveY2, curveX3, curveY3, 20));

        timer = new Timer(50, e -> {
            for (Animatable anim : animations) {
                anim.step();
            }
            repaint();
        });
        timer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.stop();
                mainFrame.showControlPanel();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Animatable anim : animations) {
            anim.drawMe(g);
        }
    }

    @Override
    public void switchTo() {
        // Implementation for switching to this panel
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    public void setPanelHeight(int x) {
        panelHeight = x;
    }

    public void setPanelWidth(int x) {
        panelWidth = x;
    }
}