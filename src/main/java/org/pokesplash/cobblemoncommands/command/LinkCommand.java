package org.pokesplash.cobblemoncommands.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.pokesplash.cobblemoncommands.CobblemonCommands;
import org.pokesplash.cobblemoncommands.util.LuckPermsUtils;
import org.pokesplash.cobblemoncommands.util.Utils;

public class LinkCommand {

	private String literal;
	private String message;
	private String link;

	public LinkCommand(String literal, String message, String link) {
		this.literal = literal;
		this.message = message;
		this.link = link;
	}

	public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> root = CommandManager
				.literal(literal)
				.executes(this::run);

		LiteralCommandNode<ServerCommandSource> registeredCommand = dispatcher.register(root);
	}

	public int run(CommandContext<ServerCommandSource> context) {

		if (!context.getSource().isExecutedByPlayer()) {
			return 1;
		}

		Text output = Text.literal(message).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(
				ClickEvent.Action.OPEN_URL,
				link
		)));

		context.getSource().sendMessage(output);

		return 1;
	}
}
