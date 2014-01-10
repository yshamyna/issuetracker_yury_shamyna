package org.training.issuetracker.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.beans.User;
import org.training.issuetracker.dao.xml.parsers.Parser;
import org.training.issuetracker.servlets.enums.Role;
import org.training.issuetracker.servlets.service.HTMLPage;
import org.training.issuetracker.servlets.service.constants.GeneralConstants;
import org.training.issuetracker.servlets.service.constants.RequestConstants;
import org.training.issuetracker.servlets.service.contents.DashboardContent;
import org.training.issuetracker.servlets.service.intefaces.IContent;
import org.training.issuetracker.servlets.service.intefaces.ILink;
import org.training.issuetracker.servlets.service.intefaces.IMenu;
import org.training.issuetracker.servlets.service.links.AdminLink;
import org.training.issuetracker.servlets.service.links.GuestLink;
import org.training.issuetracker.servlets.service.links.UserLink;
import org.training.issuetracker.servlets.service.menus.AdminMenu;
import org.training.issuetracker.servlets.service.menus.GuestMenu;
import org.training.issuetracker.servlets.service.menus.UserMenu;

public class Dashboard implements Servlet {
	
	private ServletConfig servletConfig;

	@Override
	public void destroy() {
	}

	@Override
	public ServletConfig getServletConfig() {
		return servletConfig;
	}

	@Override
	public String getServletInfo() {
		return null;
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		servletConfig = config;
		try {
			Parser.setURL(config.getServletContext().
					getResource(GeneralConstants.XML_RESOURCE));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute(RequestConstants.USER_ATTRIBUTE);
		Role role;
		if (user == null) {
			role = Role.GUEST;
		} else {
			role = Role.valueOf(user.getRole().getValue().toUpperCase());
		}
		int	currentPage = (int) request.
				getAttribute(RequestConstants.CURRENT_PAGE_ATTRIBUTE);
		int allPages = (int) request.
				getAttribute(RequestConstants.ALL_PAGES_ATTRIBUTE);
		List<Issue> issues = (List<Issue>) request.
				getAttribute(RequestConstants.ISSUES_ATTRIBUTE);
		ILink link = null;
		IMenu menu = null;
		IContent content = null;
		switch (role) {
			case GUEST:
				link = new GuestLink();
				String message = (String) request.
						getAttribute(RequestConstants.ERROR_MSG_ATTRIBUTE);
				menu = new GuestMenu(message);
				break;
			case USER:
				link = new UserLink();
				menu = new UserMenu(user);
				break;
			case ADMINISTRATOR:
				link = new AdminLink();
				menu = new AdminMenu(user);
				break;
		}
		content = new DashboardContent(issues, currentPage, allPages);
		PrintWriter out = response.getWriter();
		out.println(HTMLPage.getHTML(link, menu, content));
	}
}
