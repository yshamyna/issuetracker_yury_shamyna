package org.training.issuetracker.web.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.web.actions.interfaces.WebAction;

public class AddProjectWebAction implements WebAction {

	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/issuetracker/projects/add");
	}

}
