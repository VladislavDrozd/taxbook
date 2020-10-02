package logic;

import dao.UserDAO;
import vo.UserVO;

public class UserDelegate {

    public Long addUser(UserVO userVO) throws Exception {
        UserDAO userDAO = new UserDAO();

        // some vova`s logic

        return userDAO.addUser(userVO);
    }

}
