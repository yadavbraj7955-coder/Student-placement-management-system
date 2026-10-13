package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.Student;

public class StudentDAO {

    // ADD STUDENT
    public static boolean addStudent(Student student) {

        String sql = "INSERT INTO students (id, name, branch, cgpa, skills) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed.");
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, student.id);
                ps.setString(2, student.name);
                ps.setString(3, student.branch);
                ps.setDouble(4, student.cgpa);
                ps.setString(5, student.skills);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Student added successfully.");
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Add student error: " + e.getMessage());
        }

        return false;
    }

    // GET ALL STUDENTS
    public static ArrayList<Student> getAllStudents() {

        ArrayList<Student> students = new ArrayList<>();

        String sql = "SELECT id, name, branch, cgpa, skills FROM students";

        try (Connection con = DatabaseConnection.getConnection()) {

            if (con == null) {
                return students;
            }

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("branch"),
                        rs.getDouble("cgpa"),
                        rs.getString("skills")
                    );

                    students.add(student);
                }
            }

        } catch (Exception e) {
            System.out.println("View students error: " + e.getMessage());
        }

        return students;
    }

    // UPDATE STUDENT
    public static boolean updateStudent(Student student) {

        String sql = "UPDATE students SET name = ?, branch = ?, "
                   + "cgpa = ?, skills = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection()) {

            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, student.name);
                ps.setString(2, student.branch);
                ps.setDouble(3, student.cgpa);
                ps.setString(4, student.skills);
                ps.setInt(5, student.id);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Student updated successfully.");
                    return true;
                } else {
                    System.out.println("Student not found.");
                }
            }

        } catch (Exception e) {
            System.out.println("Update student error: " + e.getMessage());
        }

        return false;
    }

    // DELETE STUDENT
    public static boolean deleteStudent(int id) {

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection()) {

            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Student deleted successfully.");
                    return true;
                } else {
                    System.out.println("Student not found.");
                }
            }

        } catch (Exception e) {
            System.out.println("Delete student error: " + e.getMessage());
        }

        return false;
    }

    // COUNT TOTAL STUDENTS
    public static int countAll() {

        String sql = "SELECT COUNT(*) FROM students";

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
            System.out.println("Student count error: " + e.getMessage());
        }

        return 0;
    }
}