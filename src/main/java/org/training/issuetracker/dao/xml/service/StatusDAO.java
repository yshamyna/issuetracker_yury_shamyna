package org.training.issuetracker.dao.xml.service;

import java.util.List;

import org.training.issuetracker.beans.IssueStatus;
import org.training.issuetracker.dao.interfaces.IStatusDAO;
import org.training.issuetracker.dao.xml.parsers.Parser;
import org.training.issuetracker.dao.xml.parsers.StatusParser;

public class StatusDAO implements IStatusDAO {
	
	public List<IssueStatus> getAll() throws Exception {
		StatusParser statusParser = new StatusParser();
		Parser.parse(statusParser);
		return statusParser.getStatuses();
	}
	
	public IssueStatus getById(int id) throws Exception {
		List<IssueStatus> statuses = getAll();
		for (IssueStatus status : statuses) {
			if (id == status.getId()) {
				return status;
			}
		}
		return null;
	}
	
	public void add(IssueStatus status) throws Exception {
		// nothing
	}
}
