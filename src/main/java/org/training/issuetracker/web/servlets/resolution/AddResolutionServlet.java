package org.training.issuetracker.web.servlets.resolution;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.Resolution;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.ResolutionService;
import org.training.issuetracker.web.constants.GeneralConsants;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class AddResolutionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddResolutionServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher(URLConstants.ADD_RESOLUTION_JSP).
					forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
							getAttribute(ParameterConstants.USER);
			
			Resolution resolution = new Resolution();
			resolution.setName(request.getParameter(ParameterConstants.NAME));
			
			ResolutionService service = new ResolutionService(user);
			service.add(resolution);
			
			response.getWriter().println(MessageConstants.RESOLUTION_ADDED);
		} catch (JdbcSQLException e) {
			response.getWriter().println(MessageConstants.RESOLUTION_EXIST 
					+ request.getParameter(ParameterConstants.NAME) 
					+ GeneralConsants.SINGLE_QUOTE);
		} catch (Exception e) {
			response.getWriter().
				println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
