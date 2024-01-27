package org.pokesplash.cobblemoncommands.command;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
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
import org.pokesplash.cobblemoncommands.config.Booster;
import org.pokesplash.cobblemoncommands.util.LuckPermsUtils;
import org.pokesplash.cobblemoncommands.util.Utils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BoosterCommand {

	private Timer timer = null;
	private ArrayList<Booster> boosters = new ArrayList<>();

	public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> root = CommandManager
				.literal("boostha")
				.requires(ctx -> {
					if (ctx.isExecutedByPlayer()) {
						return LuckPermsUtils.hasPermission(ctx.getPlayer(), CommandHandler.basePermission +
								".booster");
					} else {
						return true;
					}
				})
				.executes(this::usage)
				.then(CommandManager.argument("rate", IntegerArgumentType.integer(1))
						.executes(this::usage)
						.then(CommandManager.argument("duration", IntegerArgumentType.integer())
								.executes(this::run)));

		LiteralCommandNode<ServerCommandSource> registeredCommand = dispatcher.register(root);
	}

	public int run(CommandContext<ServerCommandSource> context) {

		try {
			int rate = IntegerArgumentType.getInteger(context, "rate");
			int duration = IntegerArgumentType.getInteger(context, "duration");


			if (timer != null) {
				boosters.add(new Booster(rate, duration));
			} else {
				startBooster(new Booster(rate, duration));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 1;
	}

	private void startBooster(Booster booster) {
		CobblemonCommands.config.getHiddenAbility().setSpawnRate(booster.getRate());

		Timer newTimer = new Timer();
		newTimer.schedule(new TimerTask() {
			@Override
			public void run() {

				Utils.broadcastMessage(Text.of("§2The HA Booster has ended"));

				if (boosters.isEmpty()) {
					CobblemonCommands.config.init();
					if (timer != null) {
						timer.cancel();
					}
					timer = null;
				} else {
					startBooster(boosters.remove(0));
				}
			}
		}, 1000 * 60 * (long) booster.getDuration());

		if (timer != null) {
			timer.cancel();
		}

		timer = newTimer;

		Utils.broadcastMessage(Text.of("§2A HA Booster has begun for " + booster.getDuration() +
				" minutes"));
	}

	public int usage(CommandContext<ServerCommandSource> context) {
		String message = "§bHA Booster Usage: \n§3/boostha <rate> <duration>";

		context.getSource().sendMessage(Text.literal(Utils.formatMessage(message, context.getSource().isExecutedByPlayer())));

		return 1;
	}
}
