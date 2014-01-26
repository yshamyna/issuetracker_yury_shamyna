package org.training.issuetracker.web.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.dao.interfaces.IProjectDAO;
import org.training.issuetracker.db.dao.service.ProjectDAO;
import org.training.issuetracker.web.interfaces.IAction;

public class ShowProjects implements IAction {

	@Override
	public String perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			IProjectDAO projectDAO = new ProjectDAO();
			List<Project> projects = projectDAO.getAll();
			request.setAttribute("projects", projects);
			return "/projects.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
