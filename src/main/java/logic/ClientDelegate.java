package logic;

import dao.ClientDAO;
import dao.IncomeBookRecordDAO;
import def.DBPool;
import filter.ClientFilter;
import org.apache.log4j.Logger;
import vo.ClientVO;

import java.sql.Connection;
import java.util.List;

public class ClientDelegate {
    private static final Logger log = Logger.getLogger(ClientDelegate.class);

    public Long addClient(Long userId, ClientVO clientVO) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            ClientDAO clientDAO = new ClientDAO(connection);
            return clientDAO.addClient(userId, clientVO);
        } catch (Exception e) {
            log.error("DELEGATE Cannot add new client. addClient()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    public int updateClient(Long userId, ClientVO clientVO) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            ClientDAO clientDAO = new ClientDAO(connection);
            return clientDAO.updateClient(userId, clientVO);
        } catch (Exception e) {
            log.error("DELEGATE Cannot update client. updateClient()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    public ClientVO getClientById(Long clientId) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            ClientDAO clientDAO = new ClientDAO(connection);
            return clientDAO.getClientById(clientId);
        } catch (Exception e) {
            log.error("DELEGATE Cannot get client by ID. getClientById()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    public List<ClientVO> getAllClients(Long userId) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            ClientDAO clientDAO = new ClientDAO(connection);
            return clientDAO.getAllClients(userId);
        } catch (Exception e) {
            log.error("DELEGATE Cannot get all clients. getAllClients()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    public List<ClientVO> getClientListByFilter(Long userId, ClientFilter filter) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            ClientDAO clientDAO = new ClientDAO(connection);
            return clientDAO.getClientListByFilter(userId, filter);
        } catch (Exception e) {
            log.error("DELEGATE Cannot get client by filter. getClientListByFilter()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

}
