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

/**
 * Servlet implementation class Dashboard
 */
public class IssuesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IssuesServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
	}

	private void performTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			IssueService service = new IssueService(user);
			List<Issue> issues = null;
			
			if (user == null) {
				issues = service.getLastNIssues(10);
			} else {
				String page = request.getParameter("page");
				
				long pageNumber = 1;
				if (page != null) {
					pageNumber = Integer.parseInt(page);
				}
				issues = service.getIssues(user, pageNumber, 10);
				
				long maxPage = service.getQuantityPages(user.getId(), 10);
				
				pageNumber = pageNumber > maxPage ? maxPage : pageNumber;
				pageNumber = pageNumber < 1 ? 1 : pageNumber;
				
				request.setAttribute("page", pageNumber);
				request.setAttribute("maxPage", maxPage);
			}
			
			request.setAttribute("issues", issues);
			getServletContext().getRequestDispatcher("/dashboard.jsp").
					forward(request, response);	
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}
	
}
