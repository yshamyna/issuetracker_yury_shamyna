package org.training.issuetracker.web.servlets.status;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Status;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.StatusService;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class StatusesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StatusesServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		execute(request, response);
	}
	
	private void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
							getAttribute(ParameterConstants.USER);
			
			StatusService service = new StatusService(user);
			List<Status> statuses = service.getStatuses();
			
			request.setAttribute(ParameterConstants.STATUSES, statuses);
			
			getServletContext().getRequestDispatcher(URLConstants.STATUSES_JSP).
				forward(request, response);
		}  catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		} 
	}
}
