package me.soringaming.moon.korra.earthburrow;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.EarthAbility;

public class EarthBurrow extends EarthAbility implements AddonAbility {

	private Permission perm;

	private Player player;

	private boolean topBlockStored;
	
	private static final ConcurrentHashMap<Block, Long> TopBlockRevert = new ConcurrentHashMap<Block, Long>();

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
		if(!topBlockStored) {
			Block block = player.getLocation().add(new Vector(0, -1, 0)).getBlock();
			player.setVelocity(new Vector(0, -5, 0));
		}
	}
	
	public static void revert(boolean doRevert) {
		
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
