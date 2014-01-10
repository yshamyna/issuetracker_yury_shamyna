package org.training.issuetracker.dao.xml.parsers;

import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.IssueStatus;
import org.training.issuetracker.dao.xml.constants.AttrsConstants;
import org.training.issuetracker.dao.xml.constants.TagConstants;
import org.training.issuetracker.dao.xml.parsers.enums.XMLTag;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StatusParser extends DefaultHandler {
	private List<IssueStatus> statuses;
	private boolean insideTagIssueStatuses = false;
	private XMLTag tag;
	
	public List<IssueStatus> getStatuses() {
		return statuses;
	}
	
	@Override
	public void startDocument() throws SAXException {
		statuses = new ArrayList<IssueStatus>();
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
			case ISSUE_STATUSES:
				insideTagIssueStatuses = true;
				break;
			case STATUS:
				if (insideTagIssueStatuses) {
					int id = Integer.parseInt(attrs.getValue(AttrsConstants.ID));
					String value = attrs.getValue(AttrsConstants.VALUE);
					IssueStatus status = new IssueStatus();
					status.setId(id);
					status.setValue(value);
					statuses.add(status);	
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
		if (tag == XMLTag.ISSUE_STATUSES) {
			insideTagIssueStatuses = false;
		}
	}
}
