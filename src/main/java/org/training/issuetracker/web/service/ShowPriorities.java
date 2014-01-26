package org.training.issuetracker.web.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.IssuePriority;
import org.training.issuetracker.db.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.db.dao.service.PriorityDAO;
import org.training.issuetracker.web.interfaces.IAction;

public class ShowPriorities implements IAction {

	@Override
	public String perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			IPriorityDAO priorityDAO = new PriorityDAO();
			List<IssuePriority> priorities = priorityDAO.getAll();
			request.setAttribute("priorities", priorities);
			return "/priorities.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
