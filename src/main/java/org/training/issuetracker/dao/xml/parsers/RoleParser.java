package org.training.issuetracker.dao.xml.parsers;

import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.UserRole;
import org.training.issuetracker.dao.xml.constants.AttrsConstants;
import org.training.issuetracker.dao.xml.constants.TagConstants;
import org.training.issuetracker.dao.xml.parsers.enums.XMLTag;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RoleParser extends DefaultHandler {
	private List<UserRole> roles;
	private boolean insideTagRoles = false;
	private XMLTag tag;
	
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
		if (TagConstants.TNS_ISSUETRACKER.equals(qName)) {
			tag = XMLTag.ISSUETRACKER;
		} else {
			tag = XMLTag.valueOf(qName.toUpperCase());	
		}
		switch (tag) {
			case ROLES:
				insideTagRoles = true;
				break;
			case ROLE:
				if (insideTagRoles) {
					int id = Integer.parseInt(attrs.getValue(AttrsConstants.ID));
					String value = attrs.getValue(AttrsConstants.VALUE);
					UserRole role = new UserRole();
					role.setId(id);
					role.setValue(value);
					roles.add(role);	
				}
				break;
			default: 
				break;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (TagConstants.TNS_ISSUETRACKER.equals(qName)) {
			tag = XMLTag.ISSUETRACKER;
		} else {
			tag = XMLTag.valueOf(qName.toUpperCase());	
		}
		if (tag == XMLTag.ROLES) {
			insideTagRoles = false;
		}
	}
}
