package logic;

import dao.ClientDAO;
import dao.IncomeBookRecordDAO;
import def.DBPool;
import dto.IncomeBookRecordDTO;
import filter.IncomeBookRecordFilter;
import org.apache.log4j.Logger;
import vo.IncomeBookRecordVO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class IncomeBookRecordDelegate {
    private static final Logger log = Logger.getLogger(IncomeBookRecordDelegate.class);

    public Long addRecord(Long userId, IncomeBookRecordVO incomeBookRecordVO) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            IncomeBookRecordDAO incomeBookRecordDAO = new IncomeBookRecordDAO(connection);
            return incomeBookRecordDAO.addRecord(userId, incomeBookRecordVO);
        } catch (Exception e) {
            log.error("DELEGATE IncomeBookRecord Cannot add record to income book. addRecord()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    public int updateRecord(Long userId, IncomeBookRecordVO incomeBookRecordVO) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            IncomeBookRecordDAO incomeBookRecordDAO = new IncomeBookRecordDAO(connection);
            return incomeBookRecordDAO.updateRecord(userId, incomeBookRecordVO);
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

    public List<IncomeBookRecordDTO> getAllRecords(Long userId) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            IncomeBookRecordDAO incomeBookRecordDAO = new IncomeBookRecordDAO(connection);
            ClientDAO clientDAO = new ClientDAO(connection);

            List<IncomeBookRecordVO> incomeBookRecordVOList = incomeBookRecordDAO.getAllRecords(userId);
            List<IncomeBookRecordDTO> incomeBookRecordDTOList = incomeBookRecordVOList.stream()
                    .map(IncomeBookRecordDTO::new).collect(Collectors.toList());

            incomeBookRecordDTOList.forEach( record -> {
                if (record.getClientId() != null) {
                    try {
                        record.setClientVO(clientDAO.getClientById(record.getClientId()));
                    } catch (SQLException e) {
                        record.setClientVO(null);
                        log.error("DELEGATE IncomeBookRecord Cannot set ClientVO for IncomeBookRecordDTO. " +
                                "incomeBookRecordDTOList.forEach( record -> {...", e);
                    }
                }
            });
            return incomeBookRecordDTOList;
        } catch (Exception e) {
            log.error("DELEGATE IncomeBookRecord Cannot get all records in income book. getAllRecords()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    public List<IncomeBookRecordDTO> getRecordsByFilter(Long userId, IncomeBookRecordFilter filter) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            IncomeBookRecordDAO incomeBookRecordDAO = new IncomeBookRecordDAO(connection);
            ClientDAO clientDAO = new ClientDAO(connection);

            List<IncomeBookRecordVO> incomeBookRecordVOList = incomeBookRecordDAO.getRecordsByFilter(userId, filter);
            List<IncomeBookRecordDTO> incomeBookRecordDTOList = incomeBookRecordVOList.stream()
                    .map(IncomeBookRecordDTO::new).collect(Collectors.toList());

            incomeBookRecordDTOList.forEach( record -> {
                if (record.getClientId() != null) {
                    try {
                        record.setClientVO(clientDAO.getClientById(record.getClientId()));
                    } catch (SQLException e) {
                        record.setClientVO(null);
                        log.error("DELEGATE IncomeBookRecord with filter Cannot set ClientVO for IncomeBookRecordDTO. " +
                                "incomeBookRecordDTOList.forEach( record -> {...", e);
                    }
                }
            });
            return incomeBookRecordDTOList;
        } catch (Exception e) {
            log.error("DELEGATE IncomeBookRecord Cannot records by filter in income book. getAllRecords()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

}
