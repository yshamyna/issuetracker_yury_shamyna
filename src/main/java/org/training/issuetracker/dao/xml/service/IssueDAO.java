package org.training.issuetracker.dao.xml.service;

import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.dao.interfaces.IIssueDAO;
import org.training.issuetracker.dao.xml.parsers.IssueParser;
import org.training.issuetracker.dao.xml.parsers.Parser;

public class IssueDAO implements IIssueDAO {

	@Override
	public List<Issue> getAll() throws Exception {
		IssueParser parser = new IssueParser();
		Parser.parse(parser);
		return parser.getIssues();
	}

	@Override
	public Issue getById(long id) throws Exception {
		List<Issue> issues = getAll();
		for (Issue issue : issues) {
			if (id == issue.getId()) {
				return issue;
			}
		}
		return null;
	}

	@Override
	public void add(Issue issue) throws Exception {
		// nothing
	}

	@Override
	public List<Issue> getAllByUserId(long id) throws Exception {
		List<Issue> issues = getAll();
		List<Issue> results = new ArrayList<Issue>();
		for (Issue issue : issues) {
			if (issue.getAssignee() != null && issue.getAssignee().getId() == id) {
				results.add(issue);
			}
		}
		return results;
	}

}
