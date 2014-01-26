package org.training.issuetracker.web.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.IssueResolution;
import org.training.issuetracker.db.dao.interfaces.IResolutionDAO;
import org.training.issuetracker.db.dao.service.ResolutionDAO;
import org.training.issuetracker.web.interfaces.IAction;

public class ShowResolutions implements IAction {

	@Override
	public String perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			IResolutionDAO resolutionDAO = new ResolutionDAO();
			List<IssueResolution> resolutions = resolutionDAO.getAll();
			request.setAttribute("resolutions", resolutions);
			return "/resolutions.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
