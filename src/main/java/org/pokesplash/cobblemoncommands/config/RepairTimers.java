package org.pokesplash.cobblemoncommands.config;

import com.google.gson.Gson;
import org.pokesplash.cobblemoncommands.CobblemonCommands;
import org.pokesplash.cobblemoncommands.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Config file.
 */
public class RepairTimers {

	private HashMap<UUID, Long> endTimes;

	/**
	 * Constructor to create a default config file.
	 */
	public RepairTimers() {
		endTimes = new HashMap<>();
	}

	/**
	 * Method to initialize the config.
	 */
	public void init() {
		CompletableFuture<Boolean> futureRead = Utils.readFileAsync(CobblemonCommands.BASE_PATH, "timers.json",
				el -> {
					Gson gson = Utils.newGson();
					RepairTimers cfg = gson.fromJson(el, RepairTimers.class);
					endTimes = cfg.getEndTimes();
				});

		if (!futureRead.join()) {
			CobblemonCommands.LOGGER.info("No timers.json file found for cobblemoncommands. Attempting to generate " +
					"one.");
			Gson gson = Utils.newGson();
			String data = gson.toJson(this);
			CompletableFuture<Boolean> futureWrite = Utils.writeFileAsync(CobblemonCommands.BASE_PATH, "timers.json",
					data);

			if (!futureWrite.join()) {
				CobblemonCommands.LOGGER.fatal("Could not write timers for cobblemoncommands.");
			}
			return;
		}
		CobblemonCommands.LOGGER.info("cobblemoncommands config file read successfully.");
	}

	public HashMap<UUID, Long> getEndTimes() {
		return endTimes;
	}

	public long getEndTime(UUID player) {
		if (endTimes.containsKey(player)) {
			return endTimes.get(player);
		}
		return -1;
	}

	public void setEndTime(UUID player, long time) {
		endTimes.put(player, time);
	}
}