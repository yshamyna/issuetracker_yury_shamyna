package org.training.issuetracker.servlets.service.menus;

import org.training.issuetracker.beans.User;
import org.training.issuetracker.servlets.service.intefaces.IMenu;

public class AdminMenu implements IMenu {
	private User user;
	
	public AdminMenu(User user) {
		super();
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
		menu.append("<li><a href=\"\">Search issue</a></li>");
		menu.append("<li><a href=\"\">Submit issue</a></li>");
		menu.append("<li><a href=\"\">Review</a>");
		menu.append("<ul>");	
		menu.append("<li><a href=\"\">Projects</a></li>");
		menu.append("<li><a href=\"\">Statuses</a></li>");		
		menu.append("<li><a href=\"\">Resolutions</a></li>");			
		menu.append("<li><a href=\"\">Priorities</a></li>");			
		menu.append("<li><a href=\"\">Types</a></li>");
		menu.append("</ul></li>");
		menu.append("<li><a href=\"\">Search user</a></li>");
		menu.append("<li><a href=\"\">Add</a>");
		menu.append("<ul>");
		menu.append("<li><a href=\"\">Project</a></li>");
		menu.append("<li><a href=\"\">User</a></li>");		
		menu.append("<li><a href=\"\">Resolution</a></li>");		
		menu.append("<li><a href=\"\">Priority</a></li>");			
		menu.append("<li><a href=\"\">Type</a></li>");			
		menu.append("</ul>");			
		menu.append("</li>");			
		menu.append("<li><a href=\"dashboard?action=logout\">Logout</a></li></ul>");
		return menu;
	}

}
