package vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ClientVO {

    private Long clientId;
    private Long userId;
    private Date createDate;
    private String name;
    private String contacts;
    private String edrpou;
    private String notes;

    public ClientVO(Long clientId, Long userId, Date createDate, String name, String contacts, String edrpou, String notes) {
        this.clientId = clientId;
        this.userId = userId;
        this.createDate = createDate;
        this.name = name;
        this.contacts = contacts;
        this.edrpou = edrpou;
        this.notes = notes;
    }

    public ClientVO(ResultSet rs) throws SQLException {
        this.clientId = rs.getLong("client_id");
        this.userId = rs.getLong("user_id");
        this.createDate = rs.getDate("create_date");
        this.name = rs.getString("name");
        this.contacts = rs.getString("contacts");
        this.edrpou = rs.getString("edrpou");
        this.notes = rs.getString("notes");
    }

    public ClientVO() {

    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getEdrpou() {
        return edrpou;
    }

    public void setEdrpou(String edrpou) {
        this.edrpou = edrpou;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "ClientVO{" +
                "clientId=" + clientId +
                ", userId=" + userId +
                ", createDate=" + createDate +
                ", name='" + name + '\'' +
                ", contacts='" + contacts + '\'' +
                ", edrpou='" + edrpou + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
