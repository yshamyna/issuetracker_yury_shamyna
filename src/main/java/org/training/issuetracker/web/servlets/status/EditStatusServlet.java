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

public class EditStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditStatusServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
		String id = request.getParameter(ParameterConstants.ID);
		if (id == null) {
			getServletContext().getRequestDispatcher(URLConstants.STATUSES_URL).
					forward(request, response);
		} else {
			try {
				User user = (User) request.getSession().
									getAttribute(ParameterConstants.USER);
				
				long statusId = Integer.parseInt(id);
				
				StatusService service = new StatusService(user);
				Status status = service.getStatusById(statusId);
				
				if (status == null) {
					getServletContext().getRequestDispatcher(URLConstants.STATUSES_URL).
							forward(request, response);
				} else {
					request.setAttribute(ParameterConstants.STATUS, status);
					getServletContext().getRequestDispatcher(URLConstants.EDIT_STATUS_JSP).
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher(URLConstants.STATUSES_URL).
					forward(request, response);
			} catch(Exception e) {
				response.getWriter().println(MessageConstants.SORRY_MESSAGE);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
							getAttribute(ParameterConstants.USER);
			
			Status status = new Status();
			status.setId(Long.parseLong(request.getParameter(ParameterConstants.ID)));
			status.setName(request.getParameter(ParameterConstants.NAME));	
			
			StatusService service = new StatusService(user);
			service.update(status);
			
			response.getWriter().println(MessageConstants.STATUS_UPDATED);
		} catch (JdbcSQLException e) {
			response.getWriter().println(MessageConstants.STATUS_EXIST
					+ request.getParameter(ParameterConstants.NAME) 
					+ GeneralConsants.SINGLE_QUOTE);
		} catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
