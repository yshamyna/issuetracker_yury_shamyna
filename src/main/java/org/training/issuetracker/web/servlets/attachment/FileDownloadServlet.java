package org.training.issuetracker.web.servlets.attachment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.web.constants.GeneralConsants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_CONTENT_TYPE = 
			"application/octet-stream";
	private static final String HEADER_NAME = "Content-Disposition";
	private static final String HEADER_VALUE = "attachment; filename=";
       
    public FileDownloadServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		ServletContext context = getServletContext();
        String filename = context.getRealPath(URLConstants.ISSUES_URL) 
        		+ GeneralConsants.DOUBLE_BACKLASH 
        		+ request.getParameter(ParameterConstants.ISSUE) 
        		+ GeneralConsants.DOUBLE_BACKLASH 
        		+ request.getParameter(ParameterConstants.FILENAME);

        String mimeType = context.getMimeType(filename);
        if (mimeType == null) {
        	response.setContentType(DEFAULT_CONTENT_TYPE);
        }

        response.setContentType(mimeType);

        File file = new File(filename);
        response.setContentLength((int) file.length());

        response.addHeader(HEADER_NAME, HEADER_VALUE + file.getName());

        FileInputStream in = new FileInputStream(file);
        OutputStream out = response.getOutputStream();

        byte[] buf = new byte[GeneralConsants.ONE_KB];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }

        in.close();
        out.close();
	}

}
