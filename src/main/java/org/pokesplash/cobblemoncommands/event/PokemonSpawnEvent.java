package org.pokesplash.cobblemoncommands.event;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.abilities.AbilityTemplate;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.pokemon.Pokemon;
import kotlin.Unit;
import net.minecraft.client.particle.Particle;
import org.pokesplash.cobblemoncommands.CobblemonCommands;
import org.pokesplash.cobblemoncommands.util.CobblemonUtils;

import java.util.concurrent.ThreadLocalRandom;

public class PokemonSpawnEvent {
	public void registerEvent() {
		// Creates hidden abilities.
		CobblemonEvents.POKEMON_ENTITY_SPAWN.subscribe(Priority.NORMAL, e -> {

			if (CobblemonCommands.config.getHiddenAbility().isEnableSpawning()) {
				int randomNumber = ThreadLocalRandom.current()
						.nextInt(0, CobblemonCommands.config.getHiddenAbility().getSpawnRate());

				if (randomNumber == 0) {
					AbilityTemplate abilityTemplate = CobblemonUtils.getHA(e.getEntity().getPokemon());

					Pokemon pokemon = e.getEntity().getPokemon();

					if (abilityTemplate != null) {
						pokemon.setAbility(abilityTemplate.create(true));

						e.getEntity().setPokemon(pokemon);
					}
				}
			}

			return Unit.INSTANCE;
		});
	}
}
