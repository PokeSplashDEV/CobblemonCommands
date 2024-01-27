package org.pokesplash.cobblemoncommands.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.pokesplash.cobblemoncommands.CobblemonCommands;
import org.pokesplash.cobblemoncommands.config.CommandConfig;

public abstract class CommandHandler {
	public static final String basePermission = "cobblemoncommands";
	public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
		new BaseCommand().register(dispatcher);
		new PrestigeCommand().register(dispatcher);
		new DeleteMoveCommand().register(dispatcher);
		new EndBattleCommand().register(dispatcher);
		new RepairCommand().register(dispatcher);
		new BoosterCommand().register(dispatcher);

		for (CommandConfig config : CobblemonCommands.config.getLinkCommands()) {
			new LinkCommand(config.getCommand(), config.getMessage(), config.getLink()).register(dispatcher);
		}
	}

	public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher,
	                                    CommandRegistryAccess commandBuildContext,
	                                    CommandManager.RegistrationEnvironment commandSelection) {
		registerCommands(dispatcher);
	}
}
