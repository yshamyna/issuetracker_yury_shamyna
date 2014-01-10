package org.training.issuetracker.servlets.service;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.training.issuetracker.beans.User;
import org.training.issuetracker.dao.interfaces.IUserDAO;
import org.training.issuetracker.dao.xml.service.UserDAO;

public class Authorization {
	
	public static void login(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request; 
		String login = request.getParameter("emailAddress");
		String password = request.getParameter("password");
		String message = checkLoginAndPassword(login, password);
		if (message != null) {
			request.setAttribute("errorMessage", message);
			return;
		}
		IUserDAO userDAO = new UserDAO();
		boolean isRegistered = false;
		try {
			List<User> users = userDAO.getAll();
			for (User user : users) {
				if (user.getEmailAddress().equals(login) 
						&& user.getPassword().equals(password)) {
					isRegistered = true;
					HttpSession session = req.getSession();
					session.setAttribute("user", user);
					break;
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		if (!isRegistered) {
			request.setAttribute("errorMessage", "You are not authorized.");
		}
	}
	
	private static String checkLoginAndPassword(String login, String password) {
		if (login == null) {
			return "Login is not entered.";
		}
		if (password == null) {
			return "Password is not entered.";
		}
		login = login.trim();
		if (login.isEmpty()) {
			return "Login is empty.";
		}
		password = password.trim();
		if (password.isEmpty()) {
			return "Password is empty.";
		}
		return null;
	}
	
	public static void logout(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request; 
		req.getSession().removeAttribute("user");
		HttpServletResponse res = (HttpServletResponse) response;
		res.sendRedirect("/issuetracker/dashboard");
	}
}
