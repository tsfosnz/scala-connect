import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import com.mysql.jdbc.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Test the connection management of different way in Java, and HikariCP is
 * very stable connection pool, and the connection always closed
 */

final class HikariCPMySQL {

    HikariDataSource ds = null;
    private static HikariCPMySQL instance = null;
    public Connection conn = null;
    private PreparedStatement preparedStatement = null;
    Statement stmt = null;
    ResultSet rs = null;


    private HikariCPMySQL() {

        if (ds == null) {

            System.out.println("Initialize connection pool");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://192.168.1.90/connectapp");
            config.setUsername("root");
            config.setPassword("ddddddd");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.setMaximumPoolSize(2);

            ds = new HikariDataSource(config);
        }
    }

    public static HikariCPMySQL getInstance() {

        if (instance == null) {
            instance = new HikariCPMySQL();
        }

        return instance;
    }

    public void example() {

        try {
            conn = ds.getConnection();

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM post");

            if (rs != null) {
                while (rs.next()) {
                    System.out.println(rs.getString("title").substring(0, 10));
                }
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {

            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    // ignore
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    // ignore
                }
            }
        }
    }


}

// assume that conn is an already created JDBC connection (see previous examples)
final class JMySQL {

    public Connection conn = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    Statement stmt = null;
    ResultSet rs = null;

    private static JMySQL instance = null;

    public static JMySQL getInstance() {

        if (instance == null) {
            instance = new JMySQL();
        }

        return instance;
    }

    public void example() {

        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://192.168.1.90/connectapp?" +
                            "user=root&password=ddddddd");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM post");

            if (rs != null) {
                while (rs.next()) {
                    System.out.print(rs.getString("title"));
                }
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    // ignore
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    // ignore
                }
            }
        }
    }
}


class Job implements Runnable {

    JMySQL example = null;

    public void run() {

        //example = JMySQL.getInstance();
        //example.example();

        HikariCPMySQL.getInstance().example();

        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
}

class leak implements Runnable {

    public void run() {

        while (true) {

            // keep, so won't release conn
            //Connection t = JMySQL.getInstance().conn;
        }
    }
}

public class Test {

    public static void main(String args[]) {
        System.out.println("Hello World!");

        //new Thread(new leak()).start();

        for (int i = 0; i < 30; ++i) {

            //JMySQL.getInstance().example();

            //HikariCPMySQL.getInstance().example();

            new Thread(new Job()).start();
        }
    }
}
