package com.aydinov.sergey;

import java.time.ZoneId;

public class Main {
	
	private static ZoneId zoneId;

	public static void main(String[] args) {
		if (args.length == 0)
			zoneId = ZoneId.systemDefault();
		else
			zoneId = ZoneId.of(args[0]);
		System.out.println("Server started with timezone " + zoneId);
		Workflow.instance(zoneId).startListeningPort();

	}

}
