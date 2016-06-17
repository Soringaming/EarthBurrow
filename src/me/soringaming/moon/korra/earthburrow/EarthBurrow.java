package me.soringaming.moon.korra.earthburrow;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.EarthAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.util.ClickType;

public class EarthBurrow extends EarthAbility implements ComboAbility {

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
		return loc;
	}

	@Override
	public String getName() {
		return "EarthBurrow";
	}

	private String getVersion() {
		return "v1.0";
	}
	
	private String getAuthor() {
		return "Moon243 & Soringaming";
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

	}

	@Override
	public Object createNewComboInstance(Player player) {
		return new EarthBurrow(player);
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
	
	

	public void doLoad() {
		ProjectKorra.plugin.getServer().getLogger().log(Level.INFO, getName() + " " + getVersion() + " Developed By " + getAuthor() + "Has Been Enabled. ");
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new EarthBurrowListener(), ProjectKorra.plugin);
		perm = new Permission("bending.ability.EarthBurrow");
		ProjectKorra.plugin.getServer().getPluginManager().addPermission(perm);
		perm.setDefault(PermissionDefault.TRUE);
	}

	
	public void stop() {
		ProjectKorra.plugin.getServer().getPluginManager().removePermission(perm);
		ProjectKorra.plugin.getServer().getLogger().log(Level.INFO, getName() + " " + getVersion() + " Developed By " + getAuthor() + " Has Been Disabled.");
		super.remove();
	}
	
}
