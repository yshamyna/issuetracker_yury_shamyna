package org.training.issuetracker.dao.xml.service;

import java.util.List;

import org.training.issuetracker.beans.IssueResolution;
import org.training.issuetracker.dao.interfaces.IResolutionDAO;
import org.training.issuetracker.dao.xml.parsers.Parser;
import org.training.issuetracker.dao.xml.parsers.ResolutionParser;

public class ResolutionDAO implements IResolutionDAO {

	@Override
	public List<IssueResolution> getAll() throws Exception {
		ResolutionParser resolutionParser = new ResolutionParser();
		Parser.parse(resolutionParser);
		return resolutionParser.getResolutions();
	}

	@Override
	public IssueResolution getById(int id) throws Exception {
		List<IssueResolution> resolutions = getAll();
		for (IssueResolution resolution : resolutions) {
			if (id == resolution.getId()) {
				return resolution;
			}
		}
		return null;
	}

	@Override
	public void add(IssueResolution resolution) throws Exception {
		// nothing
	}

}
