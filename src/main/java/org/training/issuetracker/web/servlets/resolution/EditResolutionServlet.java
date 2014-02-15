package org.training.issuetracker.web.servlets.resolution;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Resolution;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.ResolutionService;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class EditResolutionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditResolutionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String id = request.getParameter(ParameterConstants.ID);
		if (id == null) {
			getServletContext().getRequestDispatcher(URLConstants.RESOLUTIONS_URL).
					forward(request, response);
		} else {
			try {
				User user = (User) request.getSession().
									getAttribute(ParameterConstants.USER);
				
				long resolutionId = Integer.parseInt(id);
				
				ResolutionService service = new ResolutionService(user);
				Resolution resolution = service.getResolutionById(resolutionId);
				
				if (resolution == null) {
					getServletContext().getRequestDispatcher(URLConstants.RESOLUTIONS_URL).
							forward(request, response);
				} else {
					request.setAttribute(ParameterConstants.RESOLUTION, resolution);
					getServletContext().getRequestDispatcher(URLConstants.EDIT_RESOLUTION_JSP).
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher(URLConstants.RESOLUTIONS_URL).
					forward(request, response);
			} catch(Exception e) {
				response.getWriter().println(MessageConstants.SORRY_MESSAGE);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute(ParameterConstants.USER);
			
			Resolution resolution = new Resolution();
			resolution.setId(Long.parseLong(request.getParameter(ParameterConstants.ID)));
			resolution.setName(request.getParameter(ParameterConstants.NAME));
			
			ResolutionService service = new ResolutionService(user);
			service.update(resolution);
			
			response.getWriter().println(MessageConstants.RESOLUTION_UPDATED);
		} catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
