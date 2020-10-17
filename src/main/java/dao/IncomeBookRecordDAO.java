package dao;

import org.apache.log4j.Logger;
import vo.IncomeBookRecordVO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class IncomeBookRecordDAO {
    private static final Logger log = Logger.getLogger(IncomeBookRecordDAO.class);

    private Connection connection;
    public IncomeBookRecordDAO(Connection connection) {
        this.connection = connection;
    }

    public void addListOfIncomeBoorRecords(List<IncomeBookRecordVO> records) throws SQLException {
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
