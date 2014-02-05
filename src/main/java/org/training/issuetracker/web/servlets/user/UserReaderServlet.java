package org.training.issuetracker.web.servlets.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.interfaces.IUserDAO;
import org.training.issuetracker.db.dao.service.UserDAO;

/**
 * Servlet implementation class UserReaderServlet
 */
public class UserReaderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserReaderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			IUserDAO userDAO = new UserDAO();
			List<User> users = userDAO.getAll();
			request.setAttribute("users", users);
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			getServletContext().getRequestDispatcher("/users.jsp").
				forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
