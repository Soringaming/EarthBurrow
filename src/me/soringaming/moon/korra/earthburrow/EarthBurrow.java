package me.soringaming.moon.korra.earthburrow;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.ability.EarthAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.util.ClickType;

public class EarthBurrow extends EarthAbility implements AddonAbility, ComboAbility {

	private Permission perm;

	public EarthBurrow(Player player) {
		super(player);
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
		if (!bPlayer.canBend(CoreAbility.getAbility("EarthBurrow"))) {
			remove();
			return;
		}
	}

	@Override
	public Object createNewComboInstance(Player arg0) {
		return new EarthBurrow(player);
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		ArrayList<AbilityInformation> EarthBurrow = new ArrayList<AbilityInformation>();
		EarthBurrow.add(new AbilityInformation("Collapse", ClickType.RIGHT_CLICK));
		EarthBurrow.add(new AbilityInformation("EarthTunnel", ClickType.LEFT_CLICK));
		EarthBurrow.add(new AbilityInformation("EarthTunnel", ClickType.LEFT_CLICK));
		return EarthBurrow;
	}

	@Override
	public String getInstructions() {
		return "Collapse (Right-Click) > EarthTunnel (Left-Click) > EarthTunnel (Left-Click)";
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
