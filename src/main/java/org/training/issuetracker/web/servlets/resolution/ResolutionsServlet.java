package org.training.issuetracker.web.servlets.resolution;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Resolution;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.ResolutionService;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class ResolutionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ResolutionsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		try {
			User user = (User) request.getSession().
							getAttribute(ParameterConstants.USER);
			
			ResolutionService service = new ResolutionService(user);
			List<Resolution> resolutions = service.getResolutions();
			
			request.setAttribute(ParameterConstants.RESOLUTIONS, resolutions);
			
			getServletContext().getRequestDispatcher(URLConstants.RESOLUTIONS_JSP).
				forward(request, response);
		}  catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		} 
	}
}
