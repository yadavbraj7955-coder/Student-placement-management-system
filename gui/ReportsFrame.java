package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

import dao.StudentDAO;
import dao.CompanyDAO;
import dao.ApplicationDAO;

import model.Student;
import model.Company;
import model.Application;

public class ReportsFrame extends JFrame {

    JTextArea reportArea;

    public ReportsFrame() {

        setTitle("Placement Reports");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel(
            "Placement Reports",
            SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));

        reportArea = new JTextArea();
        reportArea.setFont(
            new Font("Monospaced", Font.PLAIN, 15)
        );
        reportArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(reportArea);

        JButton generateButton =
            new JButton("Generate Report");

        JButton backButton =
            new JButton("Back");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(generateButton);
        buttonPanel.add(backButton);

        generateButton.addActionListener(
            e -> generateReport()
        );

        backButton.addActionListener(
            e -> dispose()
        );

        panel.setBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        generateReport();

        setVisible(true);
    }

    private void generateReport() {

        ArrayList<Student> students =
            StudentDAO.getAllStudents();

        ArrayList<Company> companies =
            CompanyDAO.getAllCompanies();

        ArrayList<Application> applications =
            ApplicationDAO.getAllApplications();

        int applied = 0;
        int underReview = 0;
        int shortlisted = 0;
        int selected = 0;
        int placed = 0;
        int rejected = 0;

        // Count application statuses
        for (Application app : applications) {

            if ("Applied".equalsIgnoreCase(app.status)) {
                applied++;
            }
            else if ("Under Review".equalsIgnoreCase(app.status)) {
                underReview++;
            }
            else if ("Shortlisted".equalsIgnoreCase(app.status)) {
                shortlisted++;
            }
            else if ("Selected".equalsIgnoreCase(app.status)) {
                selected++;
            }
            else if ("Placed".equalsIgnoreCase(app.status)) {
                placed++;
            }
            else if ("Rejected".equalsIgnoreCase(app.status)) {
                rejected++;
            }
        }

        // Count unique students with Placed status
        HashSet<Integer> placedStudentIds = new HashSet<>();

        for (Application app : applications) {

            if ("Placed".equalsIgnoreCase(app.status)) {
                placedStudentIds.add(app.studentId);
            }
        }

        StringBuilder report = new StringBuilder();

        report.append("====================================\n");
        report.append("          PLACEMENT REPORT\n");
        report.append("====================================\n\n");

        report.append("Total Students       : ")
              .append(students.size())
              .append("\n\n");

        report.append("Total Companies      : ")
              .append(companies.size())
              .append("\n\n");

        report.append("Total Applications    : ")
              .append(applications.size())
              .append("\n\n");

        report.append("Placed Students       : ")
              .append(placedStudentIds.size())
              .append("\n\n");

        report.append("------------------------------------\n");
        report.append("       APPLICATION STATUS\n");
        report.append("------------------------------------\n");

        report.append("Applied              : ")
              .append(applied).append("\n");

        report.append("Under Review         : ")
              .append(underReview).append("\n");

        report.append("Shortlisted          : ")
              .append(shortlisted).append("\n");

        report.append("Selected             : ")
              .append(selected).append("\n");

        report.append("Placed               : ")
              .append(placed).append("\n");

        report.append("Rejected             : ")
              .append(rejected).append("\n");

        report.append("====================================\n");

        reportArea.setText(report.toString());
    }

    public static void main(String[] args) {

        new ReportsFrame();
    }
}