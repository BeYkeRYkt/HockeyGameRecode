package ykt.BeYkeRYkt.HockeyGame.AddonTest;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import ykt.BeYkeRYkt.HockeyGame.API.Events.GoalEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.MatchStartEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.MatchStopEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.PlayerJoinArenaEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.PlayerLeaveArenaEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.RespawnPuckEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.StartPlayMusicEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;

public class TestListener implements Listener{
	
	@EventHandler
	public void onPlayerJoinArena(PlayerJoinArenaEvent event){
		HockeyPlayer player = event.getPlayer();
		player.getBukkitPlayer().sendMessage("LOL! TEST!");
	}
	
	@EventHandler
	public void onPlayerLeaveArena(PlayerLeaveArenaEvent event){
		HockeyPlayer player = event.getPlayer();
		player.getBukkitPlayer().sendMessage("LOL! TEST!");
	}
	
	@EventHandler
	public void onMatchStart(MatchStartEvent event){
		for(HockeyPlayer players: event.getPlayers()){
			players.getBukkitPlayer().sendMessage("TEST MESSAGE");
		}
	}
	
	@EventHandler
	public void onMatchStop(MatchStopEvent event){
		for(HockeyPlayer players: event.getPlayers()){
			players.getBukkitPlayer().sendMessage("TEST MESSAGE");
		}
	}
	
	
	@EventHandler
	public void onGoal(GoalEvent event){
		HockeyPlayer player = event.getPlayer();
		player.getBukkitPlayer().sendMessage("Nice work!");
	}
	
	@EventHandler
	public void onRespawnPuck(RespawnPuckEvent event){
		Location loc = event.getLocation();
		event.getArena().getWorld().spawnEntity(loc, EntityType.PIG);
	}
	
	@EventHandler
	public void onPlayMusic(StartPlayMusicEvent event){
		event.setCancelled(true);
	}
	
	
}