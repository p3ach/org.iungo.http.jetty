package org.iungo.http.jetty.api;

import org.iungo.http.api.HTTPNodeContext;
import org.iungo.node.api.NodeContext;

public class JettyHTTPNodeContext extends HTTPNodeContext {

	private static final long serialVersionUID = 1L;

	public static final String PATH = "Path";
	
	public JettyHTTPNodeContext() {
		super();
	}

	public JettyHTTPNodeContext(final NodeContext nodeContext) {
		super(nodeContext);
	}

	public String getPath() {
		return (String) get(PATH);
	}

	public void setPath(final String path) {
		put(PATH, path);
	}
}
