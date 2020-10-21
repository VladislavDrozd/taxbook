package dao;

import org.apache.log4j.Logger;
import vo.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, String> getUserIdAndHashPasswordForLogin(String loginName) throws SQLException {
        Map<String, String> map = new HashMap<>();
        String sql = "SELECT user_id, password FROM acl_user " +
                "WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, loginName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put("userId", rs.getString("user_id"));
                map.put("hashPassword", rs.getString("password"));
            }
            return map;
        } catch (SQLException e) {
            log.error("DAO Cannot get password and userId for login user from DB. getUserIdAndHashPasswordForLogin()", e);
            throw e;
        }
    }


    public int updateUser(Long userId, UserVO vo) throws SQLException {
        String sql = "UPDATE acl_user SET name = ?, email = ?, phone = ?, tax_group = ? " +
                "WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, vo.getName());
            ps.setString(2, vo.getEmail());
            ps.setString(3, vo.getPhone());
            ps.setString(4, vo.getTaxGroup());
            ps.setLong(5, userId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            log.error("DAO Cannot update user to DB. updateUser()", e);
            throw e;
        }
    }

    public int deleteUser(Long userId) throws SQLException {
        String sql = "DELETE FROM acl_user WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            log.error("DAO Cannot delete user from DB. deleteUser()", e);
            throw e;
        }
    }

    public boolean checkIfUserEmailIsAlreadyExists(String email) throws SQLException {
        String sql = "SELECT * FROM acl_user WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            log.error("DAO Cannot check if User email is already exists in DB. checkIfUserEmailIsAlreadyExists()", e);
            throw e;
        }
    }

    public UserVO getUserById(Long userId) throws SQLException {
        String sql = "SELECT * FROM acl_user WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            UserVO userVO = null;
            if (rs.next()) userVO = new UserVO(rs);
            return userVO;
        } catch (SQLException e) {
            log.error("DAO Cannot get user by id from DB. getUserById()", e);
            throw e;
        }
    }

}
