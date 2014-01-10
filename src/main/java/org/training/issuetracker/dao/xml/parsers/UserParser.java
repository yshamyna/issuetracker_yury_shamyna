package org.training.issuetracker.dao.xml.parsers;

import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.User;
import org.training.issuetracker.dao.interfaces.IRoleDAO;
import org.training.issuetracker.dao.xml.constants.AttrsConstants;
import org.training.issuetracker.dao.xml.constants.TagConstants;
import org.training.issuetracker.dao.xml.parsers.enums.XMLTag;
import org.training.issuetracker.dao.xml.service.RoleDAO;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UserParser extends DefaultHandler {
	private List<User> users;
	private boolean insideTagUsers = false;
	private XMLTag tag;
	private User user;
	
	public List<User> getUsers() {
		return users;
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (TagConstants.TNS_ISSUETRACKER.equals(qName)) {
			tag = XMLTag.ISSUETRACKER;
		} else {
			tag = XMLTag.valueOf(qName.toUpperCase());	
		}
		switch (tag) {
			case USERS:
				insideTagUsers = false;
				break;
			case USER:
				if (insideTagUsers) {
					users.add(user);
				}
				break;
			default: break;
		}
	}
	
	@Override
	public void startDocument() throws SAXException {
		users = new ArrayList<User>();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (TagConstants.TNS_ISSUETRACKER.equals(qName)) {
			tag = XMLTag.ISSUETRACKER;
		} else {
			tag = XMLTag.valueOf(qName.toUpperCase());	
		}
		switch (tag) {
			case USERS:
				insideTagUsers = true;
				break;
			case USER:
				if (insideTagUsers) {
					user = new User();
					int id = Integer.parseInt(attributes.getValue(AttrsConstants.ID));
					user.setId(id);
				}
				break;
			default: break;
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (insideTagUsers) {
			if (new String(ch, start, length).trim().length() == 0) {
				return;
			}
			switch (tag) {
				case FIRST_NAME:
					user.setFirstName(new String(ch, start, length));
					break;
				case LAST_NAME:
					user.setLastName(new String(ch, start, length));
					break;
				case EMAIL_ADDRESS:
					user.setEmailAddress(new String(ch, start, length));
					break;
				case PASSWORD: 
					user.setPassword(new String(ch, start, length));
					break;
				case ROLE:
					int roleId = Integer.parseInt(new String(ch, start, length));
					IRoleDAO rDAO = new RoleDAO();
					try {
						user.setRole(rDAO.getById(roleId));
					} catch (Exception e) {
						throw new SAXException(e);
					}
					break;
				default: 
					break;
			}
		}
	}
	
	
}
