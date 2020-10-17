package dao;

import org.apache.log4j.Logger;
import vo.IncomeBookRecordVO;

import java.sql.*;
import java.util.List;

public class IncomeBookRecordDAO {
    private static final Logger log = Logger.getLogger(IncomeBookRecordDAO.class);

    private Connection connection;
    public IncomeBookRecordDAO(Connection connection) {
        this.connection = connection;
    }

    public Long addRecord(IncomeBookRecordVO vo) throws SQLException {
        String sql = "INSERT INTO income_book " +
                "(user_id, date_time, income, refund, revised, free_received, total_income, notes, client_id, another_profit_type, another_profit_income) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING record_id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, vo.getUserId());
            ps.setTimestamp(2, vo.getDateTime());
            ps.setDouble(3, vo.getIncome());
            ps.setDouble(4, vo.getRefund());
            ps.setDouble(5, vo.getRevised());
            ps.setDouble(6, vo.getFreeReceived());
            ps.setDouble(7, vo.getTotalIncome());
            ps.setString(8, vo.getNotes());
            ps.setLong(9, vo.getClientId());
            ps.setString(10, vo.getAnotherProfitType());
            ps.setDouble(11, vo.getAnotherProfitIncome());
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

    public int updateRecord(IncomeBookRecordVO vo) throws SQLException {
        String sql = "UPDATE income_book " +
                "SET date_time = ?, income = ?, refund = ?, revised = ?, free_received = ?, " +
                        "total_income = ?, notes = ?, client_id = ?, " +
                        "another_profit_type = ?, another_profit_income = ? " +
                "WHERE record_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, vo.getDateTime());
            ps.setDouble(2, vo.getIncome());
            ps.setDouble(3, vo.getRefund());
            ps.setDouble(4, vo.getRevised());
            ps.setDouble(5, vo.getFreeReceived());
            ps.setDouble(6, vo.getTotalIncome());
            ps.setString(7, vo.getNotes());
            ps.setLong(8, vo.getClientId());
            ps.setString(9, vo.getAnotherProfitType());
            ps.setDouble(10, vo.getAnotherProfitIncome());
            ps.setLong(11, vo.getRecordId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            log.error("DAO Cannot update record in income book in DB. addListOfIncomeBoorRecords()", e);
            throw e;
        }
    }

    public int deleteRecord(Long userId, Long recordId) throws SQLException {
        String sql = "DELETE FROM income_book WHERE user_id = ? AND record_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setLong(2, recordId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            log.error("DAO Cannot update record in income book in DB. addListOfIncomeBoorRecords()", e);
            throw e;
        }
    }

    public void addListOfIncomeBookRecordsTest(List<IncomeBookRecordVO> records) throws SQLException {
        String sql = "INSERT INTO income_book " +
                "(user_id, date_time, income, refund, revised, free_received, total_income, notes) " +
                "VALUES " + recordListToString(records);
        System.out.println(sql);
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

}
