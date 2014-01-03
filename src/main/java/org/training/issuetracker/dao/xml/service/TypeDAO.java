package org.training.issuetracker.dao.xml.service;

import java.util.List;

import org.training.issuetracker.beans.IssueType;
import org.training.issuetracker.dao.interfaces.ITypeDAO;
import org.training.issuetracker.dao.xml.parsers.Parser;
import org.training.issuetracker.dao.xml.parsers.TypeParser;

public class TypeDAO implements ITypeDAO {

	@Override
	public List<IssueType> getAll() throws Exception {
		TypeParser typeParser = new TypeParser();
		Parser.parse(typeParser);
		return typeParser.getTypes();
	}

	@Override
	public IssueType getById(int id) throws Exception {
		List<IssueType> types = getAll();
		for (IssueType type : types) {
			if (id == type.getId()) {
				return type;
			}
		}
		return null;
	}

	@Override
	public void add(IssueType type) throws Exception {
		// nothing
	}

}
