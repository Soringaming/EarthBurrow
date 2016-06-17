package me.soringaming.moon.korra.earthburrow;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.EarthAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;

public class EarthBurrow extends EarthAbility implements ComboAbility{

	public EarthBurrow(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public long getCooldown() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isHarmlessAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void progress() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object createNewComboInstance(Player arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInstructions() {
		// TODO Auto-generated method stub
		return null;
	}

}
