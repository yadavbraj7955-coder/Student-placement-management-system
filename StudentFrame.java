package gui;

import javax.swing.*;
import java.awt.*;

import dao.StudentDAO;
import model.Student;

public class StudentFrame extends JFrame {

    JTextField idField;
    JTextField nameField;
    JTextField branchField;
    JTextField cgpaField;
    JTextField skillsField;

    public StudentFrame() {

        setTitle("Student Management");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Student Management");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(250, 25, 300, 40);

        JLabel idLabel = new JLabel("Student ID:");
        idLabel.setBounds(70, 90, 120, 30);

        idField = new JTextField();
        idField.setBounds(200, 90, 300, 30);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(70, 135, 120, 30);

        nameField = new JTextField();
        nameField.setBounds(200, 135, 300, 30);

        JLabel branchLabel = new JLabel("Branch:");
        branchLabel.setBounds(70, 180, 120, 30);

        branchField = new JTextField();
        branchField.setBounds(200, 180, 300, 30);

        JLabel cgpaLabel = new JLabel("CGPA:");
        cgpaLabel.setBounds(70, 225, 120, 30);

        cgpaField = new JTextField();
        cgpaField.setBounds(200, 225, 300, 30);

        JLabel skillsLabel = new JLabel("Skills:");
        skillsLabel.setBounds(70, 270, 120, 30);

        skillsField = new JTextField();
        skillsField.setBounds(200, 270, 300, 30);

        JButton addButton = new JButton("Add");
        addButton.setBounds(40, 350, 100, 35);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(150, 350, 100, 35);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(260, 350, 100, 35);

        JButton viewButton = new JButton("View");
        viewButton.setBounds(370, 350, 100, 35);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(480, 350, 100, 35);

        JButton backButton = new JButton("Back");
        backButton.setBounds(590, 350, 100, 35);


        // ADD
        addButton.addActionListener(e -> {

            try {

                if (idField.getText().isEmpty()
                        || nameField.getText().isEmpty()
                        || branchField.getText().isEmpty()
                        || cgpaField.getText().isEmpty()
                        || skillsField.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields."
                    );

                    return;
                }

                int id =
                    Integer.parseInt(idField.getText());

                String name = nameField.getText();
                String branch = branchField.getText();

                double cgpa =
                    Double.parseDouble(cgpaField.getText());

                String skills = skillsField.getText();

                Student student =
                    new Student(
                        id,
                        name,
                        branch,
                        cgpa,
                        skills
                    );

                StudentDAO.addStudent(student);

                JOptionPane.showMessageDialog(
                    this,
                    "Student saved successfully!"
                );

                clearFields();

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                    this,
                    "ID and CGPA must be numbers."
                );
            }
        });


        // UPDATE
        updateButton.addActionListener(e -> {

            try {

                int id =
                    Integer.parseInt(idField.getText());

                String name = nameField.getText();
                String branch = branchField.getText();

                double cgpa =
                    Double.parseDouble(cgpaField.getText());

                String skills = skillsField.getText();

                Student student =
                    new Student(
                        id,
                        name,
                        branch,
                        cgpa,
                        skills
                    );

                StudentDAO.updateStudent(student);

                JOptionPane.showMessageDialog(
                    this,
                    "Update operation completed."
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                    this,
                    "ID and CGPA must be numbers."
                );
            }
        });


        // DELETE
        deleteButton.addActionListener(e -> {

            try {

                if (idField.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(
                        this,
                        "Enter Student ID."
                    );

                    return;
                }

                int id =
                    Integer.parseInt(
                        idField.getText()
                    );

                int choice =
                    JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this student?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                    );

                if (choice == JOptionPane.YES_OPTION) {

                    StudentDAO.deleteStudent(id);

                    JOptionPane.showMessageDialog(
                        this,
                        "Delete operation completed."
                    );

                    clearFields();
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                    this,
                    "Student ID must be a number."
                );
            }
        });


        // VIEW
        viewButton.addActionListener(e -> {

            java.util.ArrayList<Student> students =
                StudentDAO.getAllStudents();

            if (students.isEmpty()) {

                JOptionPane.showMessageDialog(
                    this,
                    "No students found."
                );

                return;
            }

            StringBuilder data =
                new StringBuilder();

            for (Student student : students) {

                data.append("ID: ")
                    .append(student.id)
                    .append("\n");

                data.append("Name: ")
                    .append(student.name)
                    .append("\n");

                data.append("Branch: ")
                    .append(student.branch)
                    .append("\n");

                data.append("CGPA: ")
                    .append(student.cgpa)
                    .append("\n");

                data.append("Skills: ")
                    .append(student.skills)
                    .append("\n");

                data.append("--------------------\n");
            }

            JTextArea textArea =
                new JTextArea(data.toString());

            textArea.setEditable(false);

            JScrollPane scrollPane =
                new JScrollPane(textArea);

            scrollPane.setPreferredSize(
                new Dimension(500, 350)
            );

            JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "All Students",
                JOptionPane.INFORMATION_MESSAGE
            );
        });


        // CLEAR
        clearButton.addActionListener(e -> {
            clearFields();
        });


        // BACK
        backButton.addActionListener(e -> {
            dispose();
        });


        panel.add(title);

        panel.add(idLabel);
        panel.add(idField);

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(branchLabel);
        panel.add(branchField);

        panel.add(cgpaLabel);
        panel.add(cgpaField);

        panel.add(skillsLabel);
        panel.add(skillsField);

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(viewButton);
        panel.add(clearButton);
        panel.add(backButton);

        add(panel);

        setVisible(true);
    }


    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        branchField.setText("");
        cgpaField.setText("");
        skillsField.setText("");
    }


    public static void main(String[] args) {

        new StudentFrame();

    }
}