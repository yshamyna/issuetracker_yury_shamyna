package org.training.issuetracker.web.actions.interfaces;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IWebAction {
	public String execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException;
}
