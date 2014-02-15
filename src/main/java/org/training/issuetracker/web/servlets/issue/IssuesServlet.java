package org.training.issuetracker.web.servlets.issue;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Issue;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.IssueService;
import org.training.issuetracker.web.constants.GeneralConsants;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class IssuesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IssuesServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
	}

	private void performTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute(ParameterConstants.USER);
			
			IssueService service = new IssueService(user);
			List<Issue> issues = null;
			
			if (user == null) {
				issues = service.getLastNIssues(GeneralConsants.RECORDS_PER_PAGE);
			} else {
				String page = request.getParameter(ParameterConstants.PAGE);
				
				long pageNumber = 1;
				if (page != null) {
					pageNumber = Integer.parseInt(page);
				}
				issues = service.getIssues(user, pageNumber, GeneralConsants.RECORDS_PER_PAGE);
				
				service = new IssueService(user);
				long maxPage = service.getQuantityPages(user.getId(), 
											GeneralConsants.RECORDS_PER_PAGE);
				
				pageNumber = pageNumber > maxPage ? maxPage : pageNumber;
				pageNumber = pageNumber < 1 ? 1 : pageNumber;
				
				request.setAttribute(ParameterConstants.PAGE, pageNumber);
				request.setAttribute(ParameterConstants.MAX_PAGE, maxPage);
			}
			
			request.setAttribute(ParameterConstants.ISSUES, issues);
			getServletContext().getRequestDispatcher(URLConstants.DASHBOARD_JSP).
					forward(request, response);	
		} catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}
	
}
