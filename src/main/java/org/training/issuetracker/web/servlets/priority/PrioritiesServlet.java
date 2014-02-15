package org.training.issuetracker.web.servlets.priority;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Priority;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.PriorityService;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class PrioritiesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrioritiesServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
								getAttribute(ParameterConstants.USER);
			
			PriorityService service = new PriorityService(user);
			List<Priority> priorities = service.getPriorities();
			
			request.setAttribute(ParameterConstants.PRIORITIES, priorities);
			
			getServletContext().getRequestDispatcher(URLConstants.PRIORITIES_JSP).
				forward(request, response);
		}  catch (Exception e) {
			response.getWriter().
				println(MessageConstants.SORRY_MESSAGE);
		} 
	}

}
