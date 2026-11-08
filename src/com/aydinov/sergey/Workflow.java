package com.aydinov.sergey;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.util.Objects;
import com.sun.net.httpserver.HttpServer;

public class Workflow {

	private static Workflow workflow = null;
	private final HttpClient client;
	private final HttpServer server;
	private final TimeRequestHandler timeRequestHandler;

	private Workflow() throws IOException {
		super();
		client = HttpClient.newBuilder().build();
		server = HttpServer.create(new InetSocketAddress(8080), 0);
		server.createContext("/currentTime", new TimeRequestHandler());
		timeRequestHandler = new TimeRequestHandler();

	}

	public static synchronized Workflow instance() {
		if (Objects.isNull(workflow))
			try {
				workflow = new Workflow();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return workflow;
	}

	public boolean startListeningPort() {
		server.start();
		return true;
	}

}
