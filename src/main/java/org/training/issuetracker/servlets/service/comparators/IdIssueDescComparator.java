package org.training.issuetracker.servlets.service.comparators;

import java.util.Comparator;

import org.training.issuetracker.beans.Issue;

public class IdIssueDescComparator implements Comparator<Issue> {
	public int compare(Issue i1, Issue i2) {
    	return i1.getId() < i2.getId() ? 1 : i1.getId() > i2.getId() ? -1 : 0;
    }
}
