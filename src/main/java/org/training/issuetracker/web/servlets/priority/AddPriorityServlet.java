package org.training.issuetracker.web.servlets.priority;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.Priority;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.PriorityService;

public class AddPriorityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddPriorityServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/addPriority.jsp").
			forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			Priority priority  = new Priority();
			priority.setName(request.getParameter("name"));
			
			PriorityService service = new PriorityService(user);
			service.add(priority);
			
			response.getWriter().println("Issue priority was added successfully.");
		} catch (JdbcSQLException e) {
			response.getWriter().println("Already exists value '" 
						+ request.getParameter("name") + "'");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
