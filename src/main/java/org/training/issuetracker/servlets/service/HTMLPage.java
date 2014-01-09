package org.training.issuetracker.servlets.service;

import org.training.issuetracker.servlets.service.intefaces.IContent;
import org.training.issuetracker.servlets.service.intefaces.ILink;
import org.training.issuetracker.servlets.service.intefaces.IMenu;

public class HTMLPage {
	
	public static StringBuilder getHTML(ILink link, IMenu menu, IContent content) {
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE html>");
		html.append("<html style=\"font-family:arial;\">");
		html.append("<title>Issue tracker</title>");
		html.append(link.getValue());
		html.append("<body>");
		html.append("<table border=\"1\" style=\"width:100%\">");
		html.append("<tr><td>");
		html.append(menu.getValue());
		html.append("</td></tr><tr><td>");
		html.append(content.getValue());
		html.append("</td><tr></body></html>");
		return html;
	}
}
