package org.training.issuetracker.web.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.web.constants.URLConstants;
import org.training.issuetracker.web.constants.ParameterConstants;

public class UserAndGuestAccessDenied implements Filter {

    public UserAndGuestAccessDenied() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute(ParameterConstants.USER);
		if (user == null) {
			request.getRequestDispatcher(URLConstants.DASHBOARD_JSP).
						forward(request, response);
			return;
		}
		Role role = Role.valueOf(user.getRole().getName().toUpperCase());
		if (Role.USER == role) {
			request.getRequestDispatcher(URLConstants.DASHBOARD_JSP).
						forward(request, response);
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
