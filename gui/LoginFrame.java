package gui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Student Placement Management System");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Student Placement Management");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(60, 30, 350, 35);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(70, 100, 100, 25);

        usernameField = new JTextField();
        usernameField.setBounds(170, 100, 200, 30);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(70, 150, 100, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(170, 150, 200, 30);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 210, 120, 35);

        loginButton.addActionListener(e -> login());

        panel.add(title);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);

        setVisible(true);
    }

    void login() {

        String username = usernameField.getText();

        String password =
            new String(passwordField.getPassword());

        if (username.equals("admin") && password.equals("1234")) {

            JOptionPane.showMessageDialog(
                this,
                "Login Successful!"
            );

            new DashboardFrame();

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Invalid Username or Password"
            );
        }
    }

    public static void main(String[] args) {

        new LoginFrame();
    }
}