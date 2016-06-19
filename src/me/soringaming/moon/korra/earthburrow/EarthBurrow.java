package me.soringaming.moon.korra.earthburrow;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.EarthAbility;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.projectkorra.util.TempBlock;

public class EarthBurrow extends EarthAbility implements AddonAbility, Listener {

	private Permission perm;

	private Player player;

	private boolean topBlockStored;

	private int depth;

	private Location start;

	private Material blockMaterial;
	private Byte blockData;

	private boolean sunk;

	private static final ConcurrentHashMap<Block, Long> TopBlock = new ConcurrentHashMap<Block, Long>();
	private static final ConcurrentHashMap<Block, Long> CapsuleBlocks = new ConcurrentHashMap<Block, Long>();
	private static final ConcurrentHashMap<Entity, Entity> instances = new ConcurrentHashMap<Entity, Entity>();

	@SuppressWarnings("deprecation")
	public EarthBurrow(Player player) {
		super(player);
		this.player = player;
		bPlayer.addCooldown((Ability) this);
		topBlockStored = false;
		start = player.getLocation().add(new Vector(0, 1, 0));
		blockMaterial = player.getLocation().add(new Vector(0, -1, 0)).getBlock().getType();
		blockData = player.getLocation().add(new Vector(0, -1, 0)).getBlock().getData();
		sunk = false;
		start();
	}

	@Override
	public long getCooldown() {
		return 1500;
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
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
		return true;
	}

	@Override
	public void progress() {
		if (player.isDead() || !player.isOnline()) {
			remove();
			return;
		}

		if (GeneralMethods.isSolid(player.getLocation().add(new Vector(0, -1, 0)).getBlock())
				&& isEarthbendable(player.getLocation().add(new Vector(0, -1, 0)).getBlock())) {
			for (Entity e : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), 2)) {
				if (e instanceof LivingEntity && e.getEntityId() == player.getEntityId()) {
					instances.put(e, e);
				}
			}
			sinkPlayer();
		}

		if (!isEarthbendable(player.getLocation().add(new Vector(0, -1, 0)).getBlock())) {
			if(!player.isSneaking()) {
				raisePlayer();
			}
			revert(false);
			sunk = true;
		}

	}

	public void sinkPlayer() {
		if (player.isSneaking() && sunk == false) {
			Block block = player.getLocation().add(new Vector(0, -1, 0)).getBlock();
			if (topBlockStored == false) {
				ParticleEffect.BLOCK_CRACK.display(
						(ParticleEffect.ParticleData) new ParticleEffect.BlockData(blockMaterial, blockData), 0.5F,
						0.5F, 1F, 0.1F, 50, start, 500);
				player.teleport(block.getLocation());
				player.getWorld().playSound(player.getLocation(), Sound.DIG_STONE, 1, 1);
				new TempBlock(block, Material.AIR, (byte) 0);
				TopBlock.put(block, 9000L);
				topBlockStored = true;
				depth++;
			} else {
				if (depth <= 3) {
					depth++;
					new TempBlock(block, Material.AIR, (byte) 0);
					CapsuleBlocks.put(block, 20000L);
					player.teleport(block.getLocation());
					player.getWorld().playSound(player.getLocation(), Sound.DIG_STONE, 1, 1);
				} else {
					revert(false);
				}
			}
		} else if (!player.isSneaking()){
			sunk = true;
			raisePlayer();
		}
	}

	public void raisePlayer() {
		player.teleport(start);
		for (Entity e : instances.keySet()) {
			instances.remove(e);
		}
		player.getWorld().playSound(player.getLocation(), Sound.DIG_STONE, 1, 1);
		ParticleEffect.BLOCK_CRACK.display(
				(ParticleEffect.ParticleData) new ParticleEffect.BlockData(blockMaterial, blockData), 0.5F, 1F, 0.5F,
				0.1F, 50, start, 500);
		revertCapsule(false);
		remove();
		return;
	}

	public static void revert(boolean doRevert) {
		for (Block b : TopBlock.keySet()) {
			long time = TopBlock.get(b);
			if (System.currentTimeMillis() >= time || doRevert) {
				TempBlock.revertBlock(b, Material.AIR);
				TopBlock.remove(b);
			}
		}
	}

	public static void revertCapsule(boolean doRevert) {
		for (Block b : CapsuleBlocks.keySet()) {
			long time = CapsuleBlocks.get(b);
			if (System.currentTimeMillis() >= time || doRevert) {
				TempBlock.revertBlock(b, Material.AIR);
				CapsuleBlocks.remove(b);
			}
		}
	}

	@Override
	public String getDescription() {
		return getName() + " " + getVersion() + " Developed By: \nA Test Ability ";
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
		ProjectKorra.plugin.getLogger().log(Level.INFO,
				getName() + " " + getVersion() + " Developed By " + getAuthor() + " Has Been Enabled");
		perm = new Permission("bending.ability.EarthBurrow");
		perm.setDefault(PermissionDefault.TRUE);
		ProjectKorra.plugin.getServer().getPluginManager().addPermission(perm);
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new EarthBurrowListener(),
				ProjectKorra.plugin);
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(this, ProjectKorra.plugin);
	}

	@Override
	public void stop() {
		ProjectKorra.plugin.getLogger().log(Level.INFO,
				getName() + " " + getVersion() + " Developed By " + getAuthor() + " Has Been Disabled");
		ProjectKorra.plugin.getServer().getPluginManager().removePermission(perm);
		super.remove();
	}

	@EventHandler
	public void onSuffocate(EntityDamageEvent e) {
		if (instances.containsKey(e.getEntity()) && e.getCause() == DamageCause.SUFFOCATION) {
			e.setCancelled(true);
		}

	}

}
