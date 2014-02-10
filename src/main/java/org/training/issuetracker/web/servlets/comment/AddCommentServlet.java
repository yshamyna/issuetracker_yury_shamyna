package org.training.issuetracker.web.servlets.comment;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Comment;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.CommentDAO;

/**
 * Servlet implementation class AddCommentServlet
 */
public class AddCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCommentServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String text = request.getParameter("comment");
			String issueId = request.getParameter("issueId");
			User user = (User) request.getSession().getAttribute("user");
			Timestamp createDate = new Timestamp(System.currentTimeMillis());
			String S = new SimpleDateFormat("MM/dd/yyyy").format(createDate);
			
			Comment comment = new Comment();
			comment.setComment(text);
			comment.setSender(user);
			comment.setCreateDate(createDate);
			comment.setIssueId(Integer.parseInt(issueId));
			
			CommentDAO commentDAO = new CommentDAO();
			commentDAO.addComment(comment);
			
			// {"builds":[{"id":1, "version":"1.2"},{"id":2, "version":"1.2"}]}
			String jsonData = "{\"data\":{\"addedBy\":\"" 
								+ user.getFirstName() + " " + user.getLastName() + "\", "
								+ "\"createDate\":\"" + S + "\", " 
								+  "\"comment\":\"" + text + "\"}}";
			response.getWriter().println(jsonData);
//			getServletContext().getRequestDispatcher("/issues/edit?id=" + issueId).
//				forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
