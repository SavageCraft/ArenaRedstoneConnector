package de.savagecraft.ArenaRedstoneConnector.PVPArena;

import net.slipcor.pvparena.events.PAEndEvent;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.tal.redstonechips.wireless.BroadcastChannel;

import de.savagecraft.ArenaRedstoneConnector.ArenaRedstoneConnector;

public class ArenaEndListener implements Listener {
	
	public ArenaRedstoneConnector Main;	
	
	public ArenaEndListener(ArenaRedstoneConnector Main) {
		this.Main = Main;
	}
	
	@EventHandler
	public void onArenaEnd(PAEndEvent e) {
		final FileConfiguration config = Main.getConfig();
		final BroadcastChannel BlankChannel = Main.getRedstoneChips().getChannelManager().getChannelByName(config.getString("PVPArena.BlankChannel"), false);
		final BroadcastChannel ResetChannel = Main.getRedstoneChips().getChannelManager().getChannelByName(config.getString("PVPArena.ResetChannel"), false);
		BlankChannel.transmit(true, 0);		
		
		Main.getServer().getScheduler().scheduleSyncDelayedTask(Main, new Runnable() {
			   public void run() {				   
				   BlankChannel.transmit(false, 0);
				   Main.getRedstoneChips().getChannelManager().getChannelByName(config.getString("PVPArena.ReverseChannel"), false).transmit(false, 0);
				   ResetChannel.transmit(true, 0);
				   ResetChannel.transmit(false, 0);
			   }
			}, (config.getLong("PVPArena.WaitTimeInSeconds")*20L));
		
	}

}
