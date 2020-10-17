package dao;

import org.apache.log4j.Logger;
import vo.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO {
    private static final Logger log = Logger.getLogger(UserDAO.class);

    private Connection connection;
    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public Long addUser(UserVO vo) throws SQLException {
        String sql = "INSERT INTO acl_user (name, email, phone, tax_group, password, is_active) " +
                     "VALUES (?, ?, ?, ?::tax_group_enum, ?, ?) RETURNING user_id;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, vo.getName());
            ps.setString(2, vo.getEmail());
            ps.setString(3, vo.getPhone());
            ps.setString(4, vo.getTaxGroup());
            ps.setString(5, vo.getPassword());
            ps.setString(6, vo.getIsActive().toString());
            ResultSet rs = ps.executeQuery();
            Long userId = null;
            while (rs.next()) {
                userId = rs.getLong("user_id");
            }
            return userId;
        } catch (SQLException e) {
            log.error("DAO Cannot add new user to DB. addUser()", e);
            throw e;
        }
    }


}
