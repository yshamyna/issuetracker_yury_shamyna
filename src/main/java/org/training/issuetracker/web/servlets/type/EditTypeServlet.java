package org.training.issuetracker.web.servlets.type;

import java.io.IOException;

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

public class EditTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditTypeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter(ParameterConstants.ID);
		if (id == null) {
			getServletContext().getRequestDispatcher(URLConstants.TYPES_URL).
					forward(request, response);
		} else {
			try {
				User user = (User) request.getSession().
							getAttribute(ParameterConstants.USER);
				
				long typeId = Integer.parseInt(id);
				
				TypeService service = new TypeService(user);
				Type type = service.getTypeById(typeId);
				
				if (type == null) {
					getServletContext().getRequestDispatcher(URLConstants.TYPES_URL).
							forward(request, response);
				} else {
					request.setAttribute(ParameterConstants.TYPE, type);
					getServletContext().getRequestDispatcher(URLConstants.EDIT_TYPE_JSP).
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher(URLConstants.TYPES_URL).
					forward(request, response);
			} catch(Exception e) {
				response.getWriter().println(MessageConstants.SORRY_MESSAGE);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
					getAttribute(ParameterConstants.USER);
			
			Type type = new Type();
			type.setId(Long.parseLong(request.getParameter(ParameterConstants.ID)));
			type.setName(request.getParameter(ParameterConstants.NAME));
			
			TypeService service = new TypeService(user);
			service.update(type);
			
			response.getWriter().println(MessageConstants.TYPE_UPDATED);
		} catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
