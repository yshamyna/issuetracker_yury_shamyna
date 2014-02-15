package org.training.issuetracker.web.servlets.attachment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.training.issuetracker.db.beans.Attachment;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.AttachmentService;
import org.training.issuetracker.web.constants.GeneralConsants;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String CONTENT_TYPE = "text/html";
       
    public FileUploadServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		File tmpDir;
		File destinationDir;
		String path = getServletContext().getRealPath(GeneralConsants.EMPTY_STRING);
		
		String tempDirPath = path + GeneralConsants.TEMP_DIR_PATH;
        tmpDir = new File(tempDirPath);
        String destinationDirPath = path + GeneralConsants.DESTINATION_DIR_PATH 
        		+ request.getParameter(ParameterConstants.ISSUE);
        destinationDir = new File(destinationDirPath);
        if (!destinationDir.isDirectory()) {
        	destinationDir.mkdirs();
        }

        response.setContentType(CONTENT_TYPE);

        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        fileItemFactory.setSizeThreshold(GeneralConsants.ONE_MB);

        fileItemFactory.setRepository(tmpDir);

        ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
        uploadHandler.setSizeMax(GeneralConsants.HUNDRED_MB);

        try {

            List<FileItem> items = uploadHandler.parseRequest(request);
            Iterator<FileItem> itr = items.iterator();
            String filename = null;

            while (itr.hasNext()) {
                FileItem item = itr.next();
                if (!item.isFormField()) {
                	filename = item.getName();
                    String name = new File(item.getName()).getName();
                    File file = new File(destinationDir, name);
                    item.write(file);
                }
            }

            Attachment attachment = new Attachment();
            attachment.setFilename(filename);
            User addedBy = (User) request.getSession().
            				getAttribute(ParameterConstants.USER);
            attachment.setAddedBy(addedBy);
            attachment.setAddDate(new Timestamp(System.currentTimeMillis()));
            attachment.setIssueId(Long.parseLong(request.
            				getParameter(ParameterConstants.ISSUE)));
            
            AttachmentService service = new AttachmentService(addedBy);
            service.add(attachment);
            
            PrintWriter out = response.getWriter();
            out.println(MessageConstants.FILE_UPLOAD);
            out.println("<a href=\"/issuetracker/issues/edit?id=" 
            				+ request.getParameter("issue") 
            				+ "\">Back</a>");
            out.close();
        } catch (FileNotFoundException e) {
        	PrintWriter out = response.getWriter();
        	out.println(MessageConstants.CHOOSE_FILE);
            out.println("<a href=\"/issuetracker/issues/edit?id=" 
             				+ request.getParameter("issue") 
             				+ "\">Back</a>");
        } catch (Exception e) {
            response.getWriter().println(MessageConstants.SORRY_MESSAGE);
        }
	}

}
