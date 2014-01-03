package org.training.issuetracker.dao.xml.parsers;

import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.UserRole;
import org.training.issuetracker.dao.xml.constants.AttrsConstants;
import org.training.issuetracker.dao.xml.constants.TagConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RoleParser extends DefaultHandler {
	private List<UserRole> roles;
	private boolean insideTagRoles = true;
	
	public List<UserRole> getRoles() {
		return roles;
	}
	
	@Override
	public void startDocument() throws SAXException {
		roles = new ArrayList<UserRole>();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attrs) throws SAXException {
		if (TagConstants.ROLE.equals(qName) && insideTagRoles) {
			int id = Integer.parseInt(attrs.getValue(AttrsConstants.ID));
			String value = attrs.getValue(AttrsConstants.VALUE);
			UserRole role = new UserRole();
			role.setId(id);
			role.setValue(value);
			roles.add(role);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (TagConstants.ROLES.equals(qName)) {
			insideTagRoles = false;
		}
	}
}
