package org.pokesplash.cobblemoncommands.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public abstract class CommandHandler {
	public static final String basePermission = "cobblemoncommands";
	public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
		new BaseCommand().register(dispatcher);
		new PrestigeCommand().register(dispatcher);
		new DeleteMoveCommand().register(dispatcher);
	}

	public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher,
	                                    CommandRegistryAccess commandBuildContext,
	                                    CommandManager.RegistrationEnvironment commandSelection) {
		registerCommands(dispatcher);
	}
}
