package org.training.issuetracker.db.service.actions;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Comment;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.interfaces.Action;
import org.training.issuetracker.db.service.CommentService;

public class AddCommentAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			Comment comment = new Comment();
			comment.setComment(request.getParameter("comment"));
			comment.setSender(user);
			Timestamp createDate = new Timestamp(System.currentTimeMillis());
			comment.setCreateDate(createDate);
			comment.setIssueId(Integer.parseInt(request.getParameter("issueId")));
			
			CommentService service = new CommentService(user);
			service.add(comment);
			
			String jsonData = "{\"data\":{\"addedBy\":\"" 
								+ user.getFirstName() + " " + user.getLastName() + "\", "
								+ "\"addDate\":\"" + createDate + "\", " 
								+  "\"comment\":\"" + request.getParameter("comment") + "\"}}";
			
			response.getWriter().println(jsonData);
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
