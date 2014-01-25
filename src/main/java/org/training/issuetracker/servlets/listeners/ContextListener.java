package org.training.issuetracker.servlets.listeners;

import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.h2.tools.RunScript;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
public class ContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ContextListener() {
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
        Connection connection = null;
        try {
        	try {
        		Class.forName("org.h2.Driver");
                connection = DriverManager.
                		getConnection("jdbc:h2:issuetracker", "sa", "");
                URL url = sce.getServletContext().getResource("issuetracker.sql");
                RunScript.execute(connection, 
                		new InputStreamReader(url.openStream()));
			} finally {
        		if (connection != null) {
        			connection.close();
        		}
        	}
        } catch(Exception e) {
        	e.printStackTrace();
        } 
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
    }
	
}
