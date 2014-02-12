package org.training.issuetracker.web.servlets.build;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.BuildService;

public class BuildsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BuildsServlet() {
        super();
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id != null) {
			try {
				User user = (User) request.getSession().getAttribute("user");
				
				long projectId = Long.parseLong(id);
				
				BuildService service = new BuildService(user);
				List<Build> builds = service.getBuildsByProjectId(projectId);
				
				// Convert builds to JSON data.
				StringBuilder jsonText = new StringBuilder("{\"builds\":[");
				for (Build build : builds) {
					jsonText.append("{\"id\":" + build.getId() 
							+ ",\"version\":\"" + build.getVersion() 
							+ "\"},");
				}
				jsonText.deleteCharAt(jsonText.length() - 1);
				jsonText.append("]}");
				
				response.getWriter().println(jsonText);
			} catch (Exception e) {
				response.getWriter().println("");
			}
		}
	}

}
