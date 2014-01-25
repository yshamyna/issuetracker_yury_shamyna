package org.training.issuetracker.web.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.interfaces.IUserDAO;
import org.training.issuetracker.db.dao.service.UserDAO;
import org.training.issuetracker.web.interfaces.IAction;

public class Authorization implements IAction{
	
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
