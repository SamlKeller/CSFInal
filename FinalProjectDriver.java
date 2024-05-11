import javax.swing.*;


public class FinalProjectDriver
{

   public static void main(String[] args)
   { 
      JFrame frame = new JFrame("Graphing Calculator");
      frame.setSize(325, 475);  
      frame.setLocation(20, 20);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      ImageIcon img = new ImageIcon("./icon.png");
      frame.setContentPane(new FinalProjectPanel());
      frame.setIconImage(img.getImage());
      frame.setVisible(true);
   }
}