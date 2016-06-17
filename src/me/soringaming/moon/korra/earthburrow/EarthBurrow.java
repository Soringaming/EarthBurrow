package me.soringaming.moon.korra.earthburrow;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.EarthAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;

public class EarthBurrow extends EarthAbility implements ComboAbility{
	
	private Player player;
	private Location loc;
	private Permission perm;
	private Location start;
	
	public EarthBurrow(Player player) {
		super(player);
		this.player = player;
		this.loc = player.getLocation();
		this.start = player.getLocation();
	}

	@Override
	public long getCooldown() {
		return 0;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void progress() {
		
	}

	@Override
	public Object createNewComboInstance(Player arg0) {
		return null;
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		return null;
	}

	@Override
	public String getInstructions() {
		return null;
	}

}
