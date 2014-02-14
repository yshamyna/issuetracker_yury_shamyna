package org.training.issuetracker.web.servlets.attachment;

import java.io.File;
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
import org.training.issuetracker.web.constants.ParameterConstants;

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FileUploadServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File tmpDir;
		File destinationDir;
		String path = getServletContext().getRealPath("");
		
		String tempDirPath = path + "\\temp";
        tmpDir = new File(tempDirPath);
        String destinationDirPath = path + "\\issues\\" + request.
        				getParameter(ParameterConstants.ISSUE);
        destinationDir = new File(destinationDirPath);
        if (!destinationDir.isDirectory()) {
        	destinationDir.mkdirs();
        }

        response.setContentType("text/html");

        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        fileItemFactory.setSizeThreshold(1 * 1024 * 1024);

        fileItemFactory.setRepository(tmpDir);

        ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
        uploadHandler.setSizeMax(1024 * 1024 * 100);

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
            out.println("File upload successfully.");
            out.println("<a href=\"/issuetracker/issues/edit?id=" 
            				+ request.getParameter("issue") 
            				+ "\">Back</a>");
            out.close();
        } catch (Exception e) {
            response.getWriter().println("Sorry, but current service is not available... Please try later.");
        }
	}

}
