package de.savagecraft.ArenaRedstoneConnector.PVPArena;

import java.util.HashSet;

import net.slipcor.pvparena.arena.ArenaPlayer;
import net.slipcor.pvparena.arena.ArenaTeam;
import net.slipcor.pvparena.events.PAStartEvent;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.tal.redstonechips.wireless.BroadcastChannel;

import de.savagecraft.ArenaRedstoneConnector.ArenaRedstoneConnector;

public class ArenaStartListener implements Listener {
	
	private int BluePlayers = 0;
	private int RedPlayers = 0;	
	public ArenaRedstoneConnector Main;	
	
	public ArenaStartListener(ArenaRedstoneConnector Main) {
		this.Main = Main;
	}
	
	@EventHandler
	public void onArenaStart(PAStartEvent e){
		FileConfiguration config = Main.getConfig();
		HashSet<ArenaTeam> Teams = e.getArena().getTeams();
		for (ArenaTeam t : Teams) {			
			HashSet<ArenaPlayer> Players = t.getTeamMembers();			
			if (t.getName().equalsIgnoreCase(config.getString("PVPArena.BlueTeamName"))){
				BluePlayers = Players.size();
			} else if (t.getName().equalsIgnoreCase(config.getString("PVPArena.RedTeamName"))) {
				RedPlayers = Players.size();
			} else {
				Main.getLogger().info("Unkown Team: " + t.getName());
			}
		}
		BroadcastChannel BlueChannel = Main.getRedstoneChips().getChannelManager().getChannelByName(config.getString("PVPArena.BlueChannel"), false);
		for (int i = 0; i < BluePlayers; i++){
			BlueChannel.transmit(true, 0);
			BlueChannel.transmit(false, 0);
		}
		BroadcastChannel RedChannel = Main.getRedstoneChips().getChannelManager().getChannelByName(config.getString("PVPArena.RedChannel"), false);
		for (int i = 0; i < RedPlayers; i++){
			RedChannel.transmit(true, 0);
			RedChannel.transmit(false, 0);
		}
		Main.getRedstoneChips().getChannelManager().getChannelByName(config.getString("PVPArena.ReverseChannel"), false).transmit(true, 0);
	}
}
