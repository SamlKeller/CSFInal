import javax.swing.*;
import java.awt.*;

class buttonPanel extends JPanel {

   JButton[] numberButtons = new JButton[10];
   JButton[] operationsButtons = new JButton[5];
   double num1;
   double num2;    

   public buttonPanel() {   
      // Button panel 
      setLayout(new GridLayout(3,5));
      setSize(250, 475);
      
      // Instantiating buttons
      for (int x = 0; x < 10; x++) {
         numberButtons[x] = new JButton(Integer.toString(x));
         numberButtons[x].setContentAreaFilled(false);
         numberButtons[x].setFont(new Font("Calibri", Font.PLAIN, 30));
         add(numberButtons[x]);
      }
      
      operationsButtons[0] = new JButton("+");
      operationsButtons[1] = new JButton("-");
      operationsButtons[2] = new JButton("*");
      operationsButtons[3] = new JButton("/");
      operationsButtons[4] = new JButton("="); 
         
      // Setting operation buttons to have special colors
      for (int x = 0; x < 4; x++) {
         operationsButtons[x].setBackground(new Color(51, 205, 255));
      }
      
      operationsButtons[4].setBackground(new Color(51,153,255));
      
      // Adding the operation buttons to their list  

      for (int g = 0; g < operationsButtons.length; g++) {
         add(operationsButtons[g]);
      }
        
   }
   
   
  public JButton[] getNumberButtons() {
   return numberButtons; 
  } 
  
  public JButton[] getOperationsButtons() {
   return operationsButtons; 
  }
  
  public void setNumberButtons(JButton[] temp) {
   numberButtons = temp; 
  }
  
  public void setOperationsButtons(JButton[] temp) { 
   operationsButtons = temp; 
  }
   
}