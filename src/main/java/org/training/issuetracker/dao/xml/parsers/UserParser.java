package org.training.issuetracker.dao.xml.parsers;

import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.User;
import org.training.issuetracker.dao.interfaces.IRoleDAO;
import org.training.issuetracker.dao.xml.constants.AttrsConstants;
import org.training.issuetracker.dao.xml.constants.TagConstants;
import org.training.issuetracker.dao.xml.service.RoleDAO;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UserParser extends DefaultHandler {
	private List<User> users;
	private boolean insideTagUsers = false;
	private String currentTag;
	private User user;
	
	public List<User> getUsers() {
		return users;
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (TagConstants.USERS.equals(qName)) {
			insideTagUsers = false;
		}
		if (TagConstants.USER.equals(qName) && insideTagUsers) {
			users.add(user);
		}
	}
	@Override
	public void startDocument() throws SAXException {
		users = new ArrayList<User>();
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentTag = qName;
		if (TagConstants.USERS.equals(qName)) {
			insideTagUsers = true;
		}
		if (TagConstants.USER.equals(qName) && insideTagUsers) {
			user = new User();
			int id = Integer.parseInt(attributes.getValue(AttrsConstants.ID));
			user.setId(id);
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (insideTagUsers) {
			if (TagConstants.FIRST_NAME.equals(currentTag)) {
				user.setFirstName(new String(ch, start, length));
			}
			if (TagConstants.LAST_NAME.equals(currentTag)) {
				user.setLastName(new String(ch, start, length));
			}
			if (TagConstants.EMAIL_ADDRESS.equals(currentTag)) {
				user.setEmailAddress(new String(ch, start, length));
			}
			if (TagConstants.PASSWORD.equals(currentTag)) {
				user.setPassword(new String(ch, start, length));
			}
			if (TagConstants.ROLE.equals(currentTag)) {
				int roleId = Integer.parseInt(new String(ch, start, length));
				IRoleDAO rDAO = new RoleDAO();
				try {
					user.setRole(rDAO.getById(roleId));
				} catch (Exception e) {
					throw new SAXException(e);
				}
			}
			currentTag = null;
		}
	}
	
	
}
