package org.training.issuetracker.web.listeners;

import java.io.File;
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
                
                String realPath = sce.getServletContext().getRealPath("");
                File tmpDir = new File(realPath + "\\temp");
                if (!tmpDir.isDirectory()) {
                	tmpDir.mkdirs();
                }
                File destDir = new File(realPath + "\\issues");
                if (!destDir.isDirectory()) {
                	destDir.mkdirs();
                } else {
                	File[] files = destDir.listFiles();
                	for (File f : files) {
                		delete(f);
                	}
                }
			} finally {
        		if (connection != null) {
        			connection.close();
        		}
        	}
        } catch(Exception e) {
        	e.printStackTrace();
        } 
    }
    
    private void delete(File file) {
    	if(!file.exists()) {
    		return;	
    	}
    	if(file.isDirectory()) {
    		File[] files = file.listFiles();
    		for(File f : files) {
    			delete(f);
    		}
    		file.delete();
    	} else {
    		file.delete();
    	}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
    }
	
}
