package org.training.issuetracker.web.servlets.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.service.RoleService;
import org.training.issuetracker.db.service.UserService;

/**
 * Servlet implementation class EditUserServlet
 */
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null) {
			getServletContext().getRequestDispatcher("/users").
					forward(request, response);
		} else {
			try {
				User user = (User) request.getSession().getAttribute("user");
				
				long userId = Integer.parseInt(id);
				UserService uService = new UserService(user);
				User usr = uService.getUserById(userId);
				
				if (usr == null) {
					getServletContext().getRequestDispatcher("/users").
							forward(request, response);
				} else {
					request.setAttribute("usr", usr);
					
					RoleService rService = new RoleService(user);
					List<UserRole> roles = rService.getRoles();
					request.setAttribute("roles", roles);
					
					getServletContext().getRequestDispatcher("/editUser.jsp").
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher("/users").
					forward(request, response);
			} catch(Exception e) {
				response.getWriter().println("Sorry, but current service is not available... Please try later.");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			User u = new User();
			u.setId(Long.parseLong(request.getParameter("id")));
			u.setFirstName(request.getParameter("firstName"));
			u.setLastName(request.getParameter("lastName"));
			u.setEmailAddress(request.getParameter("email"));
			
			UserRole role = new UserRole();
			role.setId(Integer.parseInt(request.getParameter("roleId")));
			u.setRole(role);
			
			UserService service = new UserService(user);
			service.update(user);
			
			response.getWriter().println("User data was updated successfully.");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
