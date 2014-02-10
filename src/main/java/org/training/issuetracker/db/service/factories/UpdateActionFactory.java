package org.training.issuetracker.db.service.factories;

import org.training.issuetracker.db.interfaces.Action;
import org.training.issuetracker.db.interfaces.ActionFactory;
import org.training.issuetracker.db.service.actions.UpdateIssueAction;
import org.training.issuetracker.db.service.actions.UpdatePriorityAction;
import org.training.issuetracker.db.service.actions.UpdateProjectAction;
import org.training.issuetracker.db.service.actions.UpdateResolutionAction;
import org.training.issuetracker.db.service.actions.UpdateStatusAction;
import org.training.issuetracker.db.service.actions.UpdateTypeAction;
import org.training.issuetracker.db.service.actions.UpdateUserAction;
import org.training.issuetracker.db.service.factories.enums.EntityType;

public class UpdateActionFactory implements ActionFactory {
	public Action getActionFromFactory(String entityType) {
		// throw new FoundUndefinedEntityException();
		EntityType type = EntityType.valueOf(entityType);
		Action action = null;
		switch (type) {
			case PRIORITY: action = new UpdatePriorityAction(); break;
			case ISSUE: action = new UpdateIssueAction(); break;
			case PROJECT: action = new UpdateProjectAction(); break;
			case RESOLUTION: action = new UpdateResolutionAction(); break;
			case STATUS: action = new UpdateStatusAction(); break;
			case TYPE: action = new UpdateTypeAction(); break;
			case USER: action = new UpdateUserAction(); break;
		default: break;
		}
		return action;
	}
}
