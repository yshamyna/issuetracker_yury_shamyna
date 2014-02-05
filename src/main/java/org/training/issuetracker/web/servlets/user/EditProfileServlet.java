package org.training.issuetracker.web.servlets.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.dao.interfaces.IRoleDAO;
import org.training.issuetracker.db.dao.interfaces.IUserDAO;
import org.training.issuetracker.db.dao.service.RoleDAO;
import org.training.issuetracker.db.dao.service.UserDAO;

/**
 * Servlet implementation class EditProfileServlet
 */
public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProfileServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("user", user);
		
		if ("administrator".equals(user.getRole().getValue())) {
			try {
				IRoleDAO roleDAO = new RoleDAO();
				List<UserRole> roles = roleDAO.getAll();
				request.setAttribute("roles", roles);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		getServletContext().getRequestDispatcher("/editProfile.jsp").
			forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			String firstName = request.getParameter("fName");
			String lastName = request.getParameter("lName");
			String email = request.getParameter("email");
			String role = request.getParameter("roleId");
			
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmailAddress(email);
			if (role != null) {
				IRoleDAO roleDAO = new RoleDAO();
				UserRole userRole = roleDAO.getById(Integer.parseInt(role));
				user.setRole(userRole);
			}
			IUserDAO userDAO = new UserDAO();
			userDAO.update(user);
			response.getWriter().println("Profile was changed successfully.");
		} catch(Exception e) {
			response.getWriter().println("Sorry, but current service is not available... Please try later.");
		}
	}

}
