package vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UserVO {

    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String taxGroup;
    private String password;
    private Date createDate;
    private Date lastLoginDate;
    private Character isActive;

    public UserVO() {}

    public UserVO(Long userId, String name, String email, String phone, String taxGroup, String password, Date createDate, Date lastLoginDate, Character isActive) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.taxGroup = taxGroup;
        this.password = password;
        this.createDate = createDate;
        this.lastLoginDate = lastLoginDate;
        this.isActive = isActive;
    }

    public UserVO(ResultSet rs) throws SQLException {
        this.userId = rs.getLong("user_id");
        this.name = rs.getString("name");
        this.email = rs.getString("email");
        this.phone = rs.getString("phone");
        this.taxGroup = rs.getString("tax_group");
        this.createDate = rs.getDate("create_date");
        this.lastLoginDate = rs.getDate("last_login_date");
        this.isActive = rs.getString("is_active").charAt(0);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTaxGroup() {
        return taxGroup;
    }

    public void setTaxGroup(String taxGroup) {
        this.taxGroup = taxGroup;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Character getIsActive() {
        return isActive;
    }

    public void setIsActive(Character isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "IncomeBookVO{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", taxGroup='" + taxGroup + '\'' +
                ", password='" + password + '\'' +
                ", createDate=" + createDate +
                ", lastLoginDate=" + lastLoginDate +
                ", isActive=" + isActive +
                '}';
    }
}
