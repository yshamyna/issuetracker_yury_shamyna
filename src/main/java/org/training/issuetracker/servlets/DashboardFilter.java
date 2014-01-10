package org.training.issuetracker.servlets;

import java.io.IOException;
import java.util.Collections;
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
import org.training.issuetracker.servlets.service.Authorization;
import org.training.issuetracker.servlets.service.comparators.AssigneeIssueAscComparator;
import org.training.issuetracker.servlets.service.comparators.AssigneeIssueDescComparator;
import org.training.issuetracker.servlets.service.comparators.IdIssueAscComparator;
import org.training.issuetracker.servlets.service.comparators.IdIssueDescComparator;
import org.training.issuetracker.servlets.service.comparators.PriorityIssueAscComparator;
import org.training.issuetracker.servlets.service.comparators.PriorityIssueDescComparator;
import org.training.issuetracker.servlets.service.comparators.StatusIssueAscComparator;
import org.training.issuetracker.servlets.service.comparators.StatusIssueDescComparator;
import org.training.issuetracker.servlets.service.comparators.SummaryIssueAscComparator;
import org.training.issuetracker.servlets.service.comparators.SummaryIssueDescComparator;
import org.training.issuetracker.servlets.service.comparators.TypeIssueAscComparator;
import org.training.issuetracker.servlets.service.comparators.TypeIssueDescComparator;

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
						Authorization.logout(request, response);
						break;
					case LOGIN:
						Authorization.login(request, response);
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
			if (request.getParameter("column") != null) {
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
			issues = sort(request, response, issues);
			request.setAttribute("issues", issues);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("issues", null);
		}
	}
	
	private static enum Sort {
		ID_ASC, ID_DESC, PRIORITY_DESC, PRIORITY_ASC, 
		ASSIGNEE_ASC, ASSIGNEE_DESC, TYPE_ASC, TYPE_DESC,
		STATUS_ASC, STATUS_DESC, SUMMARY_ASC, SUMMARY_DESC;
	}
	
	private List<Issue> sort(ServletRequest request, 
			ServletResponse response, List<Issue> issues) {
		String sort = request.getParameter("column");
		if (sort != null) {
			Sort srt = Sort.valueOf(sort.toUpperCase());
			switch (srt) {
			case ID_ASC:
				Collections.sort(issues, new IdIssueAscComparator());
				break;
			case ID_DESC:
				Collections.sort(issues, new IdIssueDescComparator());
				break;
			case PRIORITY_ASC:
				Collections.sort(issues, new PriorityIssueAscComparator());
				break;
			case PRIORITY_DESC:
				Collections.sort(issues, new PriorityIssueDescComparator());
			break;
			case ASSIGNEE_ASC:
				Collections.sort(issues, new AssigneeIssueAscComparator());
				break;
			case ASSIGNEE_DESC:
				Collections.sort(issues, new AssigneeIssueDescComparator());
				break;
			case TYPE_ASC:
				Collections.sort(issues, new TypeIssueAscComparator());
				break;
			case TYPE_DESC:
				Collections.sort(issues, new TypeIssueDescComparator());
				break;
			case STATUS_ASC:
				Collections.sort(issues, new StatusIssueAscComparator());
				break;
			case STATUS_DESC:
				Collections.sort(issues, new StatusIssueDescComparator());
				break;
			case SUMMARY_ASC:
				Collections.sort(issues, new SummaryIssueAscComparator());
				break;
			case SUMMARY_DESC:
				Collections.sort(issues, new SummaryIssueDescComparator());
				break;
			}
		}
		return issues;
	}
}
