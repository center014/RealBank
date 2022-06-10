package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "center014";
        String password = "qkrqudgh193";
        connection = DriverManager.getConnection(url, user, password);

        return connection;
    }

}
