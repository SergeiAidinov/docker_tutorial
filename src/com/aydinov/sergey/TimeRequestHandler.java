package com.aydinov.sergey;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TimeRequestHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		LocalDateTime localDateTime = LocalDateTime.now();
		exchange.sendResponseHeaders(200, localDateTime.toString().length());
		OutputStream outputStream = exchange.getResponseBody();
		outputStream.write(localDateTime.toString().getBytes());
	}
}
