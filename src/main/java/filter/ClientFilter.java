package filter;

import java.util.Date;

public class ClientFilter {
    private Date createDateFrom;
    private Date createDateTo;
    private String contacts;
    private Long clientId;

    public Date getCreateDateFrom() {
        return createDateFrom;
    }

    public void setCreateDateFrom(Date createDateFrom) {
        this.createDateFrom = createDateFrom;
    }

    public Date getCreateDateTo() {
        return createDateTo;
    }

    public void setCreateDateTo(Date createDateTo) {
        this.createDateTo = createDateTo;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
