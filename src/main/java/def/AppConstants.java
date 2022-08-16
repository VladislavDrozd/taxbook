package def;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AppConstants {

    private AppConstants(){};

    public static String APP_LINK;

    public static String DB_LINK;
    public static String DB_NAME;
    public static String DB_USER;
    public static String DB_PASSWORD;

    public static void initializeProperties() throws IOException {
        Properties properties = new Properties();
        //properties.load(new FileReader(("classpath:app.properties")));
        properties.load(new FileReader(("D:\\learning_spring\\idea_projects\\taxbook\\src\\main\\resources\\app.properties")));
        APP_LINK = properties.getProperty("APP_LINK");
        DB_LINK = properties.getProperty("DB_LINK");
        DB_NAME = properties.getProperty("DB_NAME");
        DB_USER = properties.getProperty("DB_USER");
        DB_PASSWORD = properties.getProperty("DB_PASSWORD");
    }

}
