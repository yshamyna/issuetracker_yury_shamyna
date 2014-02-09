package org.training.issuetracker.db.interfaces;

public interface ActionFactory {
	public Action getActionFromFactory(String entityType);
}
