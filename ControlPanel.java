import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField equationField;

    public ControlPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        equationField = new JTextField(20);
        JButton plotButton = new JButton("Plot");

        inputPanel.add(new JLabel("Enter Equation:"));
        inputPanel.add(equationField);
        inputPanel.add(plotButton);

        add(inputPanel, BorderLayout.NORTH);
    }

    public void clearInput() {
        equationField.setText("");
    }
}
