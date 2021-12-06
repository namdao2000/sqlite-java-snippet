package sqlite.tutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    public static void run() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("DROP TABLE IF EXISTS person");
            statement.executeUpdate("""
CREATE TABLE person (
id integer,
name string,
phone string
)
""");
            statement.executeUpdate("INSERT INTO person VALUES(1, 'leo', '0403909091')");
            statement.executeUpdate("INSERT INTO person VALUES(2, 'nam', '0403909090')");
            ResultSet rs = statement.executeQuery("SELECT * FROM person");
            while (rs.next()) {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}
