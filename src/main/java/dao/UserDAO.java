package dao;

import def.DBPool;
import org.apache.log4j.Logger;
import util.ServletUtil;
import vo.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static final Logger log = Logger.getLogger(UserDAO.class);

    public Long addUser(UserVO vo) throws SQLException {
        String sql = "INSERT INTO acl_user (name, email, phone, tax_group, password, salt, is_active) " +
                     "VALUES (?, ?, ?, ?::tax_group_enum, ?, ?, ?) RETURNING user_id;";
        try (PreparedStatement ps = DBPool.getConnection().prepareStatement(sql)) {
            ps.setString(1, vo.getName());
            ps.setString(2, vo.getEmail());
            ps.setString(3, vo.getPhone());
            ps.setString(4, vo.getTaxGroup());
            ps.setString(5, vo.getPassword());
            ps.setString(6, vo.getSalt());
            ps.setString(7, vo.getIsActive().toString());
            ResultSet rs = ps.executeQuery();
            Long userId = null;
            while (rs.next()) {
                userId = rs.getLong("user_id");
            }
            return userId;
        } catch (SQLException e) {
            log.error("Cannot add user " + vo.getEmail() + " to Data Base: ", e);
            throw e;
        }
    }

}
