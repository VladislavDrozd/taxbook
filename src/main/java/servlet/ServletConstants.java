package servlet;

public class ServletConstants {

    public static final String ACTION = "action";
    public static final int STATUS_OK = 200;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_SERVER_ERROR = 500;

    /** UserServlet */
    public static final String ADD_USER = "addUser";
    public static final String UPDATE_USER = "updateUser";

    /** ClientServlet */
    public static final String ADD_CLIENT = "addClient";
    public static final String UPDATE_CLIENT = "updateClient";
    public static final String GET_CLIENT_BY_ID = "getClientById";

    /** IncomeBookRecordServlet*/
    public static final String ADD_RECORD = "addRecord";
    public static final String UPDATE_RECORD = "updateRecord";
    public static final String DELETE_RECORD = "deleteRecord";

    /** TestServlet */
    public static final String GENERATE_INCOME_BOOK_DATA = "generateRecords";
}
