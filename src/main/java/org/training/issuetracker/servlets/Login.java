package org.training.issuetracker.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.training.issuetracker.beans.User;
import org.training.issuetracker.dao.interfaces.IUserDAO;
import org.training.issuetracker.dao.xml.service.UserDAO;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	private void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("emailAddress");
		if (login == null) {
			//htmlWriter.append("Login is not entered.");
			request.setAttribute("errorMessage", "Login is not entered.");
			RequestDispatcher dispatcher = getServletContext().
					getRequestDispatcher("/dashboard");
			dispatcher.forward(request, response);
			return;
		}
		String password = request.getParameter("password");
		if (password == null) {
			//htmlWriter.append("Password is not entered.");
			request.setAttribute("errorMessage", "Password is not entered.");
			RequestDispatcher dispatcher = getServletContext().
					getRequestDispatcher("/dashboard");
			dispatcher.forward(request, response);
			return;
		}
		login = login.trim();
		password = password.trim();
		IUserDAO userDAO = new UserDAO();
		boolean isRegistered = false;
		try {
			List<User> users = userDAO.getAll();
			for (User user : users) {
				if (user.getEmailAddress().equals(login) && user.getPassword().equals(password)) {
					isRegistered = true;
					HttpSession session = request.getSession();
					session.setAttribute("user", user);
					break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isRegistered) {
			PrintWriter htmlWriter = response.getWriter();
			htmlWriter.append("<html><body>");
			htmlWriter.println("<div>Hello " + login + "</div>");
			htmlWriter.println("<div><a href=\"dashboard\">Go to startpage</a></div>");
			htmlWriter.append("</body></html>");
		} else {
			//htmlWriter.append("The login or password you entered is incorrect.");
			request.setAttribute("errorMessage", "You are not authorized.");
			RequestDispatcher dispatcher = getServletContext().
					getRequestDispatcher("/dashboard");
			dispatcher.forward(request, response);
		}
		
	}
}
