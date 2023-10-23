package org.pokesplash.cobblemoncommands.config;

import com.google.gson.Gson;
import org.pokesplash.cobblemoncommands.CobblemonCommands;
import org.pokesplash.cobblemoncommands.util.Utils;

import java.util.concurrent.CompletableFuture;

/**
 * Config file.
 */
public class Config {

	private PrestigeConfig prestige;

	/**
	 * Constructor to create a default config file.
	 */
	public Config() {
		prestige = new PrestigeConfig();
	}

	/**
	 * Method to initialize the config.
	 */
	public void init() {
		CompletableFuture<Boolean> futureRead = Utils.readFileAsync(CobblemonCommands.BASE_PATH, "config.json",
				el -> {
					Gson gson = Utils.newGson();
					Config cfg = gson.fromJson(el, Config.class);
					prestige = cfg.getPrestige();
				});

		if (!futureRead.join()) {
			CobblemonCommands.LOGGER.info("No config.json file found for cobblemoncommands. Attempting to generate one.");
			Gson gson = Utils.newGson();
			String data = gson.toJson(this);
			CompletableFuture<Boolean> futureWrite = Utils.writeFileAsync(CobblemonCommands.BASE_PATH, "config.json", data);

			if (!futureWrite.join()) {
				CobblemonCommands.LOGGER.fatal("Could not write config for cobblemoncommands.");
			}
			return;
		}
		CobblemonCommands.LOGGER.info("cobblemoncommands config file read successfully.");
	}

	public PrestigeConfig getPrestige() {
		return prestige;
	}
}