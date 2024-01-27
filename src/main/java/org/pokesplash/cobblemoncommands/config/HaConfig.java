package org.pokesplash.cobblemoncommands.config;

public class HaConfig {
	private boolean enableSpawning;
	private int spawnRate;

	public HaConfig() {
		enableSpawning = true;
		spawnRate = 250;
	}

	public boolean isEnableSpawning() {
		return enableSpawning;
	}

	public int getSpawnRate() {
		return spawnRate;
	}

	public void setSpawnRate(int spawnRate) {
		this.spawnRate = spawnRate;
	}
}
