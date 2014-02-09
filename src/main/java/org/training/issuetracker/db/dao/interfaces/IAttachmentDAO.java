package org.training.issuetracker.db.dao.interfaces;

import java.util.List;

import org.training.issuetracker.db.beans.Attachment;

public interface IAttachmentDAO {
	public List<Attachment> getListByIssueId(long issueId) throws Exception;
	public void add(Attachment attachment) throws Exception;
}
