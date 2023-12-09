package org.pokesplash.cobblemoncommands.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeEqualityPredicate;
import net.luckperms.api.util.Tristate;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.pokesplash.cobblemoncommands.CobblemonCommands;
import org.pokesplash.cobblemoncommands.util.LuckPermsUtils;
import org.pokesplash.cobblemoncommands.util.Utils;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class RepairCommand {
	public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> root = CommandManager
				.literal("repair")
				.executes(this::run);

		LiteralCommandNode<ServerCommandSource> registeredCommand = dispatcher.register(root);
	}

	public int run(CommandContext<ServerCommandSource> context) {

		try {
			if (!context.getSource().isExecutedByPlayer()) {
				context.getSource().sendMessage(Text.literal("This command must be ran by a player"));
				return 1;
			}



			ServerPlayerEntity player = context.getSource().getPlayer();

			long endTime = CobblemonCommands.repairTimers.getEndTime(player.getUuid());

			long timeLeft = endTime - new Date().getTime();

			if (timeLeft > 0) {
				context.getSource().sendMessage(Text.literal("§cYou must wait " +
						Utils.parseLongDate(timeLeft)));
				return 1;
			}

			ItemStack item = player.getInventory().getMainHandStack();

			if (item.getItem().equals(Items.AIR)) {
				context.getSource().sendMessage(Text.literal("§cYou are not holding an item."));
				return 1;
			}

			if (item.getDamage() == 0) {
				context.getSource().sendMessage(Text.literal("§cThis item can't be repaired."));
				return 1;
			}

			item.setDamage(0);

			Style green = Style.EMPTY.withColor(TextColor.parse("green"));

			context.getSource().sendMessage(Text.literal("§aYour ")
					.append(item.getName()).setStyle(green).append("§a has " +
							"been repaired."));

			long expiry = new Date().getTime() + ((long) CobblemonCommands.config.getRepairCooldown() * 60 * 1000);
			CobblemonCommands.repairTimers.setEndTime(player.getUuid(), expiry);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 1;
	}
}
