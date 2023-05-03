

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnector {
    private final String url;
    private final String user;
    private final String password;

    public DatabaseConnector(Properties prop) {
        url = prop.getProperty("database.url");
        user = prop.getProperty("database.username");
        password = prop.getProperty("database.password");
    }

    public Connection getConnection() throws SQLException {
    	System.out.println(this.url);
        return DriverManager.getConnection(url, user, password);
    }
}