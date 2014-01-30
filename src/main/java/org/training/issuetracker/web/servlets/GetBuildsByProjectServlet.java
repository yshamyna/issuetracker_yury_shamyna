package org.training.issuetracker.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.dao.interfaces.IBuildDAO;
import org.training.issuetracker.db.dao.service.BuildDAO;

/**
 * Servlet implementation class GetBuildsByProjectServlet
 */
public class GetBuildsByProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBuildsByProjectServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strId = request.getParameter("id");
		if (strId != null) {
			long id = Integer.parseInt(strId);
			//response.getWriter().println(id * 10);
			IBuildDAO buildDAO = new BuildDAO();
			try {
				List<Build> builds = buildDAO.getByProjectId(id);
				StringBuilder responseText = new StringBuilder("{\"builds\":[");
				// {"builds":[{"id":1, "version":"1.2"},{"id":2, "version":"1.2"}]}
				for (Build build : builds) {
					responseText.append("{\"id\":" + build.getId() + ",\"version\":\"" + build.getVersion() + "\"},");
				}
				responseText.deleteCharAt(responseText.length() - 1);
				responseText.append("]}");
				response.getWriter().println(responseText);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			response.getWriter().println(-1);
		}
	}

}
