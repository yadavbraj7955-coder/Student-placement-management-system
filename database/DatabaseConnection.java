package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    static String url =
        "jdbc:mysql://localhost:3306/placement_db";

    static String username = "root";

    static String password = "12345";

    public static Connection getConnection() {

        try {

            Connection con =
                DriverManager.getConnection(
                    url,
                    username,
                    password
                );

            System.out.println("Database connected.");

            return con;

        } catch (Exception e) {

            System.out.println(
                "Database connection failed."
            );

            System.out.println(
                "Error: " + e.getMessage()
            );

            return null;
        }
    }
}