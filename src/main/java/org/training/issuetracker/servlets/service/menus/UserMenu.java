package org.training.issuetracker.servlets.service.menus;

import org.training.issuetracker.beans.User;
import org.training.issuetracker.servlets.service.intefaces.IMenu;

public class UserMenu implements IMenu {
	private User user;
	
	public UserMenu(User user) {
		this.user = user;
	}

	@Override
	public StringBuilder getValue() {
		StringBuilder menu = new StringBuilder();
		menu.append("<div class=\"color-menu\"><div>User: " 
				+ user.getFirstName() + " " + user.getLastName() 
				+ "</div></div>");
		menu.append("<ul class=\"color-menu\">");
		menu.append("<li><a href=\"\">Edit</a></li>");
		menu.append("<li><a href=\"\">Search issue</a></li>")	;
		menu.append("<li><a href=\"\">Submit issue</a></li>");
		menu.append("<li><a href=\"dashboard?action=logout\">Logout</a></li></ul>");
		return menu;
	}

}
