package com.aydinov.sergey;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class RandomTimeRequester implements Runnable {
	
	// private static RandomTimeRequester randomTimeRequester = null;
	
	private final Random random = new Random();
	private final HttpClient client;
	private final HttpRequest REQUEST;
	private final BodyHandler<String> HANDLER = HttpResponse.BodyHandlers.ofString();

	public RandomTimeRequester(HttpClient client, String host, Integer port) {
		super();
		this.client = client;
		REQUEST = HttpRequest.newBuilder()
	            .uri(URI.create(
	                    "http://" + host + ":" + 8080 + "/currentTime"))
	            .GET()
	            .build();
	}
	
//	public static synchronized RandomTimeRequester instance(HttpClient client) {
//		if (Objects.isNull(randomTimeRequester)) randomTimeRequester = new RandomTimeRequester(client);
//		return randomTimeRequester;
//	}

	@Override
	public void run() {
		requestTimeRandomly();

	}

	private void requestTimeRandomly() {
		while (true) {
			int delayMillSec = random.nextInt(100, 3000);
			try {
				Thread.currentThread().sleep(delayMillSec);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			CompletableFuture<HttpResponse<String>> future = client.sendAsync(REQUEST, HANDLER);
			future.thenAccept(response -> System.out.println("Response: "
                    + response.body()));
		}

	}

}
