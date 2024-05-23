import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class TJStudentApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("TJStudent App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(new TJStudentPanel());
        frame.setVisible(true);
    }
}

class TJStudent {
    private String name;
    private String year;
    private int age;

    public TJStudent(String name, String year, int age) {
        this.name = name;
        this.year = year;
        this.age = age;
    }

    public TJStudent(String filename) throws Exception {
        Scanner infile = new Scanner(new File(filename));
        name = infile.nextLine().strip();
        year = infile.nextLine().strip();
        age = Integer.parseInt(infile.nextLine().strip());
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public int getAge() { return age; }
    public void setAge(int age) {
        if ((age > 10) && (age < 20)) {
            this.age = age; 
        } else {
            System.out.println("Error: " + age + " is not a valid age.");
        }
    }

    public void saveToFile(String filename) throws Exception {
        PrintWriter outfile = new PrintWriter(new File(filename));
        outfile.println(name);
        outfile.println(year);
        outfile.println(age);
        outfile.close();
    }
}

class TJStudentPanel extends JPanel {
    private TJStudent student;
    private JTextField nameField, yearField, ageField, fileField;

    public TJStudentPanel() {
        student = new TJStudent("TJ Student", "Freshman", 14);
        setLayout(new BorderLayout());

        JPanel fieldPanel = new JPanel(new GridLayout(3, 2));
        fieldPanel.add(new JLabel("Name:"));
        nameField = new JTextField(student.getName());
        fieldPanel.add(nameField);

        fieldPanel.add(new JLabel("Year:"));
        yearField = new JTextField(student.getYear());
        fieldPanel.add(yearField);

        fieldPanel.add(new JLabel("Age:"));
        ageField = new JTextField(String.valueOf(student.getAge()));
        fieldPanel.add(ageField);

        JPanel buttonPanel = new JPanel();
        JButton showButton = new JButton("Show current values");
        showButton.addActionListener(new ShowListener());
        buttonPanel.add(showButton);

        JButton modifyButton = new JButton("Modify fields as shown");
        modifyButton.addActionListener(new ModifyListener());
        buttonPanel.add(modifyButton);

        JPanel filePanel = new JPanel();
        filePanel.add(new JLabel("Save/Load:"));
        fileField = new JTextField("filename.txt", 20);
        filePanel.add(fileField);

        JButton saveButton = new JButton("Save to file");
        saveButton.addActionListener(new SaveListener());
        filePanel.add(saveButton);

        JButton loadButton = new JButton("Load from file");
        loadButton.addActionListener(new LoadListener());
        filePanel.add(loadButton);

        add(fieldPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);
        add(filePanel, BorderLayout.SOUTH);
    }

    private class ShowListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            nameField.setText(student.getName());
            yearField.setText(student.getYear());
            ageField.setText(String.valueOf(student.getAge()));
        }
    }

    private class ModifyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            student.setName(nameField.getText());
            student.setYear(yearField.getText());
            student.setAge(Integer.parseInt(ageField.getText()));
        }
    }

    private class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                student.saveToFile(fileField.getText());
            } catch (Exception ex) {
                System.out.println("Can't save! " + ex);
            }
        }
    }

    private class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                student = new TJStudent(fileField.getText());
                nameField.setText(student.getName());
                yearField.setText(student.getYear());
                ageField.setText(String.valueOf(student.getAge()));
            } catch (Exception ex) {
                System.out.println("Can't load! " + ex);
            }
        }
    }
}
