package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBHelper {

    public static boolean orderExists(String id) {

        try {

            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "admin");

            Statement stmt = conn.createStatement();

            ResultSet rs =
                    stmt.executeQuery(
                            "SELECT * FROM orders WHERE id='" + id + "'");

            return rs.next();

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}