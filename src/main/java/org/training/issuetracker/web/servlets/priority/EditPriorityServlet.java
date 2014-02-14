package org.training.issuetracker.web.servlets.priority;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Priority;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.PriorityService;

public class EditPriorityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditPriorityServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null) {
			getServletContext().getRequestDispatcher("/priorities").
					forward(request, response);
		} else {
			try {
				User user = (User) request.getSession().getAttribute("user");
				
				long priorityId = Integer.parseInt(id);
				
				PriorityService service = new PriorityService(user);
				Priority priority = service.getPriorityById(priorityId);
				if (priority == null) {
					getServletContext().getRequestDispatcher("/priorities").
							forward(request, response);
				} else {
					request.setAttribute("priority", priority);
					getServletContext().getRequestDispatcher("/editPriority.jsp").
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher("/priorities").
					forward(request, response);
			} catch(Exception e) {
				response.getWriter().println("Sorry, but current service is not available... Please try later.");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			Priority priority  = new Priority();
			priority.setId(Long.parseLong(request.getParameter("id")));
			priority.setName(request.getParameter("name"));
			
			PriorityService service = new PriorityService(user);
			service.update(priority);
			
			response.getWriter().println("Issue priority was updated successfully.");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
