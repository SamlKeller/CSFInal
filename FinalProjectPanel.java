import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.border.*;
import java.util.ArrayList;

class FinalProjectPanel extends JPanel {
    //Fields
    public static final int FRAME = 500;
    private static final Color BACKGROUND = new Color(255, 255, 255);
    private BufferedImage myImage;
    private Graphics myBuffer;
    private Timer t;
    private ArrayList<Animatable> animationObjects;
    private int frameCounter;
    private double displayNumber = 0;
    JTextArea number = new JTextArea(Double.toString(displayNumber));
    JButton[] numberButtons = new JButton[10];
    JButton[] operationsButtons = new JButton[5];
    private char operator;
    double num1;
    double num2;

    public FinalProjectPanel() {
        myImage = new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
        myBuffer = myImage.getGraphics();
        myBuffer.setColor(BACKGROUND);
        myBuffer.fillRect(0, 0, FRAME, FRAME);
        frameCounter = 0;

        animationObjects = new ArrayList<Animatable>();
        setLayout(new BorderLayout());

        number.setBorder(new EmptyBorder(10, 10, 10, 10));
        number.setFont(new Font("Calibri", Font.BOLD, 40));
        JPanel northPanel = new JPanel();
        GridLayout northLayout = new GridLayout(1, 3);
        northPanel.setLayout(northLayout);
        northPanel.add(number);

        add(northPanel, BorderLayout.NORTH);

        buttonPanel z = new buttonPanel();
        numberButtons = z.getNumberButtons();
        operationsButtons = z.getOperationsButtons();

        CartesianPlanePanel e = new CartesianPlanePanel("y = x^9 ");

        //Setting all the number buttons to have a Windows system default esque look

        for (int j = 0; j < numberButtons.length; j++) {
            numberButtons[j].addActionListener(new numberButtonListener());
        }

        for (int h = 0; h < operationsButtons.length; h++) {
            operationsButtons[h].addActionListener(new operationsButtonListener());
        }

        z.setNumberButtons(numberButtons);
        z.setOperationsButtons(operationsButtons);

        add(z, BorderLayout.WEST);
        add(e, BorderLayout.CENTER);

        e.setSize(600, 600);

        t = new Timer(5, new AnimationListener());
        t.start();

        number.addKeyListener(new TextAreaListener());
    }

    public void paintComponent(Graphics g) {
        g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
    }

    public void animate() {
        myBuffer.setColor(BACKGROUND);
        myBuffer.fillRect(0, 0, FRAME, FRAME);

        for (Animatable animationObject : animationObjects) {
            animationObject.step();
            animationObject.drawMe(myBuffer);
        }

        repaint();
    }

    private class AnimationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            animate();
        }
    }

    public class numberButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 10; i++) {
                if (e.getSource() == numberButtons[i]) {
                    if (number.getText().equals("0.0")) {
                        number.setText("");
                    }
                    number.setText(number.getText() + String.valueOf(i));
                }
            }
        }
    }

    private class TextAreaListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // Not used
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // Not used
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                updateGraph(number.getText());
                number.setText(""); // Clear the text area after updating the graph
            }
        }
    }

    private void updateGraph(String equation) {
        // Remove the existing CartesianPlanePanel
        removeAll();
        revalidate();
        repaint();

        // Create a new CartesianPlanePanel with the new equation
        CartesianPlanePanel graphPanel = new CartesianPlanePanel(equation);
        add(graphPanel, BorderLayout.CENTER);

        // Add the other components back
        JPanel northPanel = new JPanel();
        GridLayout northLayout = new GridLayout(1, 3);
        northPanel.setLayout(northLayout);
        northPanel.add(number);
        add(northPanel, BorderLayout.NORTH);

        buttonPanel buttonPanel = new buttonPanel();
        numberButtons = buttonPanel.getNumberButtons();
        operationsButtons = buttonPanel.getOperationsButtons();

        for (int j = 0; j < numberButtons.length; j++) {
            numberButtons[j].addActionListener(new numberButtonListener());
        }

        for (int h = 0; h < operationsButtons.length; h++) {
            operationsButtons[h].addActionListener(new operationsButtonListener());
        }

        buttonPanel.setNumberButtons(numberButtons);
        buttonPanel.setOperationsButtons(operationsButtons);
        add(buttonPanel, BorderLayout.WEST);

        revalidate();
        repaint();
    }

    private class operationsButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
          if (e.getSource() == operationsButtons[0]) {
              num1 = Double.parseDouble(number.getText());
              operator = '+';
              number.setText("");
          } else if (e.getSource() == operationsButtons[1]) {
              num1 = Double.parseDouble(number.getText());
              operator = '-';
              number.setText("");
          } else if (e.getSource() == operationsButtons[2]) {
              num1 = Double.parseDouble(number.getText());
              operator = '*';
              number.setText("");
          } else if (e.getSource() == operationsButtons[3]) {
              num1 = Double.parseDouble(number.getText());
              operator = '/';
              number.setText("");
          } else if (e.getSource() == operationsButtons[4]) {
              num2 = Double.parseDouble(number.getText());
  
              if (operator == '+') {
                  displayNumber = num1 + num2;
              } else if (operator == '-') {
                  displayNumber = num1 - num2;
              } else if (operator == '*') {
                  displayNumber = num1 * num2;
              } else if (operator == '/') {
                  displayNumber = num1 / num2;
              }
  
              // Update the graph with the calculated number
              String equation = "y = " + displayNumber;
              updateGraph(equation);
  
              // Update the JTextArea with the calculated number
              number.setText(Double.toString(displayNumber));
          }
      }
  }
}