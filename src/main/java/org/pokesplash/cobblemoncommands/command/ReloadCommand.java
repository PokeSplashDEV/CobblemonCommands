package org.pokesplash.cobblemoncommands.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.pokesplash.cobblemoncommands.CobblemonCommands;
import org.pokesplash.cobblemoncommands.util.LuckPermsUtils;
import org.pokesplash.cobblemoncommands.util.Utils;

public class ReloadCommand {
	public LiteralCommandNode<ServerCommandSource> build() {
		return CommandManager.literal("reload")
				.requires(ctx -> {
					if (ctx.isExecutedByPlayer()) {
						return LuckPermsUtils.hasPermission(ctx.getPlayer(), CommandHandler.basePermission + ".reload");
					} else {
						return true;
					}
				})
				.executes(this::run)
				.build();
	}

	public int run(CommandContext<ServerCommandSource> context) {
		CobblemonCommands.load();

		context.getSource().sendMessage(Text.literal(
				Utils.formatMessage("§2CobblemonCommands reloaded!", context.getSource().isExecutedByPlayer())
		));

		return 1;
	}

}