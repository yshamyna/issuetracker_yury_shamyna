package org.training.issuetracker.web.servlets.status;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.Status;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.StatusService;
import org.training.issuetracker.web.constants.GeneralConsants;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class AddStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddStatusServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher(URLConstants.ADD_STATUS_JSP).
			forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
						getAttribute(ParameterConstants.USER);
			
			Status status = new Status();
			status.setName(request.getParameter(ParameterConstants.NAME));	
			
			StatusService service = new StatusService(user);
			service.add(status);
			
			response.getWriter().println(MessageConstants.STATUS_ADDED);
		} catch (JdbcSQLException e) {
			response.getWriter().println(MessageConstants.STATUS_EXIST
					+ request.getParameter(ParameterConstants.NAME) 
					+ GeneralConsants.SINGLE_QUOTE);
		} catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
