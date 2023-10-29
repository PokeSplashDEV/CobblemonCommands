package org.pokesplash.cobblemoncommands.event;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.abilities.AbilityTemplate;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import kotlin.Unit;
import org.pokesplash.cobblemoncommands.CobblemonCommands;
import org.pokesplash.cobblemoncommands.util.CobblemonUtils;

import java.util.concurrent.ThreadLocalRandom;

public class PokemonSpawnEvent {
	public void registerEvent() {
		CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, e -> {

			if (CobblemonCommands.config.getHiddenAbility().isEnableSpawning()) {
				int randomNumber = ThreadLocalRandom.current()
						.nextInt(0, CobblemonCommands.config.getHiddenAbility().getSpawnRate());

				if (randomNumber == 0) {
					AbilityTemplate abilityTemplate = CobblemonUtils.getHA(e.getPokemon());

					if (abilityTemplate != null) {
						e.getPokemon().setAbility(abilityTemplate.create(true));
					}
				}
			}

			return Unit.INSTANCE;
		});
	}
}
