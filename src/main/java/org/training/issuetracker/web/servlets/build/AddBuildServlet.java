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

public class AddBuildServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddBuildServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Build build = new Build();
			
			build.setVersion(request.getParameter("version"));
			build.setProjectId(Integer.parseInt(request.getParameter("id")));
			build.setCurrent(false);
			
			User user = (User) request.getSession().getAttribute("user");
			
			BuildService service = new BuildService(user);
			service.add(build);
			
			response.getWriter().println("Build was successfully added.");
		} catch (JdbcSQLException e) {
			response.getWriter().println("Already exists build version '" 
					+ request.getParameter("version") + "'");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		request.setAttribute("projectId", 
					Integer.parseInt(request.getParameter("id")));
		request.getRequestDispatcher("/addBuild.jsp").forward(request, response);
	}

}
