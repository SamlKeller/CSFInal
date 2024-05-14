
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.border.*;
import java.util.ArrayList;

class FinalProjectPanel extends JPanel
{  
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

     
   public FinalProjectPanel()
   {
      myImage = new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB); 
      myBuffer = myImage.getGraphics(); 
      myBuffer.setColor(BACKGROUND);    
      myBuffer.fillRect(0,0,FRAME,FRAME);
      frameCounter = 0;
      
      animationObjects = new ArrayList<Animatable>();        
      setLayout(new BorderLayout());
      
      JPanel northPanel = new JPanel();
      add(northPanel, BorderLayout.NORTH);
      
      buttonPanel z = new buttonPanel();    
      numberButtons = z.getNumberButtons();
      operationsButtons = z.getOperationsButtons();

      CartesianPlanePanel e = new CartesianPlanePanel("y = x^3 - 2x^2 + x - 3"); 
      
      
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

      e.setSize(600,600);
     
      t = new Timer(5, new AnimationListener());  
      t.start();  
   }
   
      
   public void paintComponent(Graphics g) {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null); 
   }
         
   public void animate()
   {
      myBuffer.setColor(BACKGROUND);
      myBuffer.fillRect(0,0,FRAME,FRAME);
      
      for(Animatable animationObject : animationObjects)
      {
         animationObject.step();        
         animationObject.drawMe(myBuffer);          
      }
               
      repaint();
   } 
   
   
   private class AnimationListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e) {
         animate();
      }
   }
   
   public class numberButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         for(int i = 0; i < 10; i++) {
            if(e.getSource() == numberButtons[i]) {
               if (number.getText().equals("0.0")) {
                  number.setText("");
               }
               number.setText(number.getText() + String.valueOf(i)); 
            }
         }
      }
   }
   

   private class TextAreaListener implements KeyListener {
    private JTextArea textArea;

    public TextAreaListener(JTextArea textArea) {
        this.textArea = textArea;
    }

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
            String input = textArea.getText();
            System.out.println("Input from JTextArea: " + input);
            textArea.setText(""); // Clear the text area after printing
        }
    }
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
            
            if(operator == '+') {
               displayNumber = num1 + num2;
               number.setText(Double.toString(displayNumber));
            } else if (operator == '-') {
               displayNumber = num1 - num2;
               number.setText(Double.toString(displayNumber)); 
            } else if (operator == '*') {
               displayNumber = num1 * num2;
               number.setText(Double.toString(displayNumber)); 
            } else if (operator == '/') {
               displayNumber = num1 / num2;
               number.setText(Double.toString(displayNumber)); 
            }
         }
      }
   }
}
