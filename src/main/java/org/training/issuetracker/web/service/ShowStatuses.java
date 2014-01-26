package org.training.issuetracker.web.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.IssueStatus;
import org.training.issuetracker.db.dao.interfaces.IStatusDAO;
import org.training.issuetracker.db.dao.service.StatusDAO;
import org.training.issuetracker.web.interfaces.IAction;

public class ShowStatuses implements IAction {

	@Override
	public String perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			IStatusDAO statusDAO = new StatusDAO();
			List<IssueStatus> statuses = statusDAO.getAll();
			request.setAttribute("statuses", statuses);
			return "/statuses.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
