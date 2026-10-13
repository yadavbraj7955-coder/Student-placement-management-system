package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import dao.StudentDAO;
import dao.CompanyDAO;
import model.Student;
import model.Company;

public class EligibilityFrame extends JFrame {

    JComboBox<String> studentBox;
    JComboBox<String> companyBox;
    JTextArea resultArea;

    ArrayList<Student> students;
    ArrayList<Company> companies;

    public EligibilityFrame() {

        setTitle("Eligibility Checking");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);

        JLabel title = new JLabel("Student Eligibility Checking");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(170, 20, 400, 35);

        JLabel studentLabel = new JLabel("Select Student:");
        studentLabel.setBounds(60, 90, 120, 30);

        studentBox = new JComboBox<>();
        studentBox.setBounds(190, 90, 400, 30);

        JLabel companyLabel = new JLabel("Select Company:");
        companyLabel.setBounds(60, 140, 120, 30);

        companyBox = new JComboBox<>();
        companyBox.setBounds(190, 140, 400, 30);

        JButton checkButton = new JButton("Check Eligibility");
        checkButton.setBounds(190, 190, 180, 35);

        JButton refreshButton = new JButton("Refresh Data");
        refreshButton.setBounds(390, 190, 150, 35);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(60, 250, 560, 130);

        JButton backButton = new JButton("Back");
        backButton.setBounds(280, 400, 120, 35);

        checkButton.addActionListener(e -> checkEligibility());

        refreshButton.addActionListener(e -> loadData());

        backButton.addActionListener(e -> dispose());

        panel.add(title);
        panel.add(studentLabel);
        panel.add(studentBox);
        panel.add(companyLabel);
        panel.add(companyBox);
        panel.add(checkButton);
        panel.add(refreshButton);
        panel.add(scrollPane);
        panel.add(backButton);

        add(panel);

        loadData();

        setVisible(true);
    }

    private void loadData() {

        studentBox.removeAllItems();
        companyBox.removeAllItems();

        students = StudentDAO.getAllStudents();
        companies = CompanyDAO.getAllCompanies();

        for (Student student : students) {

            studentBox.addItem(
                student.id + " - " + student.name
            );
        }

        for (Company company : companies) {

            companyBox.addItem(
                company.companyId + " - " + company.companyName
            );
        }

        resultArea.setText(
            "Students loaded: " + students.size() +
            "\nCompanies loaded: " + companies.size() +
            "\nSelect a student and company to check eligibility."
        );
    }

    private void checkEligibility() {

        int studentIndex = studentBox.getSelectedIndex();
        int companyIndex = companyBox.getSelectedIndex();

        if (studentIndex < 0 || companyIndex < 0) {

            JOptionPane.showMessageDialog(
                this,
                "Please add students and companies first."
            );

            return;
        }

        Student student = students.get(studentIndex);
        Company company = companies.get(companyIndex);

        boolean cgpaEligible =
            student.cgpa >= company.requiredCgpa;

        boolean skillsEligible =
            hasRequiredSkills(
                student.skills,
                company.requiredSkills
            );

        StringBuilder result = new StringBuilder();

        result.append("Student: ")
              .append(student.name).append("\n");

        result.append("Student CGPA: ")
              .append(student.cgpa).append("\n");

        result.append("Company: ")
              .append(company.companyName).append("\n");

        result.append("Required CGPA: ")
              .append(company.requiredCgpa).append("\n\n");

        result.append("CGPA Check: ")
              .append(cgpaEligible ? "PASS" : "FAIL")
              .append("\n");

        result.append("Skills Check: ")
              .append(skillsEligible ? "PASS" : "FAIL")
              .append("\n\n");

        if (cgpaEligible && skillsEligible) {

            result.append("RESULT: ELIGIBLE");

        } else {

            result.append("RESULT: NOT ELIGIBLE");
        }

        resultArea.setText(result.toString());
    }

    private boolean hasRequiredSkills(
            String studentSkills,
            String requiredSkills) {

        if (studentSkills == null || requiredSkills == null) {
            return false;
        }

        String[] required =
            requiredSkills.split(",");

        for (String skill : required) {

            if (!studentSkills.toLowerCase()
                    .contains(skill.trim().toLowerCase())) {

                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        new EligibilityFrame();
    }
}