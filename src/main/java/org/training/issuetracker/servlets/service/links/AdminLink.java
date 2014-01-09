package org.training.issuetracker.servlets.service.links;

import org.training.issuetracker.servlets.service.intefaces.ILink;

public class AdminLink implements ILink {

	@Override
	public StringBuilder getValue() {
		return new StringBuilder("<link rel=\"stylesheet\" type=\"text/css\" href=\"menu.css\">");
	}

}
