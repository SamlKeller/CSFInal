import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class TJStudentProgram extends JFrame {
    private JTextField nameField, yearField, ageField, fileField;
    private TJStudent student;

    public TJStudentProgram() {
        setTitle("TJStudent Interactive Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        JLabel objectModificationLabel = new JLabel("Object modification:");
        add(objectModificationLabel);

        JButton showCurrentValuesButton = new JButton("Show current values");
        showCurrentValuesButton.addActionListener(new ShowCurrentValuesListener());
        add(showCurrentValuesButton);

        JButton modifyFieldsButton = new JButton("Modify fields as shown");
        modifyFieldsButton.addActionListener(new ModifyFieldsListener());
        add(modifyFieldsButton);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Year:"));
        yearField = new JTextField();
        add(yearField);

        add(new JLabel("Age:"));
        ageField = new JTextField();
        add(ageField);

        add(new JLabel("Save/Load:"));
        fileField = new JTextField("filename.txt");
        add(fileField);

        JButton saveToFileButton = new JButton("Save to file");
        saveToFileButton.addActionListener(new SaveToFileListener());
        add(saveToFileButton);

        JButton loadFromFileButton = new JButton("Load from file");
        loadFromFileButton.addActionListener(new LoadFromFileListener());
        add(loadFromFileButton);

        student = new TJStudent("John Doe", "Freshman", 14);
        pack();
        setLocationRelativeTo(null);
    }

    private class ShowCurrentValuesListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            nameField.setText(student.getName());
            yearField.setText(student.getYear());
            ageField.setText(String.valueOf(student.getAge()));
        }
    }

    private class ModifyFieldsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            student.setName(nameField.getText());
            student.setYear(yearField.getText());
            student.setAge(Integer.parseInt(ageField.getText()));
        }
    }

    private class SaveToFileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                FileWriter writer = new FileWriter(fileField.getText());
                writer.write(student.getName() + "\n");
                writer.write(student.getYear() + "\n");
                writer.write(String.valueOf(student.getAge()) + "\n");
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class LoadFromFileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Scanner scanner = new Scanner(new File(fileField.getText()));
                String name = scanner.nextLine();
                String year = scanner.nextLine();
                int age = Integer.parseInt(scanner.nextLine());
                student = new TJStudent(name, year, age);
                nameField.setText(name);
                yearField.setText(year);
                ageField.setText(String.valueOf(age));
                scanner.close();
            } catch (Exception ex) {
                System.out.println("Can't load! " + ex);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TJStudentProgram().setVisible(true);
            }
        });
    }
}

class TJStudent {
    private String name;
    private String year;
    private int age;

    public TJStudent(String name, String year, int age) {
        this.name = name;
        setYear(year);
        setAge(age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        if (isValidYear(year)) {
            this.year = year;
        } else {
            System.out.println("Error: " + year + " is not a valid year.");
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (isValidAge(age)) {
            this.age = age;
        } else {
            System.out.println("Error: " + age + " is not a valid age.");
        }
    }

    private boolean isValidYear(String year) {
        return year.equalsIgnoreCase("Freshman") || year.equalsIgnoreCase("Sophomore") ||
                year.equalsIgnoreCase("Junior") || year.equalsIgnoreCase("Senior");
    }

    private boolean isValidAge(int age) {
        return age > 10 && age < 20;
    }
}