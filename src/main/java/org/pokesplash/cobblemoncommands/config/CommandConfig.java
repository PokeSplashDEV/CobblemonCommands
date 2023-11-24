package org.pokesplash.cobblemoncommands.config;

public class CommandConfig {
	private String command;
	private String message;
	private String link;

	public CommandConfig() {
		command = "donate";
		message = "Donate to us!";
		link = "linkhere";
	}

	public String getCommand() {
		return command;
	}

	public String getMessage() {
		return message;
	}

	public String getLink() {
		return link;
	}
}
