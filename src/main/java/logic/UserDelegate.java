package logic;

import dao.ClientDAO;
import dao.IncomeBookRecordDAO;
import dao.UserDAO;
import de.mkammerer.argon2.Argon2Helper;
import def.DBPool;
import logic.hash.ArgonInitialize;
import org.apache.log4j.Logger;
import vo.ClientVO;
import vo.UserVO;
/* Argon Types */
import java.io.Console;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;




public class UserDelegate {
    private static final Logger log = Logger.getLogger(UserDelegate.class);

    public Long addUser(UserVO userVO) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
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


            return userDAO.addUser(userVO);
        } catch (Exception e) {
            log.error("DELEGATE Cannot add new acl_user. addUser()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }


    public Long loginUser(String loginName, String password) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            Map<String, String> userIdAndHashPasswordFromDB = userDAO.getUserIdAndHashPasswordForLogin(loginName);

            Long userId;
            String uId = userIdAndHashPasswordFromDB.get("userId");
            if (uId != null) {
                userId = Long.parseLong(uId);
            } else return null;

            System.out.println("some logic from master");
            System.out.println("some logic from master");


            System.out.println("some logic from master2");

            while ("master".equals("ss")) {
                System.out.println("Y");
            }

            for (int i = 0; i < 100; i++) {
                System.out.println("just some new useless logic " + i);
            }

            String passwordDBHash = userIdAndHashPasswordFromDB.get("hashPassword");

            boolean isPasswordMatch = ArgonInitialize.getInstance()
                    .getArgon2().verify(passwordDBHash, password);

            if (!isPasswordMatch) {
                return null;
            } else {
                return userId;
            }
        } catch (Exception e) {
            log.error("DELEGATE Cannot login acl_user with " +
                    "loginName = " + loginName + " and password = " + password + ". updateUser()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);

        }
    }

    public UserVO getClientById(Long clientId) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            return userDAO.getUserById(clientId);
        } catch (Exception e) {
            log.error("DELEGATE Cannot get client by ID. getClientById()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    public void anotherMethodFromMaster() {
        System.out.println("some code");
    }

    public boolean checkIfUserEmailIsAlreadyExists(String email) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            return userDAO.checkIfUserEmailIsAlreadyExists(email);
        } catch (Exception e) {
            log.error("DELEGATE Cannot check if user email is already exist. checkIfUserEmailIsAlreadyExists()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    public int updateUser(Long userId, UserVO userVO) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            return userDAO.updateUser(userId, userVO);
        } catch (Exception e) {
            log.error("DELEGATE Cannot update acl_user. updateUser()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    public int deleteUser(Long userId) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            IncomeBookRecordDAO recordDAO = new IncomeBookRecordDAO(connection);
            ClientDAO clientDAO = new ClientDAO(connection);
            UserDAO userDAO = new UserDAO(connection);
            DBPool.startTransaction(connection);
                recordDAO.deleteRecordsByUserId(userId);
                clientDAO.deleteClientsByUserId(userId);
                int del = userDAO.deleteUser(userId);
            DBPool.commitTransaction(connection);
            return del;
        } catch (Exception e) {
            DBPool.rollbackTransaction(connection);
            log.error("DELEGATE Cannot delete acl_user by id. deleteUser()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }

    public UserVO getUserById(Long userId) throws Exception {
        Connection connection = null;
        try {
            connection = DBPool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            return userDAO.getUserById(userId);
        } catch (Exception e) {
            log.error("DELEGATE Cannot get acl_user by id. getUserById()", e);
            throw e;
        } finally {
            DBPool.closeConnection(connection);
        }
    }



}
