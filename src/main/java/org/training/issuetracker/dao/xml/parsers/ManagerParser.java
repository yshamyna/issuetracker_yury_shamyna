package org.training.issuetracker.dao.xml.parsers;

import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.Manager;
import org.training.issuetracker.dao.xml.constants.AttrsConstants;
import org.training.issuetracker.dao.xml.constants.TagConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ManagerParser extends DefaultHandler {
	private List<Manager> managers;
	private boolean insideTagManagers = false;
	private Manager manager = null;
	private String currentTag = null;
	
	public List<Manager> getManagers() {
		return managers;
	}
	

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (insideTagManagers) {
			if (TagConstants.FIRST_NAME.equals(currentTag)) {
				manager.setFirstName(new String(ch, start, length));
			} else if (TagConstants.LAST_NAME.equals(currentTag)) {
				manager.setLastName(new String(ch, start, length));
			}
			currentTag = null;	
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (insideTagManagers) {
			if (TagConstants.MANAGERS.equals(qName)) {
				insideTagManagers = false;
			} else if (TagConstants.MANAGER.equals(qName)) {
				managers.add(manager);
			}	
		}
		
	}

	@Override
	public void startDocument() throws SAXException {
		managers = new ArrayList<Manager>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attrs) throws SAXException {
		if (TagConstants.MANAGERS.equals(qName)) {
			insideTagManagers = true;
		}
		if (TagConstants.MANAGER.equals(qName) && insideTagManagers) {
			manager = new Manager();
			int id = Integer.parseInt(attrs.getValue(AttrsConstants.ID));
			manager.setId(id);
		}
		currentTag = qName;
	}
	
}
