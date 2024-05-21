import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelSwapAssignment {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Panel Swap Assignment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MainPanel(frame));
        frame.pack();
        frame.setVisible(true);
    }
}

class MainPanel extends JPanel {
    private JFrame myOwner;
    private AddPanel adder;
    private ResultsPanel results;

    public MainPanel(JFrame f) {
        myOwner = f;

        setLayout(new BorderLayout());
        add(new JLabel("Add two numbers!"), BorderLayout.NORTH);

        adder = new AddPanel(this);
        results = new ResultsPanel(this);
        add(adder, BorderLayout.CENTER);
    }

    public void switchSubpanels(int sum) {
        remove(adder);
        results.setSum(sum);
        add(results, BorderLayout.CENTER);
        revalidate();
        repaint();
        myOwner.pack();
    }

    public void switchToAddPanel() {
        remove(results);
        add(adder, BorderLayout.CENTER);
        revalidate();
        repaint();
        myOwner.pack();
    }
}

class AddPanel extends JPanel {
    private MainPanel myOwner;
    private JTextField field1, field2;

    public AddPanel(MainPanel p) {
        myOwner = p;

        setLayout(new GridLayout(3, 3));
        field1 = new JTextField();
        field2 = new JTextField();
        add(new JLabel("First number:"));
        add(field1);
        add(new JLabel("Second number:"));
        add(field2);
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new AddListener());
        add(addButton);
    }

    private class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int num1 = Integer.parseInt(field1.getText());
            int num2 = Integer.parseInt(field2.getText());
            int sum = num1 + num2;
            myOwner.switchSubpanels(sum);
        }
    }
}

class ResultsPanel extends JPanel {
    private MainPanel myOwner;
    private JLabel sumLabel;

    public ResultsPanel(MainPanel p) {
        myOwner = p;

        setLayout(new BorderLayout());
        sumLabel = new JLabel();
        add(sumLabel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(200, 100));
        JButton newNumbersButton = new JButton("New Numbers");
        newNumbersButton.addActionListener(new NewNumbersListener());
        add(newNumbersButton, BorderLayout.SOUTH);
    }

    public void setSum(int sum) {
        sumLabel.setText("Sum: " + sum);
    }

    private class NewNumbersListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            myOwner.switchToAddPanel();
        }
    }
}