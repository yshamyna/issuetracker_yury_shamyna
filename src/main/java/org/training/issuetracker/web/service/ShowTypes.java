package org.training.issuetracker.web.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.IssueType;
import org.training.issuetracker.db.dao.interfaces.ITypeDAO;
import org.training.issuetracker.db.dao.service.TypeDAO;
import org.training.issuetracker.web.interfaces.IAction;

public class ShowTypes implements IAction {

	@Override
	public String perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			ITypeDAO typeDAO = new TypeDAO();
			List<IssueType> types = typeDAO.getAll();
			request.setAttribute("types", types);
			return "/types.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
