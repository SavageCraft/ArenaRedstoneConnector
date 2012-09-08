package de.savagecraft.ArenaRedstoneConnector;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.tal.redstonechips.RedstoneChips;

public class ArenaRedstoneConnector extends JavaPlugin {
	
	public Listener ArenaStartListener = new de.savagecraft.ArenaRedstoneConnector.PVPArena.ArenaStartListener(this);
	public Listener ArenaDeathListener = new de.savagecraft.ArenaRedstoneConnector.PVPArena.ArenaDeathListener(this);
	public Listener ArenaEndListener = new de.savagecraft.ArenaRedstoneConnector.PVPArena.ArenaEndListener(this);
	
	@Override
	public void onEnable(){
		this.saveDefaultConfig(); 		
		getServer().getPluginManager().registerEvents(ArenaStartListener, this); 
		getServer().getPluginManager().registerEvents(ArenaDeathListener, this); 
		getServer().getPluginManager().registerEvents(ArenaEndListener, this); 
	}
	
	public RedstoneChips getRedstoneChips() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("RedstoneChips");
	 
	    // RedstoneChips may not be loaded
	    if (plugin == null || !(plugin instanceof RedstoneChips)) {
	    	getLogger().info("RedstoneChips was not found.");
	        return null; // Maybe you want throw an exception instead
	    }
	 
	    return (RedstoneChips) plugin;
	}

}
