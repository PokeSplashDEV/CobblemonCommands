package org.pokesplash.cobblemoncommands.event;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.abilities.Abilities;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import kotlin.Unit;
import org.pokesplash.cobblemoncommands.CobblemonCommands;

import java.util.concurrent.ThreadLocalRandom;

public class PokemonCaptureEvent {
	public void registerEvent() {
		CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, e -> {

			PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(e.getPlayer());

			for (int x=0; x<6; x++) {
				Pokemon pokemon = party.get(x);

				if (pokemon != null) {
					if (pokemon.getAbility().getTemplate().equals(Abilities.INSTANCE.get("synchronize"))) {

						int randomNumber = ThreadLocalRandom.current()
								.nextInt(0, CobblemonCommands.config.getSynchronize().getRate());

						if (randomNumber == 0) {
							Pokemon caught = e.getPokemon();

							caught.setNature(pokemon.getNature());
						}

						break;
					}
				}
			}

			return Unit.INSTANCE;
		});
	}
}
