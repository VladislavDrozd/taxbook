package def;

import org.apache.log4j.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBPool {
    private static final Logger log = Logger.getLogger(DBPool.class);
    private static DataSource pool;

    public static void initializeConnectionPool() throws NamingException {
            InitialContext initContext = new InitialContext();
            pool = (DataSource) initContext.lookup("java:comp/env/jdbc/taxbook");
    }

    public static Connection getConnection() throws SQLException {
        try {
            return pool.getConnection();
        } catch (SQLException e) {
            log.error("Cannot get connection from pool", e);
            throw e;
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error("Cannot close connection", e);
        }
    }

}
