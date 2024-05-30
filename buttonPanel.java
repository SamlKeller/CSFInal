import javax.swing.*;
import java.awt.*;

class buttonPanel extends JPanel {
    private JButton[][] buttonGrid;
    private JButton[] numberButtons = new JButton[10];
    private JButton[] operationsButtons = new JButton[5];
    double num1;
    double num2;

    public buttonPanel() {
        // Button panel with 3 rows and 5 columns
        setLayout(new GridLayout(3, 5));
        setSize(250, 475);

        // Create a 2D array to represent the button layout
        buttonGrid = new JButton[3][5];

        // Instantiating number buttons
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(Integer.toString(i));
            numberButtons[i].setContentAreaFilled(false);
            numberButtons[i].setFont(new Font("Calibri", Font.PLAIN, 30));
        }

        // Instantiating operation buttons
        operationsButtons[0] = new JButton("+");
        operationsButtons[1] = new JButton("-");
        operationsButtons[2] = new JButton("*");
        operationsButtons[3] = new JButton("/");
        operationsButtons[4] = new JButton("=");

        // Setting operation buttons to have special colors
        for (int i = 0; i < 4; i++) {
            operationsButtons[i].setBackground(new Color(51, 205, 255));
        }
        operationsButtons[4].setBackground(new Color(51, 153, 255));

        /*  Placing number buttons into the 2D array
        for (int x = 0; x < 1; x++) {
            for (int i = 0; i < 4; i++) {
                buttonGrid[x][i] = numberButtons[i*x];
            }
        }*/
        
        buttonGrid[0][0] = numberButtons[0];
        buttonGrid[0][1] = numberButtons[1];
        buttonGrid[0][2] = numberButtons[2];
        buttonGrid[0][3] = numberButtons[3];
        buttonGrid[0][4] = numberButtons[4];
        buttonGrid[1][0] = numberButtons[5];
        buttonGrid[1][1] = numberButtons[6];
        buttonGrid[1][2] = numberButtons[7];
        buttonGrid[1][3] = numberButtons[8];
        buttonGrid[1][4] = numberButtons[9];

        // Placing operation buttons into the 2D array
        buttonGrid[2][0] = operationsButtons[0];
        buttonGrid[2][1] = operationsButtons[1];
        buttonGrid[2][2] = operationsButtons[2];
        buttonGrid[2][3] = operationsButtons[3];
        buttonGrid[2][4] = operationsButtons[4];



        // Adding the buttons from the 2D array to the panel
        for (int row = 0; row < buttonGrid.length; row++) {
            for (int col = 0; col < buttonGrid[row].length; col++) {
                add(buttonGrid[row][col]);
            }
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
        updateButtonGrid();
    }

    public void setOperationsButtons(JButton[] temp) {
        operationsButtons = temp;
        updateButtonGrid();
    }

    public JButton[][] getButtonGrid() {
        return buttonGrid;
    }

    public void setButtonGrid(JButton[][] newButtonGrid) {
        buttonGrid = newButtonGrid;
        updatePanelFromGrid();
    }

    private void updateButtonGrid() {
        // Update the 2D array with the new buttons
        buttonGrid[0][0] = numberButtons[0];
        buttonGrid[0][1] = numberButtons[1];
        buttonGrid[0][2] = numberButtons[2];
        buttonGrid[0][3] = numberButtons[3];
        buttonGrid[0][4] = numberButtons[4];
        buttonGrid[1][0] = numberButtons[5];
        buttonGrid[1][1] = numberButtons[6];
        buttonGrid[1][2] = numberButtons[7];
        buttonGrid[1][3] = numberButtons[8];
        buttonGrid[1][4] = numberButtons[9];

        buttonGrid[2][0] = operationsButtons[0];
        buttonGrid[2][1] = operationsButtons[1];
        buttonGrid[2][2] = operationsButtons[2];
        buttonGrid[2][3] = operationsButtons[3];
        buttonGrid[2][4] = operationsButtons[4];

        // Remove all existing buttons from the panel
        removeAll();

        // Add updated buttons from the 2D array to the panel
        for (int row = 0; row < buttonGrid.length; row++) {
            for (int col = 0; col < buttonGrid[row].length; col++) {
                add(buttonGrid[row][col]);
            }
        }

        // Revalidate and repaint the panel to reflect changes
        revalidate();
        repaint();
    }

    private void updatePanelFromGrid() {
        // Remove all existing buttons from the panel
        removeAll();

        // Add buttons from the updated 2D array to the panel
        for (int row = 0; row < buttonGrid.length; row++) {
            for (int col = 0; col < buttonGrid[row].length; col++) {
                add(buttonGrid[row][col]);
            }
        }

        // Revalidate and repaint the panel to reflect changes
        revalidate();
        repaint();
    }
}
