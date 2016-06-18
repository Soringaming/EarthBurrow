package me.soringaming.moon.korra.earthburrow;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.ability.EarthAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.TempBlock;

public class EarthBurrow extends EarthAbility implements AddonAbility, ComboAbility {

	private static final ConcurrentHashMap<Block, Long> getTopBlock = new ConcurrentHashMap<Block, Long>();

	private Location loc;
	private Permission perm;
	private boolean StoredTopBlock;

	private int depth;

	public EarthBurrow(Player player) {
		super(player);
		this.loc = player.getLocation();
		start();
	}

	@Override
	public long getCooldown() {
		return 0;
	}

	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public String getName() {
		return "EarthBurrow";
	}

	public String getDescripton() {
		return "Test Ability";
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
		if (!bPlayer.canBendIgnoreBinds(CoreAbility.getAbility("EarthBurrow")) || !bPlayer.canBend(EarthBurrow.getAbility((String) "Collapse"))) {
            this.remove();
            return;
        }
		doBlockRemove();

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
		} else {
			remove();
			return;
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
	public Object createNewComboInstance(Player player) {
		return new EarthBurrow(player);
	}

	@Override
	public boolean isHiddenAbility() {
		return true;
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		ArrayList<AbilityInformation> list = new ArrayList<AbilityInformation>();
		list.add(new AbilityInformation("Collapse", ClickType.RIGHT_CLICK));
		list.add(new AbilityInformation("EarthTunnel", ClickType.LEFT_CLICK));
		list.add(new AbilityInformation("EarthTunnel", ClickType.LEFT_CLICK));
		return list;
	}

	@Override
	public String getInstructions() {
		return "Collapse > (Right-Click) > EarthTunnel (Left-Click) > EarthTunnel (Left-Click)";
	}

	@Override
	public void stop() {
		ProjectKorra.plugin.getServer().getPluginManager().removePermission(perm);
		ProjectKorra.plugin.getServer().getLogger().log(Level.INFO,
				getName() + " " + getVersion() + " Developed By " + getAuthor() + " Has Been Disabled.");
		super.remove();
	}

	@Override
	public void load() {
		ProjectKorra.plugin.getServer().getLogger().log(Level.INFO,
				getName() + " " + getVersion() + " Developed By " + getAuthor() + "Has Been Enabled. ");
		perm = new Permission("bending.ability.EarthBurrow");
		ProjectKorra.plugin.getServer().getPluginManager().addPermission(perm);
		perm.setDefault(PermissionDefault.TRUE);

	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return "Soringaming&Moon";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "v1.0";
	}

}
