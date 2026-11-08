package com.aydinov.sergey;

import java.time.DateTimeException;
import java.time.ZoneId;

public class Main {
	
	public static void main(String[] args) {
		ZoneId zoneId = null;
		if (args.length == 0)
			zoneId = ZoneId.systemDefault();
		else try {
			zoneId = ZoneId.of(args[0]);
		} catch (DateTimeException ex) {
			System.err.println("Available timezones: " + ZoneId.getAvailableZoneIds());
			System.exit(1);
		}
			
		System.out.println("Server started with timezone " + zoneId);
		Workflow.instance(zoneId).startListeningPort();

	}

}
