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
import org.training.issuetracker.db.service.CommentService;

public class AddCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddCommentServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			Timestamp createDate = new Timestamp(System.currentTimeMillis());
			String S = new SimpleDateFormat("MM/dd/yyyy").format(createDate);
			
			Comment comment = new Comment();
			comment.setComment(request.getParameter("comment"));
			comment.setSender(user);
			comment.setCreateDate(createDate);
			comment.setIssueId(Long.parseLong(request.getParameter("issueId")));
			
			CommentService service = new CommentService(user);
			service.add(comment);
			
			// Convert comment to JSON data.
			String jsonData = "{\"data\":{\"addedBy\":\"" 
								+ user.getFirstName() + " " 
								+ user.getLastName() + "\", "
								+ "\"createDate\":\"" + S + "\", " 
								+  "\"comment\":\"" 
								+ request.getParameter("comment") + "\"}}";
			
			response.getWriter().println(jsonData);
		} catch (Exception e) {
			response.getWriter().println("");
		}
	}

}
