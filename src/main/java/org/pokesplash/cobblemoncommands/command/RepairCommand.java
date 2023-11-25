package org.pokesplash.cobblemoncommands.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
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

public class RepairCommand {
	public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> root = CommandManager
				.literal("repair")
				.executes(this::run);

		LiteralCommandNode<ServerCommandSource> registeredCommand = dispatcher.register(root);
	}

	public int run(CommandContext<ServerCommandSource> context) {

		if (!context.getSource().isExecutedByPlayer()) {
			context.getSource().sendMessage(Text.literal("This command must be ran by a player"));
			return 1;
		}

		ServerPlayerEntity player = context.getSource().getPlayer();

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

		return 1;
	}
}
