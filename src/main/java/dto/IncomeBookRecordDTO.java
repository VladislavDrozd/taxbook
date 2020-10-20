package dto;

import vo.ClientVO;
import vo.IncomeBookRecordVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class IncomeBookRecordDTO extends IncomeBookRecordVO {
    public IncomeBookRecordDTO(Long recordId, Long userId, Timestamp dateTime, Double income, Double refund, Double revised, Double freeReceived, Double totalIncome, String notes, Long clientId, String anotherProfitType, Double anotherProfitIncome) {
        super(recordId, userId, dateTime, income, refund, revised, freeReceived, totalIncome, notes, clientId, anotherProfitType, anotherProfitIncome);
    }
    public IncomeBookRecordDTO(IncomeBookRecordVO vo) {
        super.setRecordId(vo.getRecordId());
        super.setUserId(vo.getUserId());
        super.setDateTime(vo.getDateTime());
        super.setIncome(vo.getIncome());
        super.setRefund(vo.getRefund());
        super.setRevised(vo.getRevised());
        super.setFreeReceived(vo.getFreeReceived());
        super.setTotalIncome(vo.getTotalIncome());
        super.setNotes(vo.getNotes());
        super.setClientId(vo.getClientId());
        super.setAnotherProfitType(vo.getAnotherProfitType());
        super.setAnotherProfitIncome(vo.getAnotherProfitIncome());
    }

    private ClientVO clientVO;

    public ClientVO getClientVO() {
        return clientVO;
    }

    public void setClientVO(ClientVO clientVO) {
        this.clientVO = clientVO;
    }
}
