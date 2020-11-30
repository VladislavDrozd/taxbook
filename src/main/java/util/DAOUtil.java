package util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

public class DAOUtil {

    protected void setLong(PreparedStatement ps, int i, Long value) throws SQLException {
        if (value == null) {
            ps.setNull(i, Types.BIGINT);
        } else {
            ps.setLong(i, value);
        }
    }

    protected void setDouble(PreparedStatement ps, int i, Double value) throws SQLException {
        if (value == null) {
            ps.setNull(i, Types.DOUBLE);
        } else {
            ps.setDouble(i, value);
        }
    }

    protected void setInt(PreparedStatement ps, int i, Integer value) throws SQLException {
        if (value == null) {
            ps.setNull(i, Types.INTEGER);
        } else {
            ps.setInt(i, value);
        }
    }

    protected void setString(PreparedStatement ps, int i, String value) throws SQLException {
        if (value == null) {
            ps.setNull(i, Types.VARCHAR);
        } else {
            ps.setString(i, value);
        }
    }

    protected void setTimestamp(PreparedStatement ps, int i, Timestamp value) throws SQLException {
        if (value == null) {
            ps.setNull(i, Types.TIMESTAMP);
        } else {
            ps.setTimestamp(i, value);
        }
    }
}
