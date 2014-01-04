package org.training.issuetracker.dao.xml.service;

import java.util.List;

import org.training.issuetracker.beans.Manager;
import org.training.issuetracker.dao.interfaces.IManagerDAO;
import org.training.issuetracker.dao.xml.parsers.ManagerParser;
import org.training.issuetracker.dao.xml.parsers.Parser;

public class ManagerDAO implements IManagerDAO {

	@Override
	public List<Manager> getAll() throws Exception {
		ManagerParser parser = new ManagerParser();
		Parser.parse(parser);
		return parser.getManagers();
	}

	@Override
	public Manager getById(int id) throws Exception {
		List<Manager> managers = getAll();
		for (Manager manager : managers) {
			if (id == manager.getId()) {
				return manager;
			}
		}
		return null;
	}

	@Override
	public void add(Manager manager) throws Exception {
		// nothing
	}

}
