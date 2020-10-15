package dto;

import vo.UserVO;

import java.util.Date;

public class UserDTO extends UserVO {
    public UserDTO() {}

    public UserDTO(Long userId, String name, String email, String phone, String taxGroup,
                   String password, Date createDate, Date lastLoginDate, Character isActive) {
        super(userId, name, email, phone, taxGroup, password, createDate, lastLoginDate, isActive);

    }
}
