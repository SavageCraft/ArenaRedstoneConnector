package de.savagecraft.ArenaRedstoneConnector;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ArenaRedstoneConnector extends JavaPlugin {
	
	public Listener DeathListener = new DeathListener(this); //Initializing our DeathListener using the Constructor
	
	@Override
	public void onEnable(){
		this.saveDefaultConfig(); //If there's no Config, copy the DefaultConfig out of the Jar.
		getServer().getPluginManager().registerEvents(DeathListener, this); //registering our DeathListener
	}

}
