package filter;

import java.sql.Timestamp;

public class IncomeBookRecordFilter {
    private Timestamp dateFrom;
    private Timestamp dateTo;
    private Long clientId;
    private Double incomeFrom;

    public IncomeBookRecordFilter(Timestamp dateFrom, Timestamp dateTo, Long clientId, Double incomeFrom) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.clientId = clientId;
        this.incomeFrom = incomeFrom;
    }

    public Timestamp getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Timestamp dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Timestamp getDateTo() {
        return dateTo;
    }

    public void setDateTo(Timestamp dateTo) {
        this.dateTo = dateTo;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Double getIncomeFrom() {
        return incomeFrom;
    }

    public void setIncomeFrom(Double incomeFrom) {
        this.incomeFrom = incomeFrom;
    }
}
