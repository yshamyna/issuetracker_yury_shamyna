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

    /**
     * Default constructor. 
     */
    public DashboardFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			if (request.getParameter("previousPage") != null) {
				int currentPage = Integer.parseInt(request.getParameter("currentPage"));
				currentPage--;
				request.setAttribute("currentPage", currentPage);
				return;
			} else if (request.getParameter("nextPage") != null) {
				int currentPage = Integer.parseInt(request.getParameter("currentPage"));
				currentPage++;
				request.setAttribute("currentPage", currentPage);
				return;
			} else if (request.getParameter("action") != null) {
				if ("logout".equals(request.getParameter("action"))) {
					HttpServletRequest req = (HttpServletRequest) request; 
					req.getSession().removeAttribute("user");
					HttpServletResponse res = (HttpServletResponse) response;
					res.sendRedirect("/issuetracker/dashboard");
				}
			} else if (request.getParameter("searchIssue") != null) {
				System.out.println("Filter work.");	
			} else if (request.getParameter("logoutBtn") != null) {
				HttpServletRequest req = (HttpServletRequest) request; 
				req.getSession().removeAttribute("user");
				HttpServletResponse res = (HttpServletResponse) response;
				res.sendRedirect("/issuetracker/dashboard");
			} else if (request.getParameter("loginBtn") != null) {
				HttpServletRequest req = (HttpServletRequest) request; 
				String login = request.getParameter("emailAddress");
				if (login == null) {
					request.setAttribute("errorMessage", "Login is not entered.");
					return;
				}
				String password = request.getParameter("password");
				if (password == null) {
					request.setAttribute("errorMessage", "Password is not entered.");
					return;
				}
				login = login.trim();
				if (login.isEmpty()) {
					request.setAttribute("errorMessage", "Login is empty.");
					return;
				}
				password = password.trim();
				if (password.isEmpty()) {
					request.setAttribute("errorMessage", "Password is empty.");
					return;
				}
				IUserDAO userDAO = new UserDAO();
				boolean isRegistered = false;
				try {
					List<User> users = userDAO.getAll();
					for (User user : users) {
						if (user.getEmailAddress().equals(login) && user.getPassword().equals(password)) {
							isRegistered = true;
							HttpSession session = req.getSession();
							session.setAttribute("user", user);
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!isRegistered) {
					request.setAttribute("errorMessage", "You are not authorized.");
				}
			}
			request.setAttribute("currentPage", 1);
		} finally {
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
				int currentPage = (int) request.getAttribute("currentPage");
				if (request.getParameter("id_sort") != null) {
					currentPage = Integer.parseInt(request.getParameter("currentPage"));
				}
				
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
				if ("asc".equals(request.getParameter("id_sort"))) {
					Collections.sort(issues, new IdAscComparator());
				} else if ("desc".equals(request.getParameter("id_sort"))) {
					Collections.sort(issues, new IdDescComparator());
				}
				request.setAttribute("issues", issues);
			} catch (Exception e) {
				e.printStackTrace();
			}
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	private class IdAscComparator implements Comparator<Issue> {
	    public int compare(Issue i1, Issue i2) {
	    	return i1.getId() > i2.getId() ? 1 : i1.getId() < i2.getId() ? -1 : 0;
	    }
	}
	
	private class IdDescComparator implements Comparator<Issue> {
	    public int compare(Issue i1, Issue i2) {
	    	return i1.getId() < i2.getId() ? 1 : i1.getId() > i2.getId() ? -1 : 0;
	    }
	}

}
