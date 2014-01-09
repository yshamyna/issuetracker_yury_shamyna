package org.training.issuetracker.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.beans.User;
import org.training.issuetracker.dao.interfaces.IIssueDAO;
import org.training.issuetracker.dao.interfaces.IUserDAO;
import org.training.issuetracker.dao.xml.service.IssueDAO;
import org.training.issuetracker.dao.xml.service.UserDAO;

/**
 * Servlet Filter implementation class DashboardFilter
 */
public class DashboardFilter implements Filter {
	
	private static enum Action {
		PREVIOUS_PAGE, NEXT_PAGE, LOGOUT, LOGIN;
	}

    /**
     * Default constructor. 
     */
    public DashboardFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			String action = request.getParameter("action");
			if (action != null) {
				Action actn = Action.valueOf(action.toUpperCase());
				switch (actn) {
					case LOGOUT:
						logout(request, response);
						break;
					case LOGIN:
						login(request, response);
						break;
					case NEXT_PAGE:
						nextPage(request, response);
						break;
					case PREVIOUS_PAGE:
						previousPage(request, response);
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			showIssue(request, response);
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}
	
//	private class IdAscComparator implements Comparator<Issue> {
//	    public int compare(Issue i1, Issue i2) {
//	    	return i1.getId() > i2.getId() ? 1 : i1.getId() < i2.getId() ? -1 : 0;
//	    }
//	}
//	
//	private class IdDescComparator implements Comparator<Issue> {
//	    public int compare(Issue i1, Issue i2) {
//	    	return i1.getId() < i2.getId() ? 1 : i1.getId() > i2.getId() ? -1 : 0;
//	    }
//	}
	
	private void logout(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request; 
		req.getSession().removeAttribute("user");
		HttpServletResponse res = (HttpServletResponse) response;
		res.sendRedirect("/issuetracker/dashboard");
	}
	
	private void login(ServletRequest request, ServletResponse response) throws Exception {
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
	
	private String checkLoginAndPassword(String login, String password) {
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
	
	private void nextPage(ServletRequest request, ServletResponse response) {
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		currentPage++;
		request.setAttribute("currentPage", currentPage);
	}
	
	private void previousPage(ServletRequest request, ServletResponse response) {
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		currentPage--;
		request.setAttribute("currentPage", currentPage);
	}
	
	private void showIssue(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("user");
		IIssueDAO issueDAO = new IssueDAO();
		try {
			List<Issue> allIssues;
			if (user == null) {
				allIssues = issueDAO.getAll();
			} else {
				allIssues = issueDAO.getAllByUserId(user.getId());
			}
			int length = allIssues.size();
			int currentPage = 1;
			if (request.getAttribute("currentPage") != null) {
				currentPage = (int) request.getAttribute("currentPage");
			}
//			if (request.getParameter("id_sort") != null) {
//				currentPage = Integer.parseInt(request.getParameter("currentPage"));
//			}
			if (currentPage < 1) {
				currentPage = 1;
			} else if (currentPage > length/10 + 1) {
				currentPage = length/10 + 1;
			}
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("allPages", length/10 + 1);
			int endPosition = (currentPage * 10) % allIssues.size();
			if (endPosition == 0) {
				endPosition = (currentPage * 10) - allIssues.size();
			}
			if (currentPage * 10 > allIssues.size()) {
				endPosition = currentPage * 10 - endPosition;
			} else {
				endPosition = currentPage * 10;
			}
			List<Issue> issues = allIssues.subList((currentPage - 1) * 10, 
					endPosition);
//			if ("asc".equals(request.getParameter("id_sort"))) {
//				Collections.sort(issues, new IdAscComparator());
//			} else if ("desc".equals(request.getParameter("id_sort"))) {
//				Collections.sort(issues, new IdDescComparator());
//			}
			request.setAttribute("issues", issues);
		} catch (Exception e) {
			request.setAttribute("issues", null);
		}
	}
}
