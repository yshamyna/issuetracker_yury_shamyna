package org.training.issuetracker.web.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.web.actions.interfaces.IWebAction;

public class ShowPrioritiesWebAction implements IWebAction {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/issuetracker/priorities");
		return null;
	}

}
