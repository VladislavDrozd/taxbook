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

    public void generateIncomeBookData(Long userId, int quantity, Long maxClientId) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            DBPool.startTransaction(connection);
            IncomeBookRecordDAO recordDAO = new IncomeBookRecordDAO(connection);
            recordDAO.addListOfIncomeBookRecordsTest(generateTestRecords(userId, quantity, maxClientId));
            DBPool.commitTransaction(connection);
        } catch (Exception e) {
            DBPool.rollbackTransaction(connection);
            log.error("DELEGATE TEST Cannot generate test income book data. generateIncomeBookData()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    private List<IncomeBookRecordVO> generateTestRecords(Long userId, int quantity, Long maxClientId) {
        List <IncomeBookRecordVO> list = new ArrayList<>();
        Double income, refund, revised, freeReceived, totalIncome;
        long dateFrom = 1577829600000L; // 2020-01-01
        long dateTo = new Date().getTime();
        for (int i = 0; i < quantity; i++) {
            income = random(1, 10500);
            refund = 0D;
            if (random(1, 10).intValue() == 1) refund = random(2, income.intValue());
            revised = income - refund;
            freeReceived = 0D;
            if (random(1, 10).intValue() == 1) freeReceived = random(1, 1500);
            totalIncome = revised + freeReceived;
            Long clientId = null;
            if (maxClientId != null && random(1, 5).intValue() == 2) {
                clientId = random(1, maxClientId.intValue()).longValue();
            }

            String anotherProfitType = "";
            Double anotherProfitIncome = null;
            if (random(1, 8).intValue() == 2) {
                anotherProfitType = getRandomString(1, 3);
                anotherProfitIncome = random(1, 3000);
            }

            list.add(new IncomeBookRecordVO(
                    null,
                    userId,
                    new Timestamp((long) ((Math.floor(Math.random() * (dateTo - dateFrom) + dateFrom)) / 1000) * 1000 ),
                    income,
                    refund != 0 ? refund : null,
                    revised,
                    freeReceived != 0 ? freeReceived : null,
                    totalIncome,
                    getRandomString(2, 10),
                    clientId,
                    anotherProfitType,
                    anotherProfitIncome
            ));
        }
        return list;
    }

    private Double random(int min, int max) {
        return Math.floor(Math.random() * (max - min)) + min;
    }

    private String getRandomString(int minWord, int maxWord) {
        String loremText = "Соображения высшего порядка, а также сложившаяся структура организации играет важную роль в формировании ключевых компонентов планируемого обновления! Равным образом дальнейшее развитие различных форм деятельности играет важную роль в формировании новых предложений? Соображения высшего порядка, а также консультация с профессионалами из IT обеспечивает широкому кругу специалистов участие в формировании позиций, занимаемых участниками в отношении поставленных задач? " +
                "С другой стороны выбранный нами инновационный путь представляет собой интересный эксперимент проверки системы масштабного изменения ряда параметров? Не следует, однако, забывать о том, что курс на социально-ориентированный национальный проект способствует повышению актуальности дальнейших направлений развития проекта. Значимость этих проблем настолько очевидна, что сложившаяся структура организации требует от нас системного анализа всесторонне сбалансированных нововведений. Повседневная практика показывает, что социально-экономическое развитие играет важную роль в формировании ключевых компонентов планируемого обновления? " +
                "Повседневная практика показывает, что реализация намеченного плана развития требует определения и уточнения форм воздействия! Разнообразный и богатый опыт постоянное информационно-техническое обеспечение нашей деятельности способствует подготовке и реализации дальнейших направлений развитая системы массового участия. Дорогие друзья, постоянный количественный рост и сфера нашей активности играет важную роль в формировании направлений прогрессивного развития! Не следует, однако, забывать о том, что начало повседневной работы по формированию позиции играет важную роль в формировании дальнейших направлений развитая системы массового участия. " +
                "Таким образом, начало...";
        String[] arr = loremText.split(" ");
        int quantity;
        if (random(1, 3) == 1) {
            quantity = random(minWord, maxWord).intValue();
        } else return "";
        int begin = random(0, arr.length-quantity-1).intValue();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < quantity; i++) {
            sb.append(arr[begin + i]).append(" ");
        }
        return sb.toString();
    }

}
