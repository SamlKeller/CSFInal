import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TitleScreenPanel extends JPanel implements SwitchablePanel {
    private MainFrame mainFrame;
    private Timer timer;
    private List<Animatable> animations;

    public TitleScreenPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Graphing Calculator", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        setLayout(null);  // Use absolute positioning

        int panelWidth = 800; // Assume a fixed width for the panel
        int panelHeight = 600; // Assume a fixed height for the panel

        // Center the square
        int squareSize = 100;
        int squareX = (panelWidth - squareSize) / 2;
        int squareY = (panelHeight - squareSize) / 2;

        ImageIcon image = new ImageIcon("icon.png");
        JLabel imageLabel = new JLabel(image); 
        add(imageLabel);

        // Position the label below the square
        int labelY = squareY + squareSize + 20;

        titleLabel.setBounds((panelWidth - 500) / 2, labelY, 500, 50);  // Center the label
        add(titleLabel);

        animations = new ArrayList<>();
        // Outer square
        animations.add(new AnimatedLine(squareX, squareY, squareX + squareSize, squareY, 20)); // Top side
        animations.add(new AnimatedLine(squareX + squareSize, squareY, squareX + squareSize, squareY + squareSize, 20)); // Right side
        animations.add(new AnimatedLine(squareX + squareSize, squareY + squareSize, squareX, squareY + squareSize, 20)); // Bottom side
        animations.add(new AnimatedLine(squareX, squareY + squareSize, squareX, squareY, 20)); // Left side

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
}
