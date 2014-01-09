package org.training.issuetracker.servlets.service.comparators;

import java.util.Comparator;

import org.training.issuetracker.beans.Issue;

public class TypeIssueDescComparator implements Comparator<Issue> {
	public int compare(Issue i1, Issue i2) {
		return i2.getType().getValue().compareTo(i1.getType().getValue());
    }
}
