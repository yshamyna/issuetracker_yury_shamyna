package org.training.issuetracker.servlets.service.comparators;

import java.util.Comparator;

import org.training.issuetracker.beans.Issue;

public class SummaryIssueDescComparator implements Comparator<Issue>{
	public int compare(Issue i1, Issue i2) {
		return i2.getSummary().compareTo(i1.getSummary());	
    }
}
