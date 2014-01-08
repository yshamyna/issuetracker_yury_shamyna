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
import org.training.issuetracker.servlets.service.AdminPage;
import org.training.issuetracker.servlets.service.GuestPage;
import org.training.issuetracker.servlets.service.Page;
import org.training.issuetracker.servlets.service.UserPage;

public class Dashboard implements Servlet {
	
	private static enum Role {
		ADMINISTRATOR, USER, GUEST;
	}
	
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
			Parser.setURL(config.getServletContext().getResource("issuetracker.xml"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("user");
		Role role;
		if (user == null) {
			role = Role.GUEST;
		} else {
			role = Role.valueOf(user.getRole().getValue().toUpperCase());
		}
		int currentPage = (int) request.getAttribute("currentPage");
		int allPages = (int) request.getAttribute("allPages");
		List<Issue> issues = (List<Issue>) request.getAttribute("issues");
		Page page = null;
		switch (role) {
			case GUEST:
				page = new GuestPage(issues, currentPage, allPages);
				break;
			case USER:
				page = new UserPage(user, issues, currentPage, allPages);
				break;
			case ADMINISTRATOR:
				page = new AdminPage(user, issues, currentPage, allPages);
				break;
		}
		PrintWriter out = response.getWriter();
		out.println(page.getPage());
	}
}
