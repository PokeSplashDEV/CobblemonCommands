package org.pokesplash.cobblemoncommands.event;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.abilities.AbilityTemplate;
import com.cobblemon.mod.common.api.abilities.PotentialAbility;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.pokemon.FormData;
import com.cobblemon.mod.common.pokemon.Pokemon;
import kotlin.Unit;
import org.pokesplash.cobblemoncommands.util.CobblemonUtils;
import org.pokesplash.cobblemoncommands.util.Utils;

import java.util.ArrayList;
import java.util.Iterator;

public class EvolveEvent {
	public void registerEvent() {
		CobblemonEvents.EVOLUTION_COMPLETE.subscribe(Priority.NORMAL, e -> {

			Pokemon evolved = e.getPokemon();
			FormData preSpecies = e.getPokemon().getPreEvolution().getSpecies().getForm(evolved.getAspects());
			Pokemon preEvo = preSpecies.getSpecies().create(1);

			AbilityTemplate preAbility = null;

			//
			for (PotentialAbility next : preSpecies.getAbilities()) {
				if (next.getTemplate().equals(evolved.getAbility().getTemplate())) {
					preAbility = next.getTemplate();
					break;
				}
			}

			if (preAbility == null) {
				return Unit.INSTANCE;
			}

			preEvo.setAbility(preAbility.create(true));

			// If pre evo is HA and evo has HA, make it HA.
			if (CobblemonUtils.isHA(preEvo) && CobblemonUtils.getHA(evolved) != null) {
				evolved.setAbility(CobblemonUtils.getHA(evolved).create(true));
			} else {
				ArrayList<AbilityTemplate> evolvedAbilities = CobblemonUtils.getNormalAbilities(evolved);
				int index = CobblemonUtils.getNormalAbilities(preEvo).indexOf(preAbility);

				if (evolvedAbilities.size() != 1 && index >= 0) {
					evolved.setAbility(evolvedAbilities.get(index).create(true));
				} else {
					evolved.setAbility(evolvedAbilities.get(0).create(true));
				}

			}

			return Unit.INSTANCE;
		});
	}
}
