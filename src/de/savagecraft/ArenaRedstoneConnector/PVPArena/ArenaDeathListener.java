package de.savagecraft.ArenaRedstoneConnector.PVPArena;

import java.util.HashSet;

import net.slipcor.pvparena.arena.ArenaPlayer;
import net.slipcor.pvparena.arena.ArenaTeam;
import net.slipcor.pvparena.events.PADeathEvent;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.tal.redstonechips.wireless.BroadcastChannel;

import de.savagecraft.ArenaRedstoneConnector.ArenaRedstoneConnector;

public class ArenaDeathListener implements Listener {
	
	public ArenaRedstoneConnector Main;	
	
	public ArenaDeathListener(ArenaRedstoneConnector Main) {
		this.Main = Main;
	}
	
	@EventHandler
	public void onArenaPlayerDeath(PADeathEvent e){
		FileConfiguration config = Main.getConfig();
		Player p = e.getPlayer();
		HashSet<ArenaTeam> Teams = e.getArena().getTeams();
		for (ArenaTeam team : Teams) {
			HashSet<ArenaPlayer> Players = team.getTeamMembers();
			for (ArenaPlayer ap : Players) {
				Player cap = ap.get();
				if (cap.getDisplayName().equalsIgnoreCase(p.getDisplayName())){
					if (team.getName().equalsIgnoreCase(config.getString("PVPArena.BlueTeamName"))) {
						BroadcastChannel c = Main.getRedstoneChips().getChannelManager().getChannelByName(config.getString("PVPArena.BlueChannel"), false);
						c.transmit(true, 0);
						c.transmit(false, 0);
					} else if (team.getName().equalsIgnoreCase(config.getString("PVPArena.RedTeamName"))) {
						BroadcastChannel c = Main.getRedstoneChips().getChannelManager().getChannelByName(config.getString("PVPArena.RedChannel"), false);
						c.transmit(true, 0);
						c.transmit(false, 0);
					} else {
						Main.getLogger().info("Team " + team.getName() + " is unknown.");
					}
					return;
				}
			}
		}
		Main.getLogger().info("Player " + p.getDisplayName() + " was in no Team.");
	}

}
