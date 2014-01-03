package org.training.issuetracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Sample Servlet interface implementation.
 */
public class SampleServlet implements Servlet {

	private ServletConfig servletConfig;

	@Override
	public void init(ServletConfig config) throws ServletException {
		servletConfig = config;
		try {
			System.out.println(config.getServletContext().getResource("issuetracker.xml"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public ServletConfig getServletConfig() {
		return servletConfig;
	}

	@Override
	public String getServletInfo() {
		return "Sample Servlet interface implementation";
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Sample Servlet interface implementation</title>");
		out.println("</head>");
		out.println("<body><b>Hello world!</b></body>");
		out.println("<h1>test</h1>");
		out.println("</html>");
		out.close();
	}
}