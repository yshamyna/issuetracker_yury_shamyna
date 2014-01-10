package org.training.issuetracker.dao.xml.parsers;

import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.Build;
import org.training.issuetracker.beans.Project;
import org.training.issuetracker.dao.interfaces.IManagerDAO;
import org.training.issuetracker.dao.xml.constants.AttrsConstants;
import org.training.issuetracker.dao.xml.constants.TagConstants;
import org.training.issuetracker.dao.xml.parsers.enums.XMLTag;
import org.training.issuetracker.dao.xml.service.ManagerDAO;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ProjectParser extends DefaultHandler {
	private List<Project> projects;
	private Project project;
	private boolean insideTagProjects = false;
	private boolean insideTagBuilds = false;
	private XMLTag tag;
	private List<Build> builds;
	
	public List<Project> getProjects() {
		return projects;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (insideTagProjects) {
			if (new String(ch, start, length).trim().length() == 0) {
				return;
			}
			switch (tag) {
				case NAME:
					project.setName(new String(ch, start, length));
					break;
				case DESCRIPTION:
					project.setDescription(new String(ch, start, length));
					break;
				case BUILD:
					if (!insideTagBuilds) {
						Build build = null;
						int buildId = Integer.parseInt(new String(ch, start, length));
						for (Build b : builds) {
							if (buildId == b.getId()) {
								build = b;
							}
						}
						project.setBuild(build);
					}
					break;
				case MANAGER:
					int managerId = Integer.parseInt(new String(ch, start, length));
					IManagerDAO mDAO = new ManagerDAO();
					try {
						project.setManager(mDAO.getById(managerId));
					} catch (Exception e) {
						throw new SAXException(e);
					}
					break;
				default:
					break;
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
		if (insideTagProjects) {
			switch (tag) {
				case PROJECTS:
					insideTagProjects = false;
					break;
				case PROJECT:
					projects.add(project);
					break;
				case BUILDS:
					project.setBuilds(builds);
					insideTagBuilds = false;
					break;
				default: break;
			}
		}
	}

	@Override
	public void startDocument() throws SAXException {
		projects = new ArrayList<Project>();
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
			case PROJECTS:
				insideTagProjects = true;
				break;
			case PROJECT:
				if (insideTagProjects) {
					project = new Project();
					int id = Integer.parseInt(attributes.getValue(AttrsConstants.ID));
					project.setId(id);
				}
				break;
			case BUILDS:
				if (insideTagProjects) {
					builds = new ArrayList<Build>();
					insideTagBuilds = true;
				}
				break;
			case BUILD:
				if (insideTagProjects && insideTagBuilds) {
					int id = Integer.parseInt(attributes.getValue(AttrsConstants.ID));
					String version = attributes.getValue(AttrsConstants.VERSION);
					Build build = new Build();
					build.setId(id);
					build.setVersion(version);
					builds.add(build);
				}
				break;
			default: break;
		}
	}

}
