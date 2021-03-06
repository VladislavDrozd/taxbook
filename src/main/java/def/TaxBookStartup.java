package def;

import logic.hash.ArgonInitialize;
import org.apache.log4j.Logger;
import servlet.ServletConstants;

import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;


public class TaxBookStartup implements ServletContextListener {
    private static final Logger log = Logger.getLogger(TaxBookStartup.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletConstants.APP_LINK = sce.getServletContext().getInitParameter("APP_LINK");
            AppConstants.initializeProperties();
            DBPool.initializeConnectionPool();
            ArgonInitialize.getInstance();
        } catch (IOException | NamingException e) {
            log.error("Cannot initialize application", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
