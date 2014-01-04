package org.training.issuetracker.dao.xml.service;

import java.util.List;

import org.training.issuetracker.beans.Project;
import org.training.issuetracker.dao.interfaces.IProjectDAO;
import org.training.issuetracker.dao.xml.parsers.Parser;
import org.training.issuetracker.dao.xml.parsers.ProjectParser;

public class ProjectDAO implements IProjectDAO {

	@Override
	public List<Project> getAll() throws Exception {
		ProjectParser parser = new ProjectParser();
		Parser.parse(parser);
		return parser.getProjects();
	}

	@Override
	public Project getById(int id) throws Exception {
		List<Project> projects = getAll();
		for (Project project : projects) {
			if (id == project.getId()) {
				return project;
			}
		}
		return null;
	}

	@Override
	public void add(Project project) throws Exception {
		// nothing
	}

}
