package com.aydinov.sergey;

import java.time.DateTimeException;
import java.time.ZoneId;

public class Main {
	
	public static void main(String[] args) {
		if (args.length < 3) System.err.println(" Usage:\n"
				+ "  java -jar app.jar [timezone] [remote-host] [remote-port]\n"
				+ "\n"
				+ "Arguments:\n"
				+ "  timezone      Time zone of this HTTP connector (default: system default)\n"
				+ "  remote-host   Hostname or IP address of the remote HTTP connector (default: localhost)\n"
				+ "  remote-port   Port of the remote HTTP connector (default: 8080)\n"
				+ "\n"
				+ "Examples:\n"
				+ "  java -jar app.jar\n"
				+ "  java -jar app.jar Europe/Kyiv\n"
				+ "  java -jar app.jar Europe/Kyiv container2 8080\n"
				+ "  java -jar app.jar America/New_York container1 8080\n"
				+ "\n"
				+ "Available time zones:\n"
				+ "  Use IANA time zone identifiers, e.g. Europe/Kyiv, America/New_York, Asia/Tokyo.);");
				
		ZoneId zoneId = null;
		 try {
			zoneId = ZoneId.of(args[0]);
		} catch (DateTimeException ex) {
			System.err.println("Available timezones: " + ZoneId.getAvailableZoneIds());
			System.exit(1);
		}
		String host = args[1]; 
		Integer port = null;
		try {
		port = Integer.valueOf(args[2]);
		} catch (NumberFormatException ex) {
			System.err.println("Third argument has to be a number between 1 and 65535 inclusivly.");
			System.exit(1);
			
		}
		System.out.println("Server started with timezone " + zoneId);
		Workflow.instance(zoneId, host, port).start();
	}
}
