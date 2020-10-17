package logic;

import dao.IncomeBookRecordDAO;
import def.DBPool;
import org.apache.log4j.Logger;
import vo.IncomeBookRecordVO;

import java.sql.Connection;

public class IncomeBookRecordDelegate {
    private static final Logger log = Logger.getLogger(IncomeBookRecordDelegate.class);

    public Long addRecord(IncomeBookRecordVO incomeBookRecordVO) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            IncomeBookRecordDAO incomeBookRecordDAO = new IncomeBookRecordDAO(connection);
            return incomeBookRecordDAO.addRecord(incomeBookRecordVO);
        } catch (Exception e) {
            log.error("DELEGATE IncomeBookRecord Cannot add record to income book. addRecord()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    public int updateRecord(IncomeBookRecordVO incomeBookRecordVO) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            IncomeBookRecordDAO incomeBookRecordDAO = new IncomeBookRecordDAO(connection);
            return incomeBookRecordDAO.updateRecord(incomeBookRecordVO);
        } catch (Exception e) {
            log.error("DELEGATE IncomeBookRecord Cannot update record in income book. updateRecord()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    public int deleteRecord(Long userId, Long recordId) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            IncomeBookRecordDAO incomeBookRecordDAO = new IncomeBookRecordDAO(connection);
            return incomeBookRecordDAO.deleteRecord(userId, recordId);
        } catch (Exception e) {
            log.error("DELEGATE IncomeBookRecord Cannot delete record in income book. deleteRecord()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

}
