package gui;

import javax.swing.*;
import java.awt.*;

import dao.CompanyDAO;
import model.Company;

public class CompanyFrame extends JFrame {

    JTextField idField;
    JTextField nameField;
    JTextField cgpaField;
    JTextField skillsField;

    public CompanyFrame() {

        setTitle("Company Management");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Company Management");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(230, 20, 350, 40);

        JLabel idLabel = new JLabel("Company ID:");
        idLabel.setBounds(70, 90, 120, 30);

        idField = new JTextField();
        idField.setBounds(200, 90, 300, 30);

        JLabel nameLabel = new JLabel("Company Name:");
        nameLabel.setBounds(70, 135, 120, 30);

        nameField = new JTextField();
        nameField.setBounds(200, 135, 300, 30);

        JLabel cgpaLabel = new JLabel("Required CGPA:");
        cgpaLabel.setBounds(70, 180, 120, 30);

        cgpaField = new JTextField();
        cgpaField.setBounds(200, 180, 300, 30);

        JLabel skillsLabel = new JLabel("Required Skills:");
        skillsLabel.setBounds(70, 225, 120, 30);

        skillsField = new JTextField();
        skillsField.setBounds(200, 225, 300, 30);

        JButton addButton = new JButton("Add");
        addButton.setBounds(40, 300, 100, 35);

        JButton viewButton = new JButton("View");
        viewButton.setBounds(150, 300, 100, 35);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(260, 300, 100, 35);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(370, 300, 100, 35);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(480, 300, 100, 35);

        JButton backButton = new JButton("Back");
        backButton.setBounds(590, 300, 100, 35);

        // ADD COMPANY
        addButton.addActionListener(e -> {

            try {

                if (idField.getText().trim().isEmpty()
                        || nameField.getText().trim().isEmpty()
                        || cgpaField.getText().trim().isEmpty()
                        || skillsField.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                        this, "Please fill all fields."
                    );
                    return;
                }

                int id = Integer.parseInt(idField.getText().trim());

                String name = nameField.getText().trim();

                double cgpa = Double.parseDouble(cgpaField.getText().trim());

                String skills = skillsField.getText().trim();

                if (cgpa < 0 || cgpa > 10) {
                    JOptionPane.showMessageDialog(
                        this, "CGPA must be between 0 and 10."
                    );
                    return;
                }

                Company company =
                    new Company(id, name, cgpa, skills);

                CompanyDAO.addCompany(company);

                JOptionPane.showMessageDialog(
                    this,
                    "Add operation completed. Check database for confirmation."
                );

                clearFields();

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                    this, "Company ID and CGPA must be numbers."
                );
            }
        });

        // VIEW COMPANIES
        viewButton.addActionListener(e -> {

            java.util.ArrayList<Company> companies =
                CompanyDAO.getAllCompanies();

            if (companies.isEmpty()) {

                JOptionPane.showMessageDialog(
                    this, "No companies found."
                );
                return;
            }

            StringBuilder data = new StringBuilder();

            for (Company company : companies) {

                data.append("Company ID: ")
                    .append(company.companyId).append("\n");

                data.append("Company Name: ")
                    .append(company.companyName).append("\n");

                data.append("Required CGPA: ")
                    .append(company.requiredCgpa).append("\n");

                data.append("Required Skills: ")
                    .append(company.requiredSkills).append("\n");

                data.append("--------------------------\n");
            }

            JTextArea textArea = new JTextArea(data.toString());
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 350));

            JOptionPane.showMessageDialog(
                this, scrollPane, "All Companies",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        // UPDATE COMPANY
        updateButton.addActionListener(e -> {

            try {

                if (idField.getText().trim().isEmpty()
                        || nameField.getText().trim().isEmpty()
                        || cgpaField.getText().trim().isEmpty()
                        || skillsField.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                        this, "Fill all fields to update."
                    );
                    return;
                }

                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                double cgpa = Double.parseDouble(cgpaField.getText().trim());
                String skills = skillsField.getText().trim();

                if (cgpa < 0 || cgpa > 10) {
                    JOptionPane.showMessageDialog(
                        this, "CGPA must be between 0 and 10."
                    );
                    return;
                }

                Company company =
                    new Company(id, name, cgpa, skills);

                CompanyDAO.updateCompany(company);

                JOptionPane.showMessageDialog(
                    this,
                    "Update operation completed. Check database for confirmation."
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                    this, "Company ID and CGPA must be numbers."
                );
            }
        });

        // DELETE COMPANY
        deleteButton.addActionListener(e -> {

            try {

                if (idField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                        this, "Enter Company ID."
                    );
                    return;
                }

                int id = Integer.parseInt(idField.getText().trim());

                int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this company?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {

                    CompanyDAO.deleteCompany(id);

                    JOptionPane.showMessageDialog(
                        this,
                        "Delete operation completed. Check database for confirmation."
                    );

                    clearFields();
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                    this, "Company ID must be a number."
                );
            }
        });

        // CLEAR FIELDS
        clearButton.addActionListener(e -> clearFields());

        // BACK
        backButton.addActionListener(e -> dispose());

        panel.add(title);

        panel.add(idLabel);
        panel.add(idField);

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(cgpaLabel);
        panel.add(cgpaField);

        panel.add(skillsLabel);
        panel.add(skillsField);

        panel.add(addButton);
        panel.add(viewButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(clearButton);
        panel.add(backButton);

        add(panel);

        setVisible(true);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        cgpaField.setText("");
        skillsField.setText("");
    }

    public static void main(String[] args) {
        new CompanyFrame();
    }
}