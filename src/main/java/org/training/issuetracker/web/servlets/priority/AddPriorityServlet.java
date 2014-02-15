package org.training.issuetracker.web.servlets.priority;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.Priority;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.PriorityService;
import org.training.issuetracker.web.constants.GeneralConsants;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class AddPriorityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddPriorityServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher(URLConstants.ADD_PRIORITY_JSP).
			forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute(ParameterConstants.USER);
			
			Priority priority  = new Priority();
			priority.setName(request.getParameter(ParameterConstants.NAME));
			
			PriorityService service = new PriorityService(user);
			service.add(priority);
			
			response.getWriter().println(MessageConstants.PRIORITY_ADDED);
		} catch (JdbcSQLException e) {
			response.getWriter().println(MessageConstants.PRIORITY_EXIST 
						+ request.getParameter(ParameterConstants.NAME) 
						+ GeneralConsants.SINGLE_QUOTE);
		} catch (Exception e) {
			response.getWriter().
				println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
