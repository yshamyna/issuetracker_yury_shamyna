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

public class EditPriorityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditPriorityServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter(ParameterConstants.ID);
		if (id == null) {
			getServletContext().getRequestDispatcher(URLConstants.PRIORITIES_URL).
					forward(request, response);
		} else {
			try {
				User user = (User) request.getSession().
									getAttribute(ParameterConstants.USER);
				
				long priorityId = Integer.parseInt(id);
				
				PriorityService service = new PriorityService(user);
				Priority priority = service.getPriorityById(priorityId);
				if (priority == null) {
					getServletContext().getRequestDispatcher(URLConstants.PRIORITIES_URL).
							forward(request, response);
				} else {
					request.setAttribute(ParameterConstants.PRIORITY, priority);
					getServletContext().getRequestDispatcher(URLConstants.EDIT_PRIORITY_JSP).
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher(URLConstants.PRIORITIES_URL).
					forward(request, response);
			} catch(Exception e) {
				response.getWriter().println(MessageConstants.SORRY_MESSAGE);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute(ParameterConstants.USER);
			
			Priority priority  = new Priority();
			priority.setId(Long.parseLong(request.getParameter(ParameterConstants.ID)));
			priority.setName(request.getParameter(ParameterConstants.NAME));
			
			PriorityService service = new PriorityService(user);
			service.update(priority);
			
			response.getWriter().println(MessageConstants.PRIORITY_UPDATED);
		} catch (JdbcSQLException e) {
			response.getWriter().println(MessageConstants.PRIORITY_EXIST 
					+ request.getParameter(ParameterConstants.NAME) 
					+ GeneralConsants.SINGLE_QUOTE);
		} catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
