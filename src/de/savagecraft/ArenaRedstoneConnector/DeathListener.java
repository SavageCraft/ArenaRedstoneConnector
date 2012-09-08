package de.savagecraft.ArenaRedstoneConnector;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.tal.redstonechips.RedstoneChips;
import org.tal.redstonechips.wireless.BroadcastChannel;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class DeathListener implements Listener {
	
	public ArenaRedstoneConnector Main; 
	//Constructor grants access to our PluginMain
	DeathListener(ArenaRedstoneConnector Main) {
		this.Main = Main;
	}
	
	//our EventListener, it's called when some Entity dies
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e){
		Main.getLogger().info("Got Event");
		if (e.getEntity() instanceof Player){ //Check if the Entity was a Player
			Main.getLogger().info("Event is Player");
			FileConfiguration config = Main.getConfig(); //Set up our Config
			Player p = (Player) e.getEntity();
			MaterialData HeadBlockData;
			if (p.getInventory().getHelmet() != null) { // If our Helm is not null (nothing)
				HeadBlockData = p.getInventory().getHelmet().getData(); //get our HelmData
				Main.getLogger().info("Got Helm");
			} else {
				Main.getLogger().info("Got no Helm");
				return; //If we have no helm, return
			}
			if (HeadBlockData.getItemType() == Material.WOOL && (HeadBlockData.getData() == 11 || HeadBlockData.getData() == 14)){ //if our Player has red or blue Wool on his head
				Main.getLogger().info("Helm is out of Wool");
				ApplicableRegionSet set = getWorldGuard().getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation()); // get all Regions within the Player is
				for (ProtectedRegion region : set){ //do this for each region 
					Main.getLogger().info("Going through regions");
					if (region.getId().equalsIgnoreCase(config.getString("PVPArena.RegionName"))) { //if the regionname equals the defined name in the config
						Main.getLogger().info("Region found");
						if (HeadBlockData.getData() == 14){ //Red
							Main.getLogger().info("Wool is red");
							BroadcastChannel c = getRedstoneChips().getChannelManager().getChannelByName(config.getString("PVPArena.RedChannel"), false); //get TransmiterChannel for red
							if (c == null){ //If there's no Channel like this
								Main.getLogger().info("The Channel " + config.getString("PVPArena.RedChannel") + " does not exist!");								
							} else { //transmit a bit at position 0
								Main.getLogger().info("Sended");
								c.transmit(true, 0);
								c.transmit(false, 0); //set it back to 0
							}
						}
						
						if (HeadBlockData.getData() == 11){ //Blue
							Main.getLogger().info("Wool is Blue");
							BroadcastChannel c = getRedstoneChips().getChannelManager().getChannelByName(config.getString("PVPArena.BlueChannel"), false); //get TransmiterChannel for blue
							if (c == null){ //If there's no Channel like this
								Main.getLogger().info("The Channel " + config.getString("PVPArena.BlueChannel") + " does not exist!");
								return;
							} else { //transmit a bit at position 0
								Main.getLogger().info("Sended");
								c.transmit(true, 0);
								c.transmit(false, 0); //set it back to 0
							}
						}
						
					}
				}
			}
		}
	}
	
	// get WorldGuard
	private WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = Main.getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null; // Maybe you want throw an exception instead
	    }
	 
	    return (WorldGuardPlugin) plugin;
	}
	
	//get RedstoneChips
	private RedstoneChips getRedstoneChips() {
	    Plugin plugin = Main.getServer().getPluginManager().getPlugin("RedstoneChips");
	 
	    // RedstoneChips may not be loaded
	    if (plugin == null || !(plugin instanceof RedstoneChips)) {
	    	Main.getLogger().info("RedstoneChips was not found.");
	        return null; // Maybe you want throw an exception instead
	    }
	 
	    return (RedstoneChips) plugin;
	}
}
