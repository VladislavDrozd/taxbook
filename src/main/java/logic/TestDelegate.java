package logic;

import dao.IncomeBookRecordDAO;
import def.DBPool;
import org.apache.log4j.Logger;
import vo.IncomeBookRecordVO;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestDelegate {
    private static final Logger log = Logger.getLogger(TestDelegate.class);

    public void generateIncomeBookData(Long userId, int quantity) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            DBPool.startTransaction(connection);
            IncomeBookRecordDAO recordDAO = new IncomeBookRecordDAO(connection);
            recordDAO.addListOfIncomeBoorRecords(generateTestRecords(userId, quantity));
            DBPool.commitTransaction(connection);
        } catch (Exception e) {
            DBPool.rollbackTransaction(connection);
            log.error("DELEGATE TEST Cannot generate test income book data. generateIncomeBookData()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    private List<IncomeBookRecordVO> generateTestRecords(Long userId, int quantity) {
        List <IncomeBookRecordVO> list = new ArrayList<>();
        Double income, refund, revised, freeReceived, totalIncome;
        long dateFrom = 1577829600000L; // 2020-01-01
        long dateTo = new Date().getTime();
        for (int i = 0; i < quantity; i++) {
            income = random(1, 10500);
            refund = 0D;
            if (random(1, 10) == 1) refund = random(2, income.intValue());
            revised = income - refund;
            freeReceived = 0D;
            if (random(1, 10) == 1) freeReceived = random(1, 1500);
            totalIncome = revised + freeReceived;
            list.add(new IncomeBookRecordVO(
                    null,
                    userId,
                    new Timestamp((long) (Math.floor(Math.random() * (dateTo - dateFrom) + dateFrom))),
                    income,
                    refund != 0 ? refund : null,
                    revised,
                    freeReceived != 0 ? freeReceived : null,
                    totalIncome,
                    "some notes",
                    null,
                    null,
                    null
            ));
        }
        return list;
    }

    private Double random(int min, int max) {
        return Math.floor(Math.random() * (max - min)) + min;
    }

}
