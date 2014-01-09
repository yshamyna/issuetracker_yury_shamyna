package org.training.issuetracker.servlets.service.comparators;

import java.util.Comparator;

import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.beans.User;

public class AssigneeIssueDescComparator implements Comparator<Issue> {
	public int compare(Issue i1, Issue i2) {
		User assignee1 = i1.getAssignee();
		User assignee2 = i2.getAssignee();
		if (assignee1 == null && assignee2 == null) {
			return 0;
		} else if (assignee1 == null) {
			return 1;
		} else if (assignee2 == null) {
			return -1;
		} else {
			String user1 = assignee1.getFirstName()  
					+ assignee1.getLastName(); 
			String user2 = assignee2.getFirstName()  
					+ assignee2.getLastName();
			return user2.compareTo(user1);	
		}
    }
}
