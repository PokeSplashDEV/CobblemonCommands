package org.pokesplash.cobblemoncommands.command;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.pokesplash.cobblemoncommands.CobblemonCommands;
import org.pokesplash.cobblemoncommands.util.LuckPermsUtils;
import org.pokesplash.cobblemoncommands.util.Utils;

public class DeleteMoveCommand {
	public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> root = CommandManager
				.literal("deletemove")
				.requires(ctx -> {
					if (ctx.isExecutedByPlayer()) {
						return LuckPermsUtils.hasPermission(ctx.getPlayer(), CommandHandler.basePermission +
								".deletemove");
					} else {
						return true;
					}
				})
				.executes(this::usage)
				.then(CommandManager.argument("slot", IntegerArgumentType.integer())
						.suggests((ctx, builder) -> {
							for (int x=1; x < 7; x++) {
								builder.suggest(x);
							}
							return builder.buildFuture();
						})
						.executes(this::usage)
						.then(CommandManager.argument("moveSlot", IntegerArgumentType.integer())
								.suggests((ctx, builder) -> {
									for (int x=1; x < 5; x++) {
										builder.suggest(x);
									}
									return builder.buildFuture();
								})
								.executes(this::run)));

		LiteralCommandNode<ServerCommandSource> registeredCommand = dispatcher.register(root);
	}

	public int run(CommandContext<ServerCommandSource> context) {

		if (!context.getSource().isExecutedByPlayer()) {
			context.getSource().sendMessage(Text.literal("This command must be executed by a player"));
			return 1;
		}

		int slot = IntegerArgumentType.getInteger(context, "slot") - 1;

		if (slot < 0 || slot > 5) {
			context.getSource().sendMessage(Text.literal("§cPokemon slot must be between 1 and 6."));
			return 1;
		}

		int moveSlot = IntegerArgumentType.getInteger(context, "moveSlot") - 1;

		if (moveSlot < 0 || moveSlot > 3) {
			context.getSource().sendMessage(Text.literal("§cMove slot must be between 1 and 4."));
			return 1;
		}

		PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(context.getSource().getPlayer());

		Pokemon pokemon = party.get(slot);

		if (pokemon == null) {
			context.getSource().sendMessage(Text.literal("§cNo Pokemon found in slot " + (slot + 1)));
			return 1;
		}

		pokemon.getMoveSet().setMove(moveSlot, null);

		context.getSource().sendMessage(Text.literal("§2Successfully removed move in slot " + (slot + 1) +
				" from " + party.get(slot).getDisplayName().getString()));

		return 1;
	}

	public int usage(CommandContext<ServerCommandSource> context) {
		String message = "§bDelete Move Usage: \n§3/deletemove <pokemon slot> <move slot>";

		context.getSource().sendMessage(Text.literal(Utils.formatMessage(message, context.getSource().isExecutedByPlayer())));

		return 1;
	}
}
