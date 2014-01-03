package org.training.issuetracker.dao.xml.parsers;

import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.IssueResolution;
import org.training.issuetracker.dao.xml.constants.AttrsConstants;
import org.training.issuetracker.dao.xml.constants.TagConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ResolutionParser extends DefaultHandler {
	private List<IssueResolution> resolutions;
	private boolean insideTagIssueResolutions = true;
	
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
		if (TagConstants.RESOLUTION.equals(qName) && insideTagIssueResolutions) {
			int id = Integer.parseInt(attrs.getValue(AttrsConstants.ID));
			String value = attrs.getValue(AttrsConstants.VALUE);
			IssueResolution resolution = new IssueResolution();
			resolution.setId(id);
			resolution.setValue(value);
			resolutions.add(resolution);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (TagConstants.ISSUE_RESOLUTIONS.equals(qName)) {
			insideTagIssueResolutions = false;
		}
	}
}
