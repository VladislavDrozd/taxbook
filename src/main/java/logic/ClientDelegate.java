package logic;

import dao.ClientDAO;
import dao.IncomeBookRecordDAO;
import def.DBPool;
import org.apache.log4j.Logger;
import vo.ClientVO;

import java.sql.Connection;

public class ClientDelegate {
    private static final Logger log = Logger.getLogger(ClientDelegate.class);

    public Long addClient(ClientVO clientVO) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            ClientDAO clientDAO = new ClientDAO(connection);
            return clientDAO.addClient(clientVO);
        } catch (Exception e) {
            log.error("DELEGATE Cannot add new client. addClient()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    public int updateClient(ClientVO clientVO) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            ClientDAO clientDAO = new ClientDAO(connection);
            return clientDAO.updateClient(clientVO);
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

}
