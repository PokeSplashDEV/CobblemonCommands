package org.pokesplash.cobblemoncommands.command;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.api.pokemon.stats.Stat;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.pokesplash.cobblemoncommands.util.LuckPermsUtils;
import org.pokesplash.cobblemoncommands.util.Utils;

import java.util.ArrayList;

public class DittoCommand {
	public LiteralCommandNode<ServerCommandSource> build() {
		return CommandManager.literal("ivditto")
				.requires(ctx -> {
					if (ctx.isExecutedByPlayer()) {
						return LuckPermsUtils.hasPermission(ctx.getPlayer(), CommandHandler.basePermission + ".ditto");
					} else {
						return true;
					}
				})
				.executes(this::usage)
				.then(CommandManager.argument("player", StringArgumentType.string())
						.suggests((ctx, builder) -> {
							for (ServerPlayerEntity player :
									ctx.getSource().getServer().getPlayerManager().getPlayerList()) {
								builder.suggest(player.getName().getString());
							}
							return builder.buildFuture();
						})
						.executes(this::usage)
						.then(CommandManager.argument("ivs", IntegerArgumentType.integer())
								.suggests((ctx, builder) -> {
									for (int x=1; x <= 6; x++) {
										builder.suggest(x);
									}
									return builder.buildFuture();
								})
								.executes(this::run)))
				.build();
	}

	public int run(CommandContext<ServerCommandSource> context) {

		String playerName = StringArgumentType.getString(context, "player");
		ServerPlayerEntity player =
				context.getSource().getServer().getPlayerManager().getPlayer(playerName);

		if (player == null) {
			context.getSource().sendError(Text.literal(
					Utils.formatMessage("§cPlayer with name " + playerName + " can not be found.",
							context.getSource().isExecutedByPlayer())
			));
			return 1;
		}

		int ivs = IntegerArgumentType.getInteger(context, "ivs");

		if (ivs < 0 || ivs > 6) {
			context.getSource().sendError(Text.literal(
					Utils.formatMessage("§cIVs must be between 0 and 6",
							context.getSource().isExecutedByPlayer())
			));
			return 1;
		}

		Pokemon ditto = new Pokemon();
		ditto.setSpecies(PokemonSpecies.INSTANCE.getByPokedexNumber(132, Cobblemon.MODID));

		ArrayList<Stat> stats = new ArrayList<>();
		stats.add(Stats.HP);
		stats.add(Stats.ATTACK);
		stats.add(Stats.DEFENCE);
		stats.add(Stats.SPECIAL_ATTACK);
		stats.add(Stats.SPECIAL_DEFENCE);
		stats.add(Stats.SPEED);

		for (int x=0; x < ivs; x++) {
			Stat stat = Utils.getRandomValue(stats);
			ditto.setIV(stat, 31);
			stats.remove(stat);
			stats.remove(stat);
		}

		PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
		party.add(ditto);
		context.getSource().sendMessage(Text.literal(
				Utils.formatMessage("§aSuccessfully gave " + playerName + " ditto.",
						context.getSource().isExecutedByPlayer())
		));

		return 1;
	}

	public int usage(CommandContext<ServerCommandSource> context) {
		String output = "§6§lCobblemonCommands - Ditto\n§e- ivditto <player> <ivs>";
		context.getSource().sendMessage(
				Text.literal(Utils.formatMessage(output, context.getSource().isExecutedByPlayer())));
		return 1;
	}

}