package org.pokesplash.cobblemoncommands.ui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.FlagType;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.CobblemonItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import org.pokesplash.cobblemoncommands.CobblemonCommands;
import org.pokesplash.cobblemoncommands.util.LuckPermsUtils;

import java.util.ArrayList;
import java.util.Collection;

public class PrestigeMenu {
	public Page getPage() {
		Button filler = GooeyButton.builder()
				.display(new ItemStack(Items.WHITE_STAINED_GLASS_PANE))
				.title("")
				.lore(new ArrayList<>())
				.hideFlags(FlagType.All)
				.build();

		Button normalMode = GooeyButton.builder()
				.display(new ItemStack(CobblemonItems.POKE_BALL))
				.title("§9§lPrestige Regular Mode")
				.lore(new ArrayList<>())
				.hideFlags(FlagType.All)
				.onClick(e -> {
					if (LuckPermsUtils.hasPermission(e.getPlayer(),
							CobblemonCommands.config.getPrestige().getRegularPermissionNode())) {
						UIManager.openUIForcefully(e.getPlayer(), new PrestigeConfirmationMenu().getPage(true));
					} else {
						e.getPlayer().sendMessage(Text.literal("§cYou have not completed the Regular gyms."));
					}
				})
				.build();

		Button hardMode = GooeyButton.builder()
				.display(new ItemStack(CobblemonItems.GREAT_BALL))
				.title("§3§lPrestige Hard Mode")
				.lore(new ArrayList<>())
				.hideFlags(FlagType.All)
				.onClick(e -> {
					if (LuckPermsUtils.hasPermission(e.getPlayer(),
							CobblemonCommands.config.getPrestige().getHardPermissionNode())) {
						UIManager.openUIForcefully(e.getPlayer(), new PrestigeConfirmationMenu().getPage(false));
					} else {
						e.getPlayer().sendMessage(Text.literal("§cYou have not completed the Hard gyms."));
					}
				})
				.build();

		Collection<String> lore = new ArrayList<>();
		lore.add("§dOnce you have completed an entire gym difficulty");
		lore.add("§dyou can prestige which will reset you back to the beginning.");
		Button info = GooeyButton.builder()
				.display(new ItemStack(Items.NETHER_STAR))
				.title("§5§lChoose a mode to prestige.")
				.lore(lore)
				.hideFlags(FlagType.All)
				.build();

		ChestTemplate template = ChestTemplate.builder(3)
				.fill(filler)
				.set(11, normalMode)
				.set(13, info)
				.set(15, hardMode)
				.build();

		return GooeyPage.builder()
				.template(template)
				.title("§9§lPrestige")
				.build();
	}
}
