package org.training.issuetracker.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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
import org.training.issuetracker.servlets.enums.Role;
import org.training.issuetracker.servlets.service.HTMLPage;
import org.training.issuetracker.servlets.service.constants.GeneralConstants;
import org.training.issuetracker.servlets.service.constants.MessagesConstants;
import org.training.issuetracker.servlets.service.constants.RequestConstants;
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
public class EditIssue extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig servletConfig;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditIssue() {
        super();
    }
    
    @Override
	public void init(ServletConfig config) throws ServletException {
		servletConfig = config;
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
		User user = (User) session.getAttribute(RequestConstants.USER_ATTRIBUTE);
		PrintWriter out = response.getWriter();
		try {
			Parser.setURL(servletConfig.getServletContext().
					getResource(GeneralConstants.XML_RESOURCE));
			Role role;
			if (user == null) {
				role = Role.GUEST;
			} else {
				role = Role.valueOf(user.getRole().getValue().toUpperCase());
			}
			IIssueDAO iDAO = new IssueDAO();
			int issueId = Integer.parseInt(request.
					getParameter(RequestConstants.ID_PARAMETER));
			Issue issue = iDAO.getById(issueId);
			ILink link = null;
			IMenu menu = null;
			IContent content = null;
			switch (role) {
				case GUEST:
					link = new GuestLink();
					String message = (String) request.
							getAttribute(RequestConstants.ERROR_MSG_ATTRIBUTE);
					menu = new GuestMenu(message);
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
			out.println(HTMLPage.getHTML(link, menu, content));
		} catch (Exception e) {
			out.println(MessagesConstants.READ_ERROR_FROM_XML_FILE);
		}
	}

}
