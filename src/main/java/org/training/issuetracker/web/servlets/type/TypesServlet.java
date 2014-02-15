package org.training.issuetracker.web.servlets.type;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Type;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.TypeService;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class TypesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TypesServlet() {
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
			
			TypeService service = new TypeService(user);
			List<Type> types = service.getTypes();
			
			request.setAttribute(ParameterConstants.TYPES, types);
			
			getServletContext().getRequestDispatcher(URLConstants.TYPES_JSP).
				forward(request, response);
		}  catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		} 
	}
}
