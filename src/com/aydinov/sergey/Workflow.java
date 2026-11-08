package com.aydinov.sergey;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.time.Duration;
import java.time.ZoneId;
import java.util.Objects;
import com.sun.net.httpserver.HttpServer;

public class Workflow {

	private static Workflow workflow = null;
	private final HttpClient client;
	private final HttpServer server;
	private final TimeRequestHandler timeRequestHandler;
	private final String host;
	private final Integer port;

	private Workflow(ZoneId zoneId, String host, Integer port) throws IOException {
		super();
		this.host = host;
		this.port = Integer.valueOf(port);
		client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();
		server = HttpServer.create(new InetSocketAddress(port), 0);
		timeRequestHandler = new TimeRequestHandler(zoneId);
		server.createContext("/currentTime", timeRequestHandler);

	}

	public static synchronized Workflow instance(ZoneId zoneId, String host, Integer port) {
		if (Objects.isNull(workflow))
			try {
				workflow = new Workflow(zoneId, host, port);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return workflow;
	}

	private boolean startListeningPort() {
		server.start();
		return true;
	}

	public void start() {
		startListeningPort();
		RandomTimeRequester randomTimeRequester = new RandomTimeRequester(client, host, port);
		new Thread(randomTimeRequester).start();

	}

}
