package org.training.issuetracker.web.servlets.build;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.BuildService;
import org.training.issuetracker.web.constants.GeneralConsants;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class AddBuildServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddBuildServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		try {
			Build build = new Build();
			
			build.setVersion(request.
					getParameter(ParameterConstants.VERSION));
			build.setProjectId(Integer.parseInt(request.
					getParameter(ParameterConstants.ID)));
			build.setCurrent(false);
			
			User user = (User) request.getSession().
					getAttribute(ParameterConstants.USER);
			
			BuildService service = new BuildService(user);
			service.add(build);
			
			response.getWriter().println(MessageConstants.BUILD_ADDED);
		} catch (JdbcSQLException e) {
			response.getWriter().println(MessageConstants.BUILD_EXIST
					+ request.getParameter(ParameterConstants.VERSION) 
					+ GeneralConsants.SINGLE_QUOTE);
		} catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		} 
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		request.setAttribute(ParameterConstants.PROJECT_ID, 
					Integer.parseInt(request.getParameter(ParameterConstants.ID)));
		request.getRequestDispatcher(URLConstants.ADD_BUILD_JSP).
					forward(request, response);
	}

}
