package org.training.issuetracker.web.servlets.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.dao.interfaces.IRoleDAO;
import org.training.issuetracker.db.dao.interfaces.IUserDAO;
import org.training.issuetracker.db.dao.service.RoleDAO;
import org.training.issuetracker.db.dao.service.UserDAO;

/**
 * Servlet implementation class AddUserServlet
 */
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUserServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			IRoleDAO roleDAO = new RoleDAO();
			List<UserRole> roles = roleDAO.getAll();
			request.setAttribute("roles", roles);
			getServletContext().getRequestDispatcher("/addUser.jsp").
					forward(request, response);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = new User();
			user.setFirstName(request.getParameter("firstName"));
			user.setLastName(request.getParameter("lastName"));
			user.setEmailAddress(request.getParameter("email"));
			user.setPassword(request.getParameter("password"));
			UserRole role = new UserRole();
			role.setId(Integer.parseInt(request.getParameter("roles")));
			user.setFirstName(request.getParameter("firstName"));
			user.setRole(role);
			IUserDAO userDAO = new UserDAO();
			userDAO.add(user);
		} catch (JdbcSQLException e) {
			request.setAttribute("errMsg", "Already exists user with e-mail '" 
							+ request.getParameter("email") + "'");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			doGet(request, response);		
		}
	}

}
