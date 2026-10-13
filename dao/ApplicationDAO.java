package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.Application;

public class ApplicationDAO {

    // 1. Add Application with Duplicate Check
    public static boolean addApplication(Application application) {

        String checkSql =
                "SELECT 1 FROM applications WHERE student_id = ? AND company_id = ?";

        String insertSql =
                "INSERT INTO applications (student_id, company_id, status) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed.");
                return false;
            }

            // Check whether the application already exists
            try (PreparedStatement checkPs = con.prepareStatement(checkSql)) {

                checkPs.setInt(1, application.studentId);
                checkPs.setInt(2, application.companyId);

                try (ResultSet rs = checkPs.executeQuery()) {

                    if (rs.next()) {
                        System.out.println(
                                "Student has already applied to this company."
                        );
                        return false;
                    }
                }
            }

            // Insert new application
            try (PreparedStatement insertPs = con.prepareStatement(insertSql)) {

                insertPs.setInt(1, application.studentId);
                insertPs.setInt(2, application.companyId);
                insertPs.setString(3, application.status);

                int rows = insertPs.executeUpdate();

                if (rows > 0) {
                    System.out.println("Application saved successfully.");
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Application error: " + e.getMessage());
        }

        return false;
    }

    // 2. Get All Applications
    public static ArrayList<Application> getAllApplications() {

        ArrayList<Application> applications = new ArrayList<>();

        String sql = "SELECT student_id, company_id, status FROM applications";

        try (Connection con = DatabaseConnection.getConnection()) {

            if (con == null) {
                return applications;
            }

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    int studentId = rs.getInt("student_id");
                    int companyId = rs.getInt("company_id");
                    String status = rs.getString("status");

                    Application application =
                            new Application(studentId, companyId);

                    application.status = status;

                    applications.add(application);
                }
            }

        } catch (Exception e) {
            System.out.println("Application view error: " + e.getMessage());
        }

        return applications;
    }

    // 3. Update Application Status
    public static boolean updateStatus(
            int studentId, int companyId, String status) {

        String sql =
                "UPDATE applications SET status = ? " +
                "WHERE student_id = ? AND company_id = ?";

        try (Connection con = DatabaseConnection.getConnection()) {

            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, status);
                ps.setInt(2, studentId);
                ps.setInt(3, companyId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Application status updated.");
                    return true;
                } else {
                    System.out.println("Application not found.");
                }
            }

        } catch (Exception e) {
            System.out.println("Status update error: " + e.getMessage());
        }

        return false;
    }

    // 4. Count All Applications
    public static int countAll() {

        String sql = "SELECT COUNT(*) FROM applications";

        try (Connection con = DatabaseConnection.getConnection()) {

            if (con == null) {
                return 0;
            }

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            System.out.println("Application count error: " + e.getMessage());
        }

        return 0;
    }

    // 5. Count Applications by Status
    public static int countStatus(String status) {

        String sql = "SELECT COUNT(*) FROM applications WHERE status = ?";

        try (Connection con = DatabaseConnection.getConnection()) {

            if (con == null) {
                return 0;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, status);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Status count error: " + e.getMessage());
        }

        return 0;
    }
}