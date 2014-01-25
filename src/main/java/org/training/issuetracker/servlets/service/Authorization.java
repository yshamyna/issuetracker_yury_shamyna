package org.training.issuetracker.servlets.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.training.issuetracker.beans.User;
import org.training.issuetracker.dao.db.service.UserDAO;
import org.training.issuetracker.dao.interfaces.IUserDAO;
import org.training.issuetracker.servlets.service.constants.RequestConstants;
import org.training.issuetracker.servlets.service.interfaces.IAction;

public class Authorization implements IAction{
	
//	public static void login(ServletRequest request, ServletResponse response) throws Exception {
//		HttpServletRequest req = (HttpServletRequest) request; 
//		String login = request.getParameter(RequestConstants.EMAIL_ADDRESS_PARAMETER);
//		String password = request.getParameter(RequestConstants.PASSWORD_PARAMETER);
//		String message = checkEmailAndPassword(login, password);
//		if (message != null) {
//			request.setAttribute(RequestConstants.ERROR_MSG_ATTRIBUTE, message);
//			return;
//		}
//		IUserDAO userDAO = new UserDAO();
//		boolean isRegistered = false;
//		try {
//			List<User> users = userDAO.getAll();
//			for (User user : users) {
//				if (user.getEmailAddress().equals(login) 
//						&& user.getPassword().equals(password)) {
//					isRegistered = true;
//					HttpSession session = req.getSession();
//					session.setAttribute(RequestConstants.USER_ATTRIBUTE, user);
//					break;
//				}
//			}
//		} catch (Exception e) {
//			throw new Exception(e);
//		}
//		if (!isRegistered) {
//			request.setAttribute(RequestConstants.ERROR_MSG_ATTRIBUTE, 
//					MessagesConstants.YOU_ARE_NOT_AUTHORIZED);
//		}
//	}
//	
//	private static String checkEmailAndPassword(String email, String password) {
//		if (email == null) {
//			return MessagesConstants.LOGIN_IS_NOT_ENTERED;
//		}
//		if (password == null) {
//			return MessagesConstants.PASSWORD_IS_NOT_ENTERED;
//		}
//		email = email.trim();
//		if (email.isEmpty()) {
//			return MessagesConstants.LOGIN_IS_EMPTY;
//		}
//		password = password.trim();
//		if (password.isEmpty()) {
//			return MessagesConstants.PASSWORD_IS_EMPTY;
//		}
//		return null;
//	}
	
	public static void logout(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request; 
		req.getSession().removeAttribute(RequestConstants.USER_ATTRIBUTE);
		HttpServletResponse res = (HttpServletResponse) response;
		res.sendRedirect(RequestConstants.REDIRECT_TO_DASHBOARD);
	}

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		IUserDAO userDAO = new UserDAO();
		try {
			User user = userDAO.getByEmailAndPassword(email, password);
			if (user == null) {
				request.setAttribute("errMsg", "Email or password is incorrect.");
				return "/dashboard.jsp";
			} 
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect("dashboard");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
