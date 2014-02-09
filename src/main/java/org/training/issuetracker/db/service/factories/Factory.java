package org.training.issuetracker.db.service.factories;

import org.training.issuetracker.db.interfaces.ActionFactory;
import org.training.issuetracker.db.service.factories.enums.ActionType;

public class Factory {
	public static ActionFactory getActionFactory(String actionType) {
		ActionType type = ActionType.valueOf(actionType);
		switch (type) {
			case UPDATE: return new UpdateActionFactory(); 
		}
		return null;
	}
}
