package org.training.issuetracker.dao.xml.service;

import java.util.List;

import org.training.issuetracker.beans.IssuePriority;
import org.training.issuetracker.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.dao.xml.parsers.Parser;
import org.training.issuetracker.dao.xml.parsers.PriorityParser;

public class PriorityDAO implements IPriorityDAO {

	@Override
	public List<IssuePriority> getAll() throws Exception {
		PriorityParser priorityParser = new PriorityParser();
		Parser.parse(priorityParser);
		return priorityParser.getPriorities();
	}

	@Override
	public IssuePriority getById(int id) throws Exception {
		List<IssuePriority> priorities = getAll();
		for (IssuePriority priority : priorities) {
			if (id == priority.getId()) {
				return priority;
			}
		}
		return null;
	}

	@Override
	public void add(IssuePriority priority) throws Exception {
		// nothing
	}

}
