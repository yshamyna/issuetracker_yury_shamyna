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

import org.training.issuetracker.web.constants.ParameterConstants;

public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FileDownloadServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
        String filename = context.getRealPath("/issues") 
        		+ "\\" + request.getParameter(ParameterConstants.ISSUE) 
        		+ "\\" + request.getParameter(ParameterConstants.FILENAME);

        String mimeType = context.getMimeType(filename);
        if (mimeType == null) {
        	response.setContentType("application/octet-stream");
        }

        System.out.println("mimeType " + mimeType);
        response.setContentType(mimeType);

        File file = new File(filename);
        response.setContentLength((int) file.length());

        response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());

        FileInputStream in = new FileInputStream(file);
        OutputStream out = response.getOutputStream();

        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }

        in.close();
        out.close();
	}

}
