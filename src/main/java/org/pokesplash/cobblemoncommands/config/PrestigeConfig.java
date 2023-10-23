package org.pokesplash.cobblemoncommands.config;

import java.util.ArrayList;

public class PrestigeConfig {
	private String regularPermissionNode;
	private ArrayList<String> regularCommands;
	private String hardPermissionNode;
	private ArrayList<String> hardCommands;

	public PrestigeConfig() {
		regularPermissionNode = "prestige.regular";
		regularCommands = new ArrayList<>();
		hardPermissionNode = "prestige.hard";
		hardCommands = new ArrayList<>();
	}

	public ArrayList<String> getRegularCommands() {
		return regularCommands;
	}

	public ArrayList<String> getHardCommands() {
		return hardCommands;
	}

	public String getRegularPermissionNode() {
		return regularPermissionNode;
	}

	public String getHardPermissionNode() {
		return hardPermissionNode;
	}
}
