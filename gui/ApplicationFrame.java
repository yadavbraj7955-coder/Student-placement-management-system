package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import dao.StudentDAO;
import dao.CompanyDAO;
import dao.ApplicationDAO;

import model.Student;
import model.Company;
import model.Application;

public class ApplicationFrame extends JFrame {

    JComboBox<String> studentBox;
    JComboBox<String> companyBox;
    JComboBox<String> statusBox;

    ArrayList<Student> students;
    ArrayList<Company> companies;

    JTextArea resultArea;

    public ApplicationFrame() {

        setTitle("Application Management");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);

        JLabel title = new JLabel("Application Management");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(180, 20, 400, 35);

        JLabel studentLabel = new JLabel("Select Student:");
        studentLabel.setBounds(60, 80, 120, 30);

        studentBox = new JComboBox<>();
        studentBox.setBounds(200, 80, 400, 30);

        JLabel companyLabel = new JLabel("Select Company:");
        companyLabel.setBounds(60, 125, 120, 30);

        companyBox = new JComboBox<>();
        companyBox.setBounds(200, 125, 400, 30);

        JButton applyButton = new JButton("Apply");
        applyButton.setBounds(100, 175, 120, 35);

        JButton viewButton = new JButton("View Applications");
        viewButton.setBounds(240, 175, 170, 35);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(430, 175, 120, 35);

        JLabel statusLabel = new JLabel("New Status:");
        statusLabel.setBounds(60, 235, 120, 30);

        statusBox = new JComboBox<>(
    new String[] {
        "Applied",
        "Under Review",
        "Shortlisted",
        "Selected",
        "Placed",
        "Rejected"
    }
);

        statusBox.setBounds(200, 235, 200, 30);

        JButton updateStatusButton =
            new JButton("Update Status");

        updateStatusButton.setBounds(420, 235, 150, 35);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane =
            new JScrollPane(resultArea);

        scrollPane.setBounds(60, 290, 610, 150);

        JButton backButton = new JButton("Back");
        backButton.setBounds(300, 455, 120, 35);

        applyButton.addActionListener(
            e -> applyForCompany()
        );

        viewButton.addActionListener(
            e -> viewApplications()
        );

        refreshButton.addActionListener(
            e -> loadData()
        );

        updateStatusButton.addActionListener(
            e -> updateApplicationStatus()
        );

        backButton.addActionListener(
            e -> dispose()
        );

        panel.add(title);

        panel.add(studentLabel);
        panel.add(studentBox);

        panel.add(companyLabel);
        panel.add(companyBox);

        panel.add(applyButton);
        panel.add(viewButton);
        panel.add(refreshButton);

        panel.add(statusLabel);
        panel.add(statusBox);
        panel.add(updateStatusButton);

        panel.add(scrollPane);
        panel.add(backButton);

        add(panel);

        loadData();

        setVisible(true);
    }

    // LOAD STUDENTS AND COMPANIES
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
                company.companyId + " - " +
                company.companyName
            );
        }
    }

    // APPLY FOR COMPANY
    private void applyForCompany() {

        int studentIndex =
            studentBox.getSelectedIndex();

        int companyIndex =
            companyBox.getSelectedIndex();

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

        if (!cgpaEligible || !skillsEligible) {

            JOptionPane.showMessageDialog(
                this,
                "Student is not eligible for this company."
            );

            return;
        }

        Application application =
            new Application(
                student.id,
                company.companyId
            );

        ApplicationDAO.addApplication(application);

        JOptionPane.showMessageDialog(
            this,
            "Application submitted. Check the application list to confirm."
        );

        viewApplications();
    }

    // CHECK REQUIRED SKILLS
    private boolean hasRequiredSkills(
            String studentSkills,
            String requiredSkills) {

        if (studentSkills == null ||
                requiredSkills == null) {

            return false;
        }

        String[] required =
            requiredSkills.split(",");

        for (String skill : required) {

            if (!studentSkills.toLowerCase()
                    .contains(
                        skill.trim().toLowerCase()
                    )) {

                return false;
            }
        }

        return true;
    }

    // VIEW ALL APPLICATIONS
    private void viewApplications() {

        ArrayList<Application> applications =
            ApplicationDAO.getAllApplications();

        if (applications.isEmpty()) {

            resultArea.setText("No applications found.");
            return;
        }

        StringBuilder data = new StringBuilder();

        for (Application application : applications) {

            String studentName = "Unknown";
            String companyName = "Unknown";

            for (Student student : students) {

                if (student.id == application.studentId) {
                    studentName = student.name;
                    break;
                }
            }

            for (Company company : companies) {

                if (company.companyId == application.companyId) {
                    companyName = company.companyName;
                    break;
                }
            }

            data.append("Student: ")
                .append(studentName)
                .append(" (ID: ")
                .append(application.studentId)
                .append(")\n");

            data.append("Company: ")
                .append(companyName)
                .append(" (ID: ")
                .append(application.companyId)
                .append(")\n");

            data.append("Status: ")
                .append(application.status)
                .append("\n");

            data.append("--------------------------\n");
        }

        resultArea.setText(data.toString());
    }

    // UPDATE APPLICATION STATUS
    private void updateApplicationStatus() {

        int studentIndex =
            studentBox.getSelectedIndex();

        int companyIndex =
            companyBox.getSelectedIndex();

        if (studentIndex < 0 || companyIndex < 0) {

            JOptionPane.showMessageDialog(
                this,
                "Select a student and company first."
            );

            return;
        }

        Student student = students.get(studentIndex);

        Company company = companies.get(companyIndex);

        String status =
            (String) statusBox.getSelectedItem();

        ApplicationDAO.updateStatus(
            student.id,
            company.companyId,
            status
        );

        viewApplications();
    }

    public static void main(String[] args) {

        new ApplicationFrame();
    }
}