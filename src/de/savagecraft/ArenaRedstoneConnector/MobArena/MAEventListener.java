package de.savagecraft.ArenaRedstoneConnector.MobArena;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.tal.redstonechips.wireless.BroadcastChannel;

import com.garbagemule.MobArena.events.ArenaEndEvent;
import com.garbagemule.MobArena.events.ArenaStartEvent;
import com.garbagemule.MobArena.events.NewWaveEvent;

import de.savagecraft.ArenaRedstoneConnector.ArenaRedstoneConnector;

public class MAEventListener implements Listener{
	
	public ArenaRedstoneConnector Main;	
	public FileConfiguration config;
	
	public MAEventListener(ArenaRedstoneConnector Main) {
		this.Main = Main;
		this.config = Main.getConfig();
	}
	
	@EventHandler
	public void onArenaStart(ArenaStartEvent e){
		if (e.getArena().arenaName().equalsIgnoreCase(config.getString("MobArena1.ArenaName"))) {
			Main.getRedstoneChips().getChannelManager().getChannelByName(config.getString("MobArena1.RunningChannel"), false).transmit(true, 0);
		} else if (e.getArena().arenaName().equalsIgnoreCase(config.getString("MobArena2.ArenaName"))){
			Main.getRedstoneChips().getChannelManager().getChannelByName(config.getString("MobArena2.RunningChannel"), false).transmit(true, 0);
		}
	}
	
	@EventHandler
	public void onArenaEnd(ArenaEndEvent e){
		if (e.getArena().arenaName().equalsIgnoreCase(config.getString("MobArena1.ArenaName"))) {
			Main.getRedstoneChips().getChannelManager().getChannelByName(config.getString("MobArena1.RunningChannel"), false).transmit(false, 0);
		} else if (e.getArena().arenaName().equalsIgnoreCase(config.getString("MobArena2.ArenaName"))){
			Main.getRedstoneChips().getChannelManager().getChannelByName(config.getString("MobArena2.RunningChannel"), false).transmit(false, 0);
		}
	}
	
	@EventHandler
	public void onNewWave(NewWaveEvent e){
		if (e.getArena().arenaName().equalsIgnoreCase(config.getString("MobArena1.ArenaName"))) {
			BroadcastChannel NewWaveChannel = Main.getRedstoneChips().getChannelManager().getChannelByName(config.getString("MobArena1.NewWaveChannel"), false);
			NewWaveChannel.transmit(true, 0);
			NewWaveChannel.transmit(false, 0);
		} else if (e.getArena().arenaName().equalsIgnoreCase(config.getString("MobArena2.ArenaName"))){
			BroadcastChannel NewWaveChannel = Main.getRedstoneChips().getChannelManager().getChannelByName(config.getString("MobArena2.NewWaveChannel"), false);
			NewWaveChannel.transmit(true, 0);
			NewWaveChannel.transmit(false, 0);
		}
	}
}
