package vo;

import java.sql.Timestamp;

public class IncomeBookRecordVO {

    private Long recordId;
    private Long userId;
    private Timestamp dateTime;
    private Double income;
    private Double refund;
    private Double revised;
    private Double freeReceived;
    private Double totalIncome;
    private String notes;
    private Long clientId;
    private String anotherProfitType;
    private Double anotherProfitIncome;

    public IncomeBookRecordVO(Long recordId, Long userId, Timestamp dateTime, Double income, Double refund, Double revised, Double freeReceived, Double totalIncome, String notes, Long clientId, String anotherProfitType, Double anotherProfitIncome) {
        this.recordId = recordId;
        this.userId = userId;
        this.dateTime = dateTime;
        this.income = income;
        this.refund = refund;
        this.revised = revised;
        this.freeReceived = freeReceived;
        this.totalIncome = totalIncome;
        this.notes = notes;
        this.clientId = clientId;
        this.anotherProfitType = anotherProfitType;
        this.anotherProfitIncome = anotherProfitIncome;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getRefund() {
        return refund;
    }

    public void setRefund(Double refund) {
        this.refund = refund;
    }

    public Double getRevised() {
        return revised;
    }

    public void setRevised(Double revised) {
        this.revised = revised;
    }

    public Double getFreeReceived() {
        return freeReceived;
    }

    public void setFreeReceived(Double freeReceived) {
        this.freeReceived = freeReceived;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getAnotherProfitType() {
        return anotherProfitType;
    }

    public void setAnotherProfitType(String anotherProfitType) {
        this.anotherProfitType = anotherProfitType;
    }

    public Double getAnotherProfitIncome() {
        return anotherProfitIncome;
    }

    public void setAnotherProfitIncome(Double anotherProfitIncome) {
        this.anotherProfitIncome = anotherProfitIncome;
    }

    @Override
    public String toString() {
        return "IncomeBookRecordVO{" +
                "recordId=" + recordId +
                ", userId=" + userId +
                ", dateTime=" + dateTime +
                ", income=" + income +
                ", refund=" + refund +
                ", revised=" + revised +
                ", freeReceived=" + freeReceived +
                ", totalIncome=" + totalIncome +
                ", notes='" + notes + '\'' +
                ", clientId=" + clientId +
                ", anotherProfitType='" + anotherProfitType + '\'' +
                ", anotherProfitIncome=" + anotherProfitIncome +
                '}';
    }
}