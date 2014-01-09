package org.training.issuetracker.servlets.service.comparators;

import java.util.Comparator;

import org.training.issuetracker.beans.Issue;

public class StatusIssueAscComparator implements Comparator<Issue> {
	public int compare(Issue i1, Issue i2) {
		return i1.getStatus().getValue().compareTo(i2.getStatus().getValue());
    }
}
