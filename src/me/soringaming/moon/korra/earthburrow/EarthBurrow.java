package me.soringaming.moon.korra.earthburrow;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.EarthAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.util.TempBlock;

public class EarthBurrow extends EarthAbility implements ComboAbility {

	private static final ConcurrentHashMap<Block, Long> getTopBlock = new ConcurrentHashMap<Block, Long>();

	private Player player;
	private Location loc;
	private Permission perm;
	private Location start;

	private boolean StoredTopBlock;

	private int depth;

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
		if (!GeneralMethods.isSolid(player.getLocation().add(new Vector(0, -1, 0)).getBlock())) {
			remove();
			return;
		}
		if (player.isDead() || !player.isOnline()) {
			remove();
			return;
		}

	}

	public void doBlockRemove() {
		if (StoredTopBlock == false) {
			Block b = player.getLocation().add(new Vector(0, -1, 0)).getBlock();
			new TempBlock(b, Material.AIR, (byte) 0);
			getTopBlock.put(b, 1000L);
			player.setVelocity(new Vector(0, -4, 0));
			StoredTopBlock = true;
		} else if (depth <= 3) {
			Block b = player.getLocation().add(new Vector(0, -1, 0)).getBlock();
			b.setType(Material.AIR);
			player.setVelocity(new Vector(0, -4, 0));
			depth++;
		}

	}

	public static void revert(boolean doRevert) {
		for (Block block : getTopBlock.keySet()) {
			long time = getTopBlock.get(block);
			if (System.currentTimeMillis() > time || doRevert) {
				TempBlock.removeBlock(block);
				getTopBlock.remove(block);
			}
		}
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
