package dao;

import filter.IncomeBookRecordFilter;
import org.apache.log4j.Logger;
import util.DAOUtil;
import vo.IncomeBookRecordVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeBookRecordDAO extends DAOUtil {
    private static final Logger log = Logger.getLogger(IncomeBookRecordDAO.class);

    private Connection connection;
    public IncomeBookRecordDAO(Connection connection) {
        this.connection = connection;
    }

    public Long addRecord(Long userId, IncomeBookRecordVO vo) throws SQLException {
        String sql = "INSERT INTO income_book " +
                "(user_id, date_time, income, refund, revised, free_received, total_income, notes, client_id, another_profit_type, another_profit_income) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING record_id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setLong(ps, 1, userId);
            setTimestamp(ps,2, vo.getDateTime());
            setDouble(ps,3, vo.getIncome());
            setDouble(ps,4, vo.getRefund());
            setDouble(ps,5, vo.getRevised());
            setDouble(ps,6, vo.getFreeReceived());
            setDouble(ps,7, vo.getTotalIncome());
            setString(ps,8, vo.getNotes());
            setLong(ps, 9, vo.getClientId());
            setString(ps, 10, vo.getAnotherProfitType());
            setDouble(ps, 11, vo.getAnotherProfitIncome());
            ResultSet rs = ps.executeQuery();
            Long newRecordId = null;
            while (rs.next()) {
                newRecordId = rs.getLong("record_id");
            }
            return newRecordId;
        } catch (SQLException e) {
            log.error("DAO Cannot add record to income book in DB. addListOfIncomeBoorRecords()", e);
            throw e;
        }
    }

    public int updateRecord(Long userId, IncomeBookRecordVO vo) throws SQLException {
        String sql = "UPDATE income_book " +
                "SET date_time = ?, income = ?, refund = ?, revised = ?, free_received = ?, " +
                        "total_income = ?, notes = ?, client_id = ?, " +
                        "another_profit_type = ?, another_profit_income = ? " +
                "WHERE record_id = ? AND user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setTimestamp(ps, 1, vo.getDateTime());
            setDouble(ps, 2, vo.getIncome());
            setDouble(ps, 3, vo.getRefund());
            setDouble(ps, 4, vo.getRevised());
            setDouble(ps, 5, vo.getFreeReceived());
            setDouble(ps, 6, vo.getTotalIncome());
            setString(ps, 7, vo.getNotes());
            setLong(ps, 8, vo.getClientId());
            setString(ps, 9, vo.getAnotherProfitType());
            setDouble(ps, 10, vo.getAnotherProfitIncome());
            setLong(ps, 11, vo.getRecordId());
            setLong(ps, 12, userId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            log.error("DAO Cannot update record in income book in DB. addListOfIncomeBoorRecords()", e);
            throw e;
        }
    }

    public int deleteRecord(Long userId, Long recordId) throws SQLException {
        String sql = "DELETE FROM income_book WHERE user_id = ? AND record_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setLong(ps, 1, userId);
            setLong(ps, 2, recordId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            log.error("DAO Cannot delete record in income book in DB. deleteRecord()", e);
            throw e;
        }
    }

    public int deleteRecordsByUserId(Long userId) throws SQLException {
        String sql = "DELETE FROM income_book WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setLong(ps, 1, userId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            log.error("DAO Cannot delete records by userId in income book in DB. deleteRecordsByUserId()", e);
            throw e;
        }
    }

    public List<IncomeBookRecordVO> getAllRecords(Long userId) throws SQLException {
        String sql = "SELECT * FROM income_book WHERE user_id = ? " +
                "ORDER BY date_time DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setLong(ps, 1, userId);
            ResultSet rs = ps.executeQuery();
            List<IncomeBookRecordVO> incomeBookRecordVOList = new ArrayList<>();
            while (rs.next()) {
                incomeBookRecordVOList.add(new IncomeBookRecordVO(rs));
            }
            return incomeBookRecordVOList;
        } catch (SQLException e) {
            log.error("DAO Cannot get all records from income book in DB. getAllRecords()", e);
            throw e;
        }
    }

    public List<IncomeBookRecordVO> getRecordsByFilter(Long userId, IncomeBookRecordFilter filter) throws SQLException {
        String sql = "SELECT * FROM income_book WHERE user_id = " + userId + getFilterWhereClauseQueryString(filter) + " " +
                "ORDER BY date_time DESC";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            List<IncomeBookRecordVO> incomeBookRecordVOList = new ArrayList<>();
            while (rs.next()) {
                incomeBookRecordVOList.add(new IncomeBookRecordVO(rs));
            }
            return incomeBookRecordVOList;
        } catch (SQLException e) {
            log.error("DAO Cannot get records by filter from income book in DB. getRecordsByFilter()", e);
            throw e;
        }
    }

    public void addListOfIncomeBookRecordsTest(List<IncomeBookRecordVO> records) throws SQLException {
        String sql = "INSERT INTO income_book " +
                "(user_id, date_time, income, refund, revised, free_received, total_income, notes) " +
                "VALUES " + recordListToString(records);
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("DAO TEST Cannot add list of records to DB. addListOfIncomeBoorRecords()", e);
            throw e;
        }
    }

    private String recordListToString(List<IncomeBookRecordVO> records) {
        StringBuilder sb = new StringBuilder();
        records.forEach(
                r -> sb.append("(" + r.getUserId() + ", '" + r.getDateTime() + "', " + r.getIncome() + ", " + r.getRefund() + ", "
                        + r.getRevised() + ", " + r.getFreeReceived() + ", " + r.getTotalIncome() + ", '" + r.getNotes() + "'),")
        );
        String str = sb.toString();
        return str.substring(0, str.length()-1);
    }

    private String getFilterWhereClauseQueryString(IncomeBookRecordFilter filter) {
        List<String> strings = new ArrayList<>();
        if (filter.getDateFrom() != null) {
            strings.add("date_time >= '" + filter.getDateFrom() + "'");
        }
        if (filter.getDateTo() != null) {
            strings.add("date_time <= '" + filter.getDateTo() + "'");
        }
        if (filter.getClientId() != null) {
            strings.add("client_id = " + filter.getClientId());
        }
        if (filter.getClientId() != null) {
            strings.add("income >= " + filter.getIncomeFrom());
        }

        if (strings.size() == 0) {
            return "";
        } else {
            return " AND ".concat(String.join(" AND ", strings));
        }

    }

}
