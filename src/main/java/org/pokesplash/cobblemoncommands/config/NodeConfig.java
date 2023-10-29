package org.pokesplash.cobblemoncommands.config;

import java.util.ArrayList;

public class NodeConfig {
	private ArrayList<SingleNodeConfig> joinGroups;

	public NodeConfig() {
		joinGroups = new ArrayList<>();
		joinGroups.add(new SingleNodeConfig());
	}

	public ArrayList<SingleNodeConfig> getJoinGroups() {
		return joinGroups;
	}
}
