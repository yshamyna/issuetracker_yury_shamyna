package org.training.issuetracker.dao.xml.parsers;

import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.Build;
import org.training.issuetracker.beans.Project;
import org.training.issuetracker.dao.interfaces.IManagerDAO;
import org.training.issuetracker.dao.xml.constants.AttrsConstants;
import org.training.issuetracker.dao.xml.constants.TagConstants;
import org.training.issuetracker.dao.xml.service.ManagerDAO;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ProjectParser extends DefaultHandler {
	private List<Project> projects;
	private Project project;
	private boolean insideTagProjects = false;
	private boolean insideTagBuilds = false;
	private String currentTag;
	private List<Build> builds;
	
	public List<Project> getProjects() {
		return projects;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (insideTagProjects) {
			if (TagConstants.NAME.equals(currentTag)) {
				project.setName(new String(ch, start, length));
			} else if (TagConstants.DESCRIPTION.equals(currentTag)) {
				project.setDescription(new String(ch, start, length));
			} else if (TagConstants.BUILD.equals(currentTag) && !insideTagBuilds) {
				Build build = null;
				int buildId = Integer.parseInt(new String(ch, start, length));
				for (Build b : builds) {
					if (buildId == b.getId()) {
						build = b;
					}
				}
				project.setBuild(build);
			} else if (TagConstants.MANAGER.equals(currentTag)) {
				int managerId = Integer.parseInt(new String(ch, start, length));
				IManagerDAO mDAO = new ManagerDAO();
				try {
					project.setManager(mDAO.getById(managerId));
				} catch (Exception e) {
					throw new SAXException(e);
				}
			}
			currentTag = null;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (insideTagProjects) {
			if (TagConstants.PROJECTS.equals(qName)) {
				insideTagProjects = false;
			} else if (TagConstants.PROJECT.equals(qName)) {
				projects.add(project);
			} else if (TagConstants.BUILDS.equals(qName)) {
				project.setBuilds(builds);
				insideTagBuilds = false;
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
		if (TagConstants.PROJECTS.equals(qName)) {
			insideTagProjects = true;
		}
		if (TagConstants.PROJECT.equals(qName) && insideTagProjects) {
			project = new Project();
			int id = Integer.parseInt(attributes.getValue(AttrsConstants.ID));
			project.setId(id);
		}
		if (TagConstants.BUILDS.equals(qName) && insideTagProjects) {
			builds = new ArrayList<Build>();
			insideTagBuilds = true;
		}
		if (TagConstants.BUILD.equals(qName) && insideTagProjects && insideTagBuilds) {
			int id = Integer.parseInt(attributes.getValue(AttrsConstants.ID));
			String version = attributes.getValue(AttrsConstants.VERSION);
			Build build = new Build();
			build.setId(id);
			build.setVersion(version);
			builds.add(build);
		}
		currentTag = qName;
	}

}
