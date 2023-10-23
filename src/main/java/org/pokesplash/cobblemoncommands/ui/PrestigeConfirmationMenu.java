package org.pokesplash.cobblemoncommands.ui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.FlagType;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.CobblemonItems;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import org.pokesplash.cobblemoncommands.CobblemonCommands;
import org.pokesplash.cobblemoncommands.util.Utils;

import java.util.ArrayList;
import java.util.Collection;

public class PrestigeConfirmationMenu {
	public Page getPage(boolean isRegular) {
		Button filler = GooeyButton.builder()
				.display(new ItemStack(Items.WHITE_STAINED_GLASS_PANE))
				.title("")
				.lore(new ArrayList<>())
				.hideFlags(FlagType.All)
				.build();

		String difficulty = isRegular ? "Regular" : "Hard";


		Collection<String> confirmLore = new ArrayList<>();
		confirmLore.add("§aClick to prestige in the " + difficulty + " mode gyms");
		confirmLore.add("§cThis action can not be undone!");
		Button confirm = GooeyButton.builder()
				.display(new ItemStack(Items.LIME_STAINED_GLASS_PANE))
				.title("§2Confirm")
				.lore(confirmLore)
				.hideFlags(FlagType.All)
				.onClick(e -> {
					CommandDispatcher<ServerCommandSource> dispatcher =
							e.getPlayer().getServer().getCommandManager().getDispatcher();

					ArrayList<String> commands = isRegular ? CobblemonCommands.config.getPrestige().getRegularCommands() :
							CobblemonCommands.config.getPrestige().getHardCommands();

					for (String command : commands) {
						try {
							dispatcher.execute(
									Utils.formatPlaceholders(command, e.getPlayer().getName().getString()),
									e.getPlayer().getServer().getCommandSource());
						} catch (CommandSyntaxException ex) {
							throw new RuntimeException(ex);
						}
					}
					UIManager.closeUI(e.getPlayer());
				})
				.build();

		Collection<String> cancelLore = new ArrayList<>();
		confirmLore.add("§cReturn to the main menu.");
		Button cancel = GooeyButton.builder()
				.display(new ItemStack(Items.RED_STAINED_GLASS_PANE))
				.title("§cCancel")
				.lore(cancelLore)
				.hideFlags(FlagType.All)
				.onClick(e -> {
					UIManager.openUIForcefully(e.getPlayer(), new PrestigeMenu().getPage());
				})
				.build();

		ChestTemplate template = ChestTemplate.builder(3)
				.fill(filler)
				.set(11, confirm)
				.set(15, cancel)
				.build();

		return GooeyPage.builder()
				.template(template)
				.title("§3Confirm Prestige")
				.build();
	}
}
