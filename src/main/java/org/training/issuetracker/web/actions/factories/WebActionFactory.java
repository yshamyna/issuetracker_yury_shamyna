package org.training.issuetracker.web.actions.factories;

import org.training.issuetracker.web.actions.AddPriorityWebAction;
import org.training.issuetracker.web.actions.AddStatusWebAction;
import org.training.issuetracker.web.actions.AddTypeWebAction;
import org.training.issuetracker.web.actions.LoginWebAction;
import org.training.issuetracker.web.actions.LogoutWebAction;
import org.training.issuetracker.web.actions.ShowPrioritiesWebAction;
import org.training.issuetracker.web.actions.ShowStatusesWebAction;
import org.training.issuetracker.web.actions.ShowTypesWebAction;
import org.training.issuetracker.web.actions.interfaces.IWebAction;

public class WebActionFactory {
	private static enum Action {
		LOGIN, LOGOUT, SHOW_TYPES, SHOW_STATUSES, SHOW_PRIORITIES, ADD_PRIORITY,
		ADD_STATUS, ADD_TYPE;
	}
	
	public static IWebAction getWebActionFromFactory(String action) {
		Action actn = Action.valueOf(action.toUpperCase());
		IWebAction webAction = null;
		switch (actn) {
			case LOGIN: webAction = new LoginWebAction(); break;
			case LOGOUT: webAction = new LogoutWebAction(); break;
			case SHOW_TYPES: webAction = new ShowTypesWebAction(); break;
			case SHOW_STATUSES: webAction = new ShowStatusesWebAction(); break;
			case SHOW_PRIORITIES: webAction = new ShowPrioritiesWebAction(); break;
			case ADD_STATUS: webAction = new AddStatusWebAction(); break;
			case ADD_TYPE: webAction = new AddTypeWebAction(); break;
			case ADD_PRIORITY: webAction = new AddPriorityWebAction(); break;
		}
		return webAction;
	}
}
