package org.iungo.http.jetty.api;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.iungo.http.api.AbstractHTTPNode;
import org.iungo.http.api.AbstractHTTPServlet;
import org.iungo.node.api.NodeContext;
import org.iungo.result.api.Result;

public class JettyHTTPNode extends AbstractHTTPNode {

	private final JettyHTTPNodeContext jettyHTTPNodeContext;
	
	private final Server server;
	
	private final ServletContextHandler context;

	public JettyHTTPNode(final NodeContext nodeContext) {
		super(nodeContext);

		jettyHTTPNodeContext = new JettyHTTPNodeContext(nodeContext);
		
		server = new Server();
		ServerConnector sc = new ServerConnector(server);
		sc.setHost(jettyHTTPNodeContext.getHost());
		sc.setPort(jettyHTTPNodeContext.getPort());
		
		server.addConnector(sc);
		
		context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(jettyHTTPNodeContext.getPath());
        server.setHandler(context);
 
        try {
        	server.start();
        } catch (final Exception exception) {
        	
		}
	}

	@Override
	protected Result beforeClose() {
		Result result;
        try {
        	if (server.isStarted()) {
        		server.stop();
        	}
        	result = Result.TRUE;
		} catch (final Exception exception) {
			result = Result.valueOf(exception);
		}
		if (result.isTrue()) {
			result = super.beforeClose();
		}
		return result;
	}
	
	@Override
	public Result addServlet(final AbstractHTTPServlet httpServlet) {
		Result result = null;
		try {
			context.addServlet(new ServletHolder(httpServlet), httpServlet.getPath());
			result = new Result(true, String.format("Added HTTP Servlet [%s].", httpServlet), null);
			return result;
		} catch (final Exception exception) {
			result = Result.valueOf(exception);
			return result;
		} finally {
		}
	}

	@Override
	public Result removeServlet(final AbstractHTTPServlet httpServlet) {
		return Result.FALSE;
	}

}
