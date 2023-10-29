package org.pokesplash.cobblemoncommands.event;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.query.QueryOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.pokesplash.cobblemoncommands.CobblemonCommands;
import org.pokesplash.cobblemoncommands.config.SingleNodeConfig;

import java.util.Collection;
import java.util.List;

public class PlayerJoinEvent implements ServerPlayConnectionEvents.Join {

	@Override
	public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
		User player = LuckPermsProvider.get().getUserManager().getUser(handler.getPlayer().getUuid());
		Collection<Group> groups = player.getInheritedGroups(QueryOptions.nonContextual());

		for (SingleNodeConfig node : CobblemonCommands.config.getFirstJoin().getJoinGroups()) {

			List<String> track = LuckPermsProvider.get().getTrackManager().getTrack(node.getTrack()).getGroups();

			boolean hasGroup = false;
			for (Group g : groups) {
				if (track.contains(g.getName())) {
					hasGroup = true;
					break;
				}
			}

			if (!hasGroup) {
				LuckPermsProvider.get().getUserManager().modifyUser(player.getUniqueId(), user -> {
					user.data().add(Node.builder("group." + node.getGroup()).value(true).build());
				});
			}
		}
	}
}
