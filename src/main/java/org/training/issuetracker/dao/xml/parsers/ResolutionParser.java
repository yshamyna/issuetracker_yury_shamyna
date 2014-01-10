package org.training.issuetracker.dao.xml.parsers;

import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.IssueResolution;
import org.training.issuetracker.dao.xml.constants.AttrsConstants;
import org.training.issuetracker.dao.xml.constants.TagConstants;
import org.training.issuetracker.dao.xml.parsers.enums.XMLTag;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ResolutionParser extends DefaultHandler {
	private List<IssueResolution> resolutions;
	private boolean insideTagIssueResolutions = false;
	private XMLTag tag;
	
	public List<IssueResolution> getResolutions() {
		return resolutions;
	}

	@Override
	public void startDocument() throws SAXException {
		resolutions = new ArrayList<IssueResolution>();
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
			case ISSUE_RESOLUTIONS:
				insideTagIssueResolutions = true;
				break;
			case RESOLUTION:
				if (insideTagIssueResolutions) {
					int id = Integer.parseInt(attrs.getValue(AttrsConstants.ID));
					String value = attrs.getValue(AttrsConstants.VALUE);
					IssueResolution resolution = new IssueResolution();
					resolution.setId(id);
					resolution.setValue(value);
					resolutions.add(resolution);	
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
		if (tag == XMLTag.ISSUE_RESOLUTIONS) {
			insideTagIssueResolutions = false;
		}
	}
}
