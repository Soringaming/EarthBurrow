package me.soringaming.moon.korra.earthburrow;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.EarthAbility;
import com.projectkorra.projectkorra.util.TempBlock;

public class EarthBurrow extends EarthAbility implements AddonAbility {

	private Permission perm;

	private Player player;

	private boolean topBlockStored;

	private int depth;
	
	private static final ConcurrentHashMap<Block, Long> TopBlock = new ConcurrentHashMap<Block, Long>();

	public EarthBurrow(Player player) {
		super(player);
		this.player = player;
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
		return "EarthBurrow";
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
		revert(false);
		if (player.isDead() || !player.isOnline()) {
			remove();
			return;
		}
		
		if(!GeneralMethods.isSolid(player.getLocation().add(new Vector(0, -1, 0)).getBlock())) {
			Block block = player.getLocation().add(new Vector(0, -1, 0)).getBlock();
			if(!isEarthbendable(block)) {
				remove();
				return;
			} else {
				player.teleport(block.getLocation());
				sinkPlayer();
			}
			
		}
			
	}
	
	public void sinkPlayer() {
		Block block = player.getLocation().add(new Vector(0, -1, 0)).getBlock();
		if(!topBlockStored) {
			player.setVelocity(new Vector(0, -5, 0));
			new TempBlock(block, Material.AIR, (byte) 0);
			player.getWorld().playSound(player.getLocation(), Sound.DIG_STONE, 1, 1);
			TopBlock.put(block, 1000L);
			topBlockStored = true;
			depth++;
		} else {
			if(depth <= 3) {
				depth++;
				block.setType(Material.AIR);
				player.getWorld().playSound(player.getLocation(), Sound.DIG_STONE, 1, 1);
			}
		}
	}
	
	public static void revert(boolean doRevert) {
		for(Block b : TopBlock.keySet()) {
			long time = TopBlock.get(b);
			if(System.currentTimeMillis() >= time || doRevert) {
				TempBlock.revertBlock(b, Material.AIR);
				TopBlock.remove(b);
			}
		}
	}
	
	@Override
	public String getAuthor() {
		return "Soringaming & Moon243";
	}

	@Override
	public String getVersion() {
		return "v1.0";
	}

	@Override
	public void load() {
		ProjectKorra.plugin.getServer().getLogger().log(Level.INFO,
				getName() + " " + getVersion() + " Developed By " + getAuthor() + " Has Been Enabled");
		perm = new Permission("bending.ability.EarthBurrow");
		perm.setDefault(PermissionDefault.TRUE);
		ProjectKorra.plugin.getServer().getPluginManager().addPermission(perm);
	}

	@Override
	public void stop() {
		ProjectKorra.plugin.getServer().getLogger().log(Level.INFO,
				getName() + " " + getVersion() + " Developed By " + getAuthor() + " Has Been Disabled");
		ProjectKorra.plugin.getServer().getPluginManager().removePermission(perm);
		super.remove();
	}

}
