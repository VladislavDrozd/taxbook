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
            log.error("DBPool Cannot get connection from pool", e);
            throw e;
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            log.error("DBPool Cannot close connection", e);
        }
    }

    public static void startTransaction(Connection connection) {
        try {
            if (connection != null) connection.setAutoCommit(false);
        } catch (SQLException e) {
            log.error("DBPool Cannot start transaction", e);
        }
    }

    public static void commitTransaction(Connection connection) {
        try {
            if (connection != null) connection.commit();
        } catch (SQLException e) {
            log.error("DBPool Cannot commit transaction", e);
        }
    }

    public static void rollbackTransaction(Connection connection) {
        try {
            if (connection != null) connection.rollback();
        } catch (SQLException e) {
            log.error("DBPool Cannot rollback transaction", e);
        }
    }

}
