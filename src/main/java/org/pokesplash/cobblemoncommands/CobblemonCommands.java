package org.pokesplash.cobblemoncommands;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pokesplash.cobblemoncommands.command.CommandHandler;
import org.pokesplash.cobblemoncommands.config.Config;
import org.pokesplash.cobblemoncommands.event.PlayerJoinEvent;
import org.pokesplash.cobblemoncommands.event.PokemonCaptureEvent;
import org.pokesplash.cobblemoncommands.event.PokemonDefeatedEvent;
import org.pokesplash.cobblemoncommands.event.PokemonSpawnEvent;

public class CobblemonCommands implements ModInitializer {
	public static final String MOD_ID = "CobblemonCommands";
	public static final String BASE_PATH = "/config/" + MOD_ID + "/";
	public static final String VERSION = "1.0.0";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Config config = new Config();
	public static MinecraftServer server;

	/**
	 * Runs the mod initializer.
	 */
	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register(CommandHandler::registerCommands);
		ServerWorldEvents.LOAD.register((t,e) -> server = t);
		ServerPlayConnectionEvents.JOIN.register(new PlayerJoinEvent());
		new PokemonSpawnEvent().registerEvent();
		new PokemonCaptureEvent().registerEvent();
		new PokemonDefeatedEvent().registerEvent();
		load();
	}

	public static void load() {
		config.init();
	}
}
