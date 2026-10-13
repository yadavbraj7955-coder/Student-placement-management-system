package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.Company;

public class CompanyDAO {

    // Add Company
    public static void addCompany(Company company) {

        String sql = "INSERT INTO companies "
                + "(company_id, company_name, required_cgpa, required_skills) "
                + "VALUES (?, ?, ?, ?)";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, company.companyId);
            ps.setString(2, company.companyName);
            ps.setDouble(3, company.requiredCgpa);
            ps.setString(4, company.requiredSkills);

            ps.executeUpdate();

            System.out.println("Company saved in database.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Get All Companies
    public static ArrayList<Company> getAllCompanies() {

        ArrayList<Company> companies = new ArrayList<>();

        String sql = "SELECT * FROM companies";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                int id = rs.getInt("company_id");
                String name = rs.getString("company_name");
                double cgpa = rs.getDouble("required_cgpa");
                String skills = rs.getString("required_skills");

                Company company =
                        new Company(id, name, cgpa, skills);

                companies.add(company);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return companies;
    }

    // Update Company
    public static void updateCompany(Company company) {

        String sql = "UPDATE companies SET "
                + "company_name=?, required_cgpa=?, required_skills=? "
                + "WHERE company_id=?";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, company.companyName);
            ps.setDouble(2, company.requiredCgpa);
            ps.setString(3, company.requiredSkills);
            ps.setInt(4, company.companyId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Company updated successfully.");
            } else {
                System.out.println("Company not found.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Delete Company
    public static void deleteCompany(int companyId) {

        String sql =
                "DELETE FROM companies WHERE company_id=?";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, companyId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Company deleted successfully.");
            } else {
                System.out.println("Company not found.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Count All Companies
    public static int countAll() {

        String sql = "SELECT COUNT(*) FROM companies";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println(
                    "Company Count Error: " + e.getMessage()
            );
        }

        return 0;
    }
}