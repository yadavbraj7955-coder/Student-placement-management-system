package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private final Color navy = new Color(15, 32, 75);
    private final Color blue = new Color(30, 100, 255);
    private final Color background = new Color(241, 246, 255);

    public DashboardFrame() {

        setTitle("Student Placement Management System");
        setSize(1250, 800);
        setMinimumSize(new Dimension(1000, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(background);

        // SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(navy);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(22, 15, 20, 15));

        JLabel logo = new JLabel("  🎓 SPMS");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel logoSub = new JLabel("  Placement Management");
        logoSub.setForeground(new Color(190, 210, 255));
        logoSub.setFont(new Font("Arial", Font.PLAIN, 12));

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(logoSub);
        sidebar.add(Box.createVerticalStrut(35));

        JLabel admin = new JLabel("  👤  Admin  • Online");
        admin.setForeground(new Color(100, 255, 180));
        admin.setFont(new Font("Arial", Font.BOLD, 15));
        sidebar.add(admin);

        sidebar.add(Box.createVerticalStrut(35));

        addMenuButton(sidebar, "🏠  Dashboard", null);
        addMenuButton(sidebar, "👨‍🎓  Manage Students",
                () -> new StudentFrame());

        addMenuButton(sidebar, "🏢  Manage Companies",
                () -> new CompanyFrame());

        addMenuButton(sidebar, "🔍  Check Eligibility",
                () -> new EligibilityFrame());

        addMenuButton(sidebar, "📄  Applications",
                () -> new ApplicationFrame());

        addMenuButton(sidebar, "📊  Placement Reports",
                () -> new ReportsFrame());

        sidebar.add(Box.createVerticalStrut(20));

        addMenuButton(sidebar, "🚪  Logout", () -> {
            dispose();
            new LoginFrame();
        });

        sidebar.add(Box.createVerticalGlue());

        JLabel sidebarFooter = new JLabel(
                "<html><center>Better Skills<br>" +
                "Better Opportunities<br>" +
                "Brighter Future</center></html>",
                SwingConstants.CENTER);

        sidebarFooter.setForeground(Color.WHITE);
        sidebarFooter.setFont(new Font("Arial", Font.BOLD, 14));
        sidebarFooter.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(sidebarFooter);

        // MAIN CONTENT
        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBackground(background);
        content.setBorder(new EmptyBorder(20, 22, 15, 22));

        // COLORFUL HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(blue);
        header.setBorder(new EmptyBorder(20, 22, 20, 22));

        JPanel headerText = new JPanel();
        headerText.setOpaque(false);
        headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel(
                "Student Placement Management System");

        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Arial", Font.BOLD, 23));

        JLabel subheading = new JLabel(
                "Connect  •  Learn  •  Get Placed");

        subheading.setForeground(new Color(225, 235, 255));
        subheading.setFont(new Font("Arial", Font.PLAIN, 14));

        headerText.add(heading);
        headerText.add(Box.createVerticalStrut(7));
        headerText.add(subheading);

        JLabel adminLabel = new JLabel("👤  Admin  ");
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setFont(new Font("Arial", Font.BOLD, 16));

        header.add(headerText, BorderLayout.WEST);
        header.add(adminLabel, BorderLayout.EAST);

        // DASHBOARD BODY
        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("👋  Welcome, Admin!");
        welcome.setFont(new Font("Arial", Font.BOLD, 24));
        welcome.setForeground(navy);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = new JLabel(
                "Here's an overview of your placement management system.");

        description.setFont(new Font("Arial", Font.PLAIN, 14));
        description.setForeground(new Color(65, 85, 125));
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        body.add(welcome);
        body.add(Box.createVerticalStrut(6));
        body.add(description);
        body.add(Box.createVerticalStrut(20));

        // STATISTICS CARDS
        JPanel stats = new JPanel(new GridLayout(1, 4, 12, 12));
        stats.setOpaque(false);
        stats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        stats.setAlignmentX(Component.LEFT_ALIGNMENT);

        stats.add(createStatCard(
    "👨‍🎓",
    "Total Students",
    String.valueOf(dao.StudentDAO.countAll()),
    "Students registered",
    new Color(48, 93, 255)
));
       stats.add(createStatCard(
        "🏢",
        "Total Companies",
        String.valueOf(dao.CompanyDAO.countAll()),
        "Companies registered",
        new Color(0, 180, 105)
));
       stats.add(createStatCard(
        "📋",
        "Total Applications",
        String.valueOf(dao.ApplicationDAO.countAll()),
        "Applications submitted",
        new Color(255, 152, 0)
));
        stats.add(createStatCard(
        "🎯",
        "Placed Students",
        String.valueOf(dao.ApplicationDAO.countStatus("Placed")),
        "Students placed successfully",
        new Color(142, 68, 173)
));

        body.add(stats);
        body.add(Box.createVerticalStrut(20));

        // MIDDLE PANELS
        JPanel middle = new JPanel(new GridLayout(1, 2, 15, 0));
        middle.setOpaque(false);
        middle.setMaximumSize(new Dimension(Integer.MAX_VALUE, 205));
        middle.setAlignmentX(Component.LEFT_ALIGNMENT);

        middle.add(createInfoPanel(
    "📊  Placement Overview",
    "Total Students             " + dao.StudentDAO.countAll() + "\n\n" +
    "Total Companies             " + dao.CompanyDAO.countAll() + "\n\n" +
    "Total Applications          " + dao.ApplicationDAO.countAll() + "\n\n" +
    "Placed Students              " + dao.ApplicationDAO.countStatus("Placed")
));

        middle.add(createInfoPanel(
                "⚡  Quick Actions",
                "Select a feature to continue"));

        body.add(middle);
        body.add(Box.createVerticalStrut(18));

        // QUICK ACTION BUTTONS
        JPanel actions = new JPanel(new GridLayout(1, 5, 10, 0));
        actions.setOpaque(false);
        actions.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        actions.setAlignmentX(Component.LEFT_ALIGNMENT);

        actions.add(createActionButton("➕ Student",
                new Color(35, 115, 255),
                () -> new StudentFrame()));

        actions.add(createActionButton("🏢 Company",
                new Color(0, 180, 105),
                () -> new CompanyFrame()));

        actions.add(createActionButton("🔍 Eligibility",
                new Color(140, 60, 235),
                () -> new EligibilityFrame()));

        actions.add(createActionButton("📄 Applications",
                new Color(255, 145, 0),
                () -> new ApplicationFrame()));

        actions.add(createActionButton("📊 Reports",
                new Color(220, 45, 130),
                () -> new ReportsFrame()));

        body.add(actions);

        JScrollPane scrollPane = new JScrollPane(body);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        content.add(header, BorderLayout.NORTH);
        content.add(scrollPane, BorderLayout.CENTER);

        JLabel footer = new JLabel(
                "© 2026 Student Placement Management System | Java + MySQL",
                SwingConstants.CENTER);

        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        footer.setForeground(new Color(90, 110, 150));
        content.add(footer, BorderLayout.SOUTH);

        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);

        setContentPane(root);
        setVisible(true);
    }

    private void addMenuButton(JPanel sidebar,
                               String text,
                               Runnable action) {

        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(navy);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (action != null) {
            button.addActionListener(e -> action.run());
        }

        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(6));
    }

    private JPanel createStatCard(String icon,
                                  String title,
                                  String value,
                                  String subtitle,
                                  Color color) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(color);
        card.setBorder(new EmptyBorder(13, 13, 13, 10));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 23));
        iconLabel.setForeground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 29));
        valueLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        subtitleLabel.setForeground(Color.WHITE);

        card.add(iconLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(valueLabel);
        card.add(subtitleLabel);

        return card;
    }

    private JPanel createInfoPanel(String title, String text) {

        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 230, 245)),
                new EmptyBorder(15, 15, 15, 15)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 17));
        titleLabel.setForeground(navy);

        JTextArea textArea = new JTextArea(text);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setForeground(new Color(55, 75, 115));
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setFocusable(false);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(textArea, BorderLayout.CENTER);

        return panel;
    }

    private JButton createActionButton(String text,
                                       Color color,
                                       Runnable action) {

        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> action.run());

        return button;
    }
}
       