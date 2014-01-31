package org.training.issuetracker.web.servlets.type;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.IssueType;
import org.training.issuetracker.db.dao.interfaces.ITypeDAO;
import org.training.issuetracker.db.dao.service.TypeDAO;

/**
 * Servlet implementation class EditTypeServlet
 */
public class EditTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditTypeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id != null) {
			try {
				long typeId = Integer.parseInt(id);
				ITypeDAO typeDAO = new TypeDAO();
				IssueType type = typeDAO.getById(typeId);
				request.setAttribute("type", type);
			} catch (RuntimeException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				getServletContext().getRequestDispatcher("/editType.jsp").
						forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String id = request.getParameter("id");
			String name = request.getParameter("entityName");
			IssueType type = new IssueType();
			type.setId(Integer.parseInt(id));
			type.setValue(name);	
			ITypeDAO typeDAO = new TypeDAO();
			typeDAO.updateName(type);
			//request.setAttribute("errMsg", "Issue type was updated successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//doGet(request, response);
			response.getWriter().println("Issue type was updated successfully.");
		}
	}

}
