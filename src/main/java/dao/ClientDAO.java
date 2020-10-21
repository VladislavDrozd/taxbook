package dao;

import filter.ClientFilter;
import org.apache.log4j.Logger;
import vo.ClientVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    private static final Logger log = Logger.getLogger(ClientDAO.class);

    private Connection connection;
    public ClientDAO(Connection connection) {
        this.connection = connection;
    }

    public Long addClient(Long userId, ClientVO vo) throws SQLException {
        String sql = "INSERT INTO client (user_id, create_date, name, contacts, edrpou, notes) " +
                "VALUES (?, NOW(), ?, ?, ?, ?) RETURNING user_id;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
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

    public int updateClient(Long userId, ClientVO vo) throws SQLException {
        String sql = "UPDATE client SET name = ?, contacts = ?, edrpou = ?, notes = ? " +
                "WHERE client_id = ? AND user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, vo.getName());
            ps.setString(2, vo.getContacts());
            ps.setString(3, vo.getEdrpou());
            ps.setString(4, vo.getNotes());
            ps.setLong(5, vo.getClientId());
            ps.setLong(6, userId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            log.error("DAO Cannot update client to DB. updateClient()", e);
            throw e;
        }
    }

    public int deleteClient(Long userId, Long clientId) throws SQLException {
        String sql = "DELETE FROM client WHERE user_id = ? AND client_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setLong(2, clientId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            log.error("DAO Cannot delete client from DB. deleteClient()", e);
            throw e;
        }
    }

    public int deleteClientsByUserId(Long userId) throws SQLException {
        String sql = "DELETE FROM client WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            log.error("DAO Cannot delete clients by userId from DB. deleteClient()", e);
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
            log.error("DAO Cannot get client by id from DB. getClientById()", e);
            throw e;
        }
    }

    public List<ClientVO> getAllClients(Long userId) throws SQLException {
        String sql = "SELECT * FROM client WHERE user_id = ? " +
                "ORDER BY create_date DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet rs  = ps.executeQuery();
            List<ClientVO> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new ClientVO(rs));
            }
            return list;
        } catch (SQLException e) {
            log.error("DAO Cannot get all clients from DB. getAllClients()", e);
            throw e;
        }
    }

    public List<ClientVO> getClientListByFilter(Long userId, ClientFilter filter) throws SQLException {
        String sql = "SELECT * FROM client WHERE user_id = " + userId + getFilterWhereClauseQueryString(filter) + " " +
                "ORDER BY create_date DESC";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs  = statement.executeQuery(sql);
            List<ClientVO> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new ClientVO(rs));
            }
            return list;
        } catch (SQLException e) {
            log.error("DAO Cannot get clients by filter from DB. getClientListByFilter()", e);
            throw e;
        }
    }

    private String getFilterWhereClauseQueryString(ClientFilter filter) {
        List<String> strings = new ArrayList<>();
        if (filter.getCreateDateFrom() != null) {
            strings.add("create_date >= '" + filter.getCreateDateFrom() + "'");
        }
        if (filter.getCreateDateTo() != null) {
            strings.add("create_date <= '" + filter.getCreateDateTo() + "'");
        }
        if (filter.getContacts() != null && !filter.getContacts().isEmpty()) {
            strings.add("contacts ILIKE '%" + filter.getContacts() + "%' ");
        }
        if (filter.getClientId() != null) {
            strings.add("client_id = " + filter.getClientId());
        }

        if (strings.size() == 0) {
            return "";
        } else {
            return " AND ".concat(String.join(" AND ", strings));
        }

    }

}
