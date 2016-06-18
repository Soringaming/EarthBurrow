package me.soringaming.moon.korra.earthburrow;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;

public class EarthBurrowListener {
	
	@EventHandler
	public void onShift(PlayerToggleSneakEvent e) {
		if (e.getPlayer().isSneaking()) {
			return;
		}
		
		if (canBend(e.getPlayer())) {
			new EarthBurrow(e.getPlayer());
		}
	}
		private boolean canBend(Player p) {
			BendingPlayer bp = BendingPlayer.getBendingPlayer(p.getPlayer());
			
			if (bp.canBend(CoreAbility.getAbility("EarthBurrow"))) {
				return true;
			}
			
			return false;
		}
		
	}
	
