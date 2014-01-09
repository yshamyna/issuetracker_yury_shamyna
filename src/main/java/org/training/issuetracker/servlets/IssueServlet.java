package org.training.issuetracker.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.beans.User;
import org.training.issuetracker.dao.interfaces.IIssueDAO;
import org.training.issuetracker.dao.xml.parsers.Parser;
import org.training.issuetracker.dao.xml.service.IssueDAO;
import org.training.issuetracker.servlets.service.AdminIssuePage;
import org.training.issuetracker.servlets.service.GuestIssuePage;
import org.training.issuetracker.servlets.service.Page;
import org.training.issuetracker.servlets.service.UserIssuePage;

/**
 * Servlet implementation class IssueServlet
 */
public class IssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig servletConfig;
	
	private static enum Role {
		ADMINISTRATOR, USER, GUEST;
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IssueServlet() {
        super();
    }
    
    @Override
	public void init(ServletConfig config) throws ServletException {
		servletConfig = config;
		try {
			Parser.setURL(config.getServletContext().getResource("issuetracker.xml"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		task(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		task(request, response);
	}
	
	private void task(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		try {
			Parser.setURL(servletConfig.getServletContext().getResource("issuetracker.xml"));
			Role role;
			if (user == null) {
				role = Role.GUEST;
			} else {
				role = Role.valueOf(user.getRole().getValue().toUpperCase());
			}
			IIssueDAO iDAO = new IssueDAO();
			int issueId = Integer.parseInt(request.getParameter("id"));
			Issue issue = iDAO.getById(issueId);
			Page page = null;
			switch (role) {
				case GUEST:
					page = new GuestIssuePage(issue);
					break;
				case USER:
					page = new UserIssuePage(user, issue);
					break;
				case ADMINISTRATOR:
					page = new AdminIssuePage(user, issue);
					break;
			}
			PrintWriter out = response.getWriter();
			out.println(page.getPage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
