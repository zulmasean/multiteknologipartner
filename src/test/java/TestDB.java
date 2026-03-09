import java.sql.Connection;
import java.sql.DriverManager;

public class TestDB {

    public static void main(String[] args) throws Exception {

        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "admin"
        );

        System.out.println("Connected!");
    }
}