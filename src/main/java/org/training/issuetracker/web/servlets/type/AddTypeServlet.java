package org.training.issuetracker.web.servlets.type;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.Type;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.TypeService;
import org.training.issuetracker.web.constants.GeneralConsants;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class AddTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddTypeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		getServletContext().getRequestDispatcher(URLConstants.ADD_TYPE_JSP).
				forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
						getAttribute(ParameterConstants.USER);
			
			Type type = new Type();
			type.setName(request.getParameter(ParameterConstants.NAME));
			
			TypeService service = new TypeService(user);
			service.add(type);
			
			response.getWriter().println(MessageConstants.TYPE_ADDED);
		} catch (JdbcSQLException e) {
			response.getWriter().println(MessageConstants.TYPE_EXIST
					+ request.getParameter(ParameterConstants.NAME) 
					+ GeneralConsants.SINGLE_QUOTE);
		} catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}
}
