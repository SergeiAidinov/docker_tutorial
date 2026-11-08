package com.aydinov.sergey;

import java.io.IOException;
import java.io.OutputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TimeRequestHandler implements HttpHandler{
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	private final ZoneId zoneId;

	
	
	public TimeRequestHandler(ZoneId zoneId) {
		this.zoneId = zoneId;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		ZonedDateTime currentTime = ZonedDateTime.now(zoneId);
		String response = "Current date and time in " + zoneId + " is " + formatter.format(currentTime);
		exchange.sendResponseHeaders(200,  response.length());
		try( OutputStream outputStream = exchange.getResponseBody()) {
		outputStream.write(response.getBytes());
		}
	}
}
