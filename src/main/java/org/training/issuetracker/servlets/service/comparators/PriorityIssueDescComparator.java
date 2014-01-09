package org.training.issuetracker.servlets.service.comparators;

import java.util.Comparator;

import org.training.issuetracker.beans.Issue;

public class PriorityIssueDescComparator implements Comparator<Issue> {
	public int compare(Issue i1, Issue i2) {
		return i2.getPriority().getValue().compareTo(i1.getPriority().getValue());
    }
}
