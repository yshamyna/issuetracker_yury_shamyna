package org.training.issuetracker.dao.xml.parsers;

import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.IssueType;
import org.training.issuetracker.dao.xml.constants.AttrsConstants;
import org.training.issuetracker.dao.xml.constants.TagConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TypeParser extends DefaultHandler {
	private List<IssueType> types;
	private boolean insideTagIssueTypes = true;
	
	public List<IssueType> getTypes() {
		return types;
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (TagConstants.ISSUE_TYPES.equals(qName)) {
			insideTagIssueTypes = false;
		}
	}

	@Override
	public void startDocument() throws SAXException {
		types = new ArrayList<IssueType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attrs) throws SAXException {
		if (TagConstants.TYPE.equals(qName) && insideTagIssueTypes) {
			int id = Integer.parseInt(attrs.getValue(AttrsConstants.ID));
			String value = attrs.getValue(AttrsConstants.VALUE);
			IssueType type = new IssueType();
			type.setId(id);
			type.setValue(value);
			types.add(type);
		}
	}

}
