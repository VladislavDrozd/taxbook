package logic;

import dao.UserDAO;
import de.mkammerer.argon2.Argon2Helper;
import def.DBPool;
import logic.hash.ArgonInitialize;
import org.apache.log4j.Logger;
import vo.UserVO;
/* Argon Types */
import java.io.Console;
import java.sql.Connection;
import java.time.Duration;
import java.time.Instant;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;




public class UserDelegate {
    private static final Logger log = Logger.getLogger(UserDelegate.class);

    public Long addUser(UserVO userVO) throws Exception {
        try {
            Connection connection = DBPool.getConnection();
            UserDAO userDAO = new UserDAO(connection);

            // some vova`s logic

            //for time-resource testing
       /*
       Instant beginHash = Instant.now();
       Instant endHash = Instant.now();
       Duration.between(beginHash, endHash).toMillis() / 1024.0;

       Instant beginVerify = Instant.now();
       Instant endVerify = Instant.now();
        Duration.between(beginVerify, endVerify).toMillis() / 1024.0
       */

            char[] userPassword = userVO.getPassword().toCharArray();

            String hash;
            ArgonInitialize argon = ArgonInitialize.getInstance();

            try {
                hash = argon.getArgon2().hash(argon.getIterations(), argon.getMemory(), argon.getParallelism(), userPassword);
            }finally {
                argon.getArgon2().wipeArray(userPassword);
            }

            userVO.setPassword(hash);

            //TO Verify input password with hash from bd use this
            /* argon.getArgon2().verify(hash, userPassword);*/



            Long userId = userDAO.addUser(userVO);
            DBPool.closeConnection(connection);
            return userId;
        } catch (Exception e) {
            log.error("DELEGATE Cannot add new acl_user. addUser()", e);
            throw e;
        }
    }



}
