package org.training.issuetracker.dao.xml.parsers;

import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.IssuePriority;
import org.training.issuetracker.dao.xml.constants.AttrsConstants;
import org.training.issuetracker.dao.xml.constants.TagConstants;
import org.training.issuetracker.dao.xml.parsers.enums.XMLTag;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PriorityParser extends DefaultHandler {
	private List<IssuePriority> priorities;
	private boolean insideTagIssuePriorities = false;
	private XMLTag tag;
	
	public List<IssuePriority> getPriorities() {
		return priorities;
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (TagConstants.TNS_ISSUETRACKER.equals(qName)) {
			tag = XMLTag.ISSUETRACKER;
		} else {
			tag = XMLTag.valueOf(qName.toUpperCase());	
		}
		if (tag == XMLTag.ISSUE_PRIORITIES) {
			insideTagIssuePriorities = false;
		}
	}
	
	@Override
	public void startDocument() throws SAXException {
		priorities = new ArrayList<IssuePriority>();
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
			case ISSUE_PRIORITIES:
				insideTagIssuePriorities = true;
				break;
			case PRIORITY:
				if (insideTagIssuePriorities) {
					int id = Integer.parseInt(attrs.getValue(AttrsConstants.ID));
					String value = attrs.getValue(AttrsConstants.VALUE);
					IssuePriority priority = new IssuePriority();
					priority.setId(id);
					priority.setValue(value);
					priorities.add(priority);	
				}
				break;
			default: 
				break;
		}
	}
}
