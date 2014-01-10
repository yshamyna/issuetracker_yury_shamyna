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
import org.training.issuetracker.servlets.service.constants.MessagesConstants;
import org.training.issuetracker.servlets.service.constants.RequestConstants;

public class Authorization {
	
	public static void login(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request; 
		String login = request.getParameter(RequestConstants.EMAIL_ADDRESS_PARAMETER);
		String password = request.getParameter(RequestConstants.PASSWORD_PARAMETER);
		String message = checkLoginAndPassword(login, password);
		if (message != null) {
			request.setAttribute(RequestConstants.ERROR_MSG_ATTRIBUTE, message);
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
					session.setAttribute(RequestConstants.USER_ATTRIBUTE, user);
					break;
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		if (!isRegistered) {
			request.setAttribute(RequestConstants.ERROR_MSG_ATTRIBUTE, 
					MessagesConstants.YOU_ARE_NOT_AUTHORIZED);
		}
	}
	
	private static String checkLoginAndPassword(String login, String password) {
		if (login == null) {
			return MessagesConstants.LOGIN_IS_NOT_ENTERED;
		}
		if (password == null) {
			return MessagesConstants.PASSWORD_IS_NOT_ENTERED;
		}
		login = login.trim();
		if (login.isEmpty()) {
			return MessagesConstants.LOGIN_IS_EMPTY;
		}
		password = password.trim();
		if (password.isEmpty()) {
			return MessagesConstants.PASSWORD_IS_EMPTY;
		}
		return null;
	}
	
	public static void logout(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request; 
		req.getSession().removeAttribute(RequestConstants.USER_ATTRIBUTE);
		HttpServletResponse res = (HttpServletResponse) response;
		res.sendRedirect(RequestConstants.REDIRECT_TO_DASHBOARD);
	}
}
