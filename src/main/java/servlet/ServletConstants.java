package servlet;

public class ServletConstants {

    public static String APP_LINK = ""; // set from web.xml when tomcat deploy application

    public static final String LANGUAGE_UA = "ua";
    public static final String LANGUAGE_EN = "en";

    public static final String ACTION = "action";
    public static final int STATUS_OK = 200;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_UNAUTHORIZED = 401;
    public static final int STATUS_SERVER_ERROR = 500;

    /** Attribute names */
    public static final String ATTRIBUTE_NAME_USER_ID = "userId";
    public static final String COOKIE_NAME_LANGUAGE = "language";

    /** LoginLogoutServlet */
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String REGISTER = "register";
    public static final String SEND_SIMPLE_EMAIL = "sendSimpleEmail";

    /** UserServlet */
    public static final String GET_USER_BY_ID = "getUserById";
    public static final String UPDATE_USER = "updateUser";
    public static final String DELETE_USER = "deleteUser";

    /** ClientServlet */
    public static final String ADD_CLIENT = "addClient";
    public static final String UPDATE_CLIENT = "updateClient";
    public static final String DELETE_CLIENT = "deleteClient";
    public static final String GET_CLIENT_BY_ID = "getClientById";
    public static final String GET_ALL_CLIENTS = "getAllClients";
    public static final String GET_CLIENTS_BY_FILTER = "getClientsByFilter";

    /** IncomeBookRecordServlet*/
    public static final String ADD_RECORD = "addRecord";
    public static final String UPDATE_RECORD = "updateRecord";
    public static final String DELETE_RECORD = "deleteRecord";
    public static final String GET_ALL_RECORDS = "getAllRecords";
    public static final String GET_RECORDS_BY_FILTER = "getRecordsByFilter";

    /** TestServlet */
    public static final String GENERATE_INCOME_BOOK_DATA = "generateRecords";
}
