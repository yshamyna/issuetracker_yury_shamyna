package org.training.issuetracker.web.servlets.user;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.beans.Issue;
import org.training.issuetracker.db.beans.IssuePriority;
import org.training.issuetracker.db.beans.IssueStatus;
import org.training.issuetracker.db.beans.IssueType;
import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.dao.interfaces.IIssueDAO;
import org.training.issuetracker.db.dao.interfaces.IRoleDAO;
import org.training.issuetracker.db.dao.interfaces.IUserDAO;
import org.training.issuetracker.db.dao.service.IssueDAO;
import org.training.issuetracker.db.dao.service.RoleDAO;
import org.training.issuetracker.db.dao.service.UserDAO;

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
        // TODO Auto-generated constructor stub
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
				long userId = Integer.parseInt(id);
				IUserDAO dao = new UserDAO();
				User user = dao.getById(userId);
				if (user == null) {
					getServletContext().getRequestDispatcher("/users").
							forward(request, response);
				} else {
					request.setAttribute("usr", user);
					IRoleDAO roleDAO = new RoleDAO();
					List<UserRole> roles = roleDAO.getAll();
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
			long id = Integer.parseInt(request.getParameter("id"));
			
			response.getWriter().println("User data was changed successfully.");
		} catch(Exception e) {
			response.getWriter().println("Sorry, but current service is not available... Please try later.");
		}
	}

}
