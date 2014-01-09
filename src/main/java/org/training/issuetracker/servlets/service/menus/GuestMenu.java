package org.training.issuetracker.servlets.service.menus;

import org.training.issuetracker.servlets.service.intefaces.IMenu;

public class GuestMenu implements IMenu {
	private String msg;
	private String currentServlet;
	
	public GuestMenu(String message, String currServlet) {
		msg = message;
		currentServlet = currServlet;
	}

	@Override
	public StringBuilder getValue() {
		StringBuilder form = new StringBuilder();
		form.append("<form method=\"post\" action=\"" + currentServlet + "?action=login\">");
		form.append("Email address: <input type=\"text\" name=\"emailAddress\"/>&nbsp;");
		form.append("Password: <input type=\"password\" name=\"password\"/>");
		form.append("<input type=\"submit\" name=\"loginBtn\" value=\"Login\"/>");
		form.append("</form>");
		if (msg != null) {
			form.append("<div style=\"font-size:10pt;color:red;\">" + msg + "</div>");
		}
		form.append("<div style=\"float:left;height:26px;\"><form action=\"dashboard?actionsearch_issue\">");
		form.append("<input type=\"submit\" name=\"searchIssue\" value=\"Search\"/>");
		form.append("</form></div>");
		return form;
	}

}
