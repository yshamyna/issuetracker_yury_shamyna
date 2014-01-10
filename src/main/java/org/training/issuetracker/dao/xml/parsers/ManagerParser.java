package org.training.issuetracker.dao.xml.parsers;

import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.Manager;
import org.training.issuetracker.dao.xml.constants.AttrsConstants;
import org.training.issuetracker.dao.xml.constants.TagConstants;
import org.training.issuetracker.dao.xml.parsers.enums.XMLTag;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ManagerParser extends DefaultHandler {
	private List<Manager> managers;
	private boolean insideTagManagers = false;
	private Manager manager = null;
	private XMLTag tag;
	
	public List<Manager> getManagers() {
		return managers;
	}
	

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (insideTagManagers) {
			if (new String(ch, start, length).trim().length() == 0) {
				return;
			}
			switch (tag) {
				case FIRST_NAME: 
					manager.setFirstName(new String(ch, start, length));
					break;
				case LAST_NAME:
					manager.setLastName(new String(ch, start, length));
					break;
				default: break;
			}
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
		switch (tag) {
			case MANAGERS: 
				insideTagManagers = false;
				break;
			case MANAGER:
				if (insideTagManagers) {
					managers.add(manager);
				}
				break;
			default: break;
		}
	}

	@Override
	public void startDocument() throws SAXException {
		managers = new ArrayList<Manager>();
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
			case MANAGERS:
				insideTagManagers = true;
				break;
			case MANAGER:
				if (insideTagManagers) {
					manager = new Manager();
					int id = Integer.parseInt(attrs.getValue(AttrsConstants.ID));
					manager.setId(id);
				}
				break;
			default: break;
		}
	}
}
