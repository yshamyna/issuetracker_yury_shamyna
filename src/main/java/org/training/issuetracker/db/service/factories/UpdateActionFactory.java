package org.training.issuetracker.db.service.factories;

import org.training.issuetracker.db.interfaces.Action;
import org.training.issuetracker.db.interfaces.ActionFactory;
import org.training.issuetracker.db.service.actions.UpdatePriorityAction;
import org.training.issuetracker.db.service.factories.enums.EntityType;

public class UpdateActionFactory implements ActionFactory {
	public Action getActionFromFactory(String entityType) {
		EntityType type = EntityType.valueOf(entityType);
		switch (type) {
			case PRIORITY: return new UpdatePriorityAction();
		}
		return null;
	}
}
