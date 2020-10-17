package dto;

import vo.UserVO;
import java.sql.Timestamp;

public class UserDTO extends UserVO {
    public UserDTO() {}

    public UserDTO(Long userId, String name, String email, String phone, String taxGroup,
                   String password, Timestamp createDate, Timestamp lastLoginDate, Character isActive) {
        super(userId, name, email, phone, taxGroup, password, createDate, lastLoginDate, isActive);

    }
}
