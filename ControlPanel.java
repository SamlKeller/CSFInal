import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        plotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String equation = equationField.getText();
            }
        });

        inputPanel.add(new JLabel("Enter Equation:"));
        inputPanel.add(equationField);
        inputPanel.add(plotButton);

        add(inputPanel, BorderLayout.NORTH);
    }

    public void clearInput() {
        equationField.setText("");
    }
}
