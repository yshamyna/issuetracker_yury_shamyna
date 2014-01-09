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
import org.training.issuetracker.servlets.service.HTMLPage;
import org.training.issuetracker.servlets.service.contents.GuestReviewIssue;
import org.training.issuetracker.servlets.service.contents.UserReviewIssue;
import org.training.issuetracker.servlets.service.intefaces.IContent;
import org.training.issuetracker.servlets.service.intefaces.ILink;
import org.training.issuetracker.servlets.service.intefaces.IMenu;
import org.training.issuetracker.servlets.service.links.AdminLink;
import org.training.issuetracker.servlets.service.links.GuestLink;
import org.training.issuetracker.servlets.service.links.UserLink;
import org.training.issuetracker.servlets.service.menus.AdminMenu;
import org.training.issuetracker.servlets.service.menus.GuestMenu;
import org.training.issuetracker.servlets.service.menus.UserMenu;

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
			ILink link = null;
			IMenu menu = null;
			IContent content = null;
			switch (role) {
				case GUEST:
					link = new GuestLink();
					String message = (String) request.getAttribute("errorMessage");
					String servletName = servletConfig.getServletName().toLowerCase();
					menu = new GuestMenu(message, servletName);
					content = new GuestReviewIssue(issue);
					break;
				case USER:
					link = new UserLink();
					menu = new UserMenu(user);
					content = new UserReviewIssue(issue);
					break;
				case ADMINISTRATOR:
					link = new AdminLink();
					menu = new AdminMenu(user);
					content = new UserReviewIssue(issue);
					break;
			}
			PrintWriter out = response.getWriter();
			out.println(HTMLPage.getHTML(link, menu, content));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
