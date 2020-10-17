package dao;

import org.apache.log4j.Logger;
import vo.ClientVO;
import vo.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO {
    private static final Logger log = Logger.getLogger(ClientDAO.class);

    private Connection connection;
    public ClientDAO(Connection connection) {
        this.connection = connection;
    }

    public Long addClient(ClientVO vo) throws SQLException {
        String sql = "INSERT INTO client (user_id, create_date, name, contacts, edrpou, notes) " +
                "VALUES (?, NOW(), ?, ?, ?, ?) RETURNING user_id;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, vo.getUserId());
            ps.setString(2, vo.getName());
            ps.setString(3, vo.getContacts());
            ps.setString(4, vo.getEdrpou());
            ps.setString(5, vo.getNotes());
            ResultSet rs = ps.executeQuery();
            Long clientId = null;
            while (rs.next()) {
                clientId = rs.getLong("client_id");
            }
            return clientId;
        } catch (SQLException e) {
            log.error("DAO Cannot add new client to DB. addUser()", e);
            throw e;
        }
    }

    public int updateClient(ClientVO vo) throws SQLException {
        String sql = "UPDATE client SET name = ?, contacts = ?, edrpou = ?, notes = ? " +
                "WHERE client_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, vo.getName());
            ps.setString(2, vo.getContacts());
            ps.setString(3, vo.getEdrpou());
            ps.setString(4, vo.getNotes());
            ps.setLong(5, vo.getClientId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            log.error("DAO Cannot add new client to DB. addUser()", e);
            throw e;
        }
    }

    public ClientVO getClientById(Long clientId) throws SQLException {
        String sql = "SELECT * FROM client WHERE client_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, clientId);
            ResultSet rs = ps.executeQuery();
            ClientVO clientVO = null;
            while (rs.next()) {
                clientVO = new ClientVO(rs);
            }
            return clientVO;
        } catch (SQLException e) {
            log.error("DAO Cannot add new client to DB. addUser()", e);
            throw e;
        }
    }

}
