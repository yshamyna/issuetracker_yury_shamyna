package org.training.issuetracker.servlets.service.links;

import org.training.issuetracker.servlets.service.intefaces.ILink;

public class GuestLink implements ILink {

	@Override
	public StringBuilder getValue() {
		return new StringBuilder("");
	}

}
