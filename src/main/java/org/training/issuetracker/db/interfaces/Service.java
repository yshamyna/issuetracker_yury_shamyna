package org.training.issuetracker.db.interfaces;

import java.util.Map;

public interface Service {
	public String[] getParameters();
	public void add(Map<String, String> values) throws Exception;
	public void update(Map<String, String> values) throws Exception;
}
