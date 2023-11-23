package org.pokesplash.cobblemoncommands.event;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.battles.model.actor.BattleActor;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import kotlin.Unit;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.pokesplash.cobblemoncommands.CobblemonCommands;

import java.util.UUID;

public class PokemonDefeatedEvent {
	public void registerEvent() {
		CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, e -> {

			int totalLevels = 0;

			for (BattleActor actor : e.getLosers()) {
				for (BattlePokemon pokemon : actor.getPokemonList()) {
					totalLevels += pokemon.getEffectedPokemon().getLevel();
				}
			}

			for (BattleActor actor : e.getWinners()) {
				for (UUID uuid : actor.getPlayerUUIDs()) {
					ServerPlayerEntity player = CobblemonCommands.server.getPlayerManager().getPlayer(uuid);

					if (player != null) {
//						player.addExperience(totalLevels / 2);

						CobblemonCommands.server.getWorld(player.getWorld().getRegistryKey())
								.spawnEntity(new ExperienceOrbEntity(
										player.getWorld(),
										player.getX(),
										player.getY(),
										player.getZ(),
										totalLevels
								));
					}
				}
			}

			return Unit.INSTANCE;
		});
	}
}
