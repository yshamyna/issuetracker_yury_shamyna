package org.training.issuetracker.servlets.service.comparators;

import java.util.Comparator;

import org.training.issuetracker.beans.Issue;

public class StatusIssueDescComparator implements Comparator<Issue> {
	public int compare(Issue i1, Issue i2) {
		return i2.getStatus().getValue().compareTo(i1.getStatus().getValue());
    }
}
