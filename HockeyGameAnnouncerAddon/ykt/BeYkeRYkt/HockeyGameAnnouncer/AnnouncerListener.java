package ykt.BeYkeRYkt.HockeyGameAnnouncer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Events.GoalEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.MatchStartEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.MatchStopEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.PlayerJoinArenaEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.PlayerLeaveArenaEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;

public class AnnouncerListener implements Listener{
	
	private Announcer addon;
	
	public AnnouncerListener(Announcer announcer){
		this.addon = announcer;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if(addon.getConfig().get("Announce." + player.getName()) == null){
			addon.setPlayer(player, true);
			HGAPI.sendMessage(player, ChatColor.GOLD + addon.getConfig().getString("Lang.Enable") + ChatColor.RED + player.getName(), true);
		}
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){
		String cmd = event.getMessage();
		Player player = event.getPlayer();
		
		if(cmd.equals("/hgan")){
		if(addon.getConfig().getBoolean("Announce." + player.getName())){
			addon.setPlayer(player, false);
			HGAPI.sendMessage(player, ChatColor.GOLD + addon.getConfig().getString("Lang.Disable") + ChatColor.RED + player.getName(), false);
			event.setCancelled(true);
		}else if(!addon.getConfig().getBoolean("Announce." + player.getName())){
			addon.setPlayer(player, true);
			HGAPI.sendMessage(player, ChatColor.GOLD + addon.getConfig().getString("Lang.Enable") + ChatColor.RED + player.getName(), false);
			event.setCancelled(true);
		}
		}
	}

	@EventHandler
	public void onMatchStart(MatchStartEvent event){
			for(Player players: Bukkit.getOnlinePlayers()){
				if(HGAPI.getPlayerManager().getHockeyPlayer(players.getName()) == null){
				if(addon.getConfig().getBoolean("Announce." + players.getName())){
					HGAPI.sendMessage(players, ChatColor.YELLOW + "[" + ChatColor.WHITE + event.getArena().getName() + ChatColor.YELLOW + "] " + Lang.GAME_STARTED.toString(), true);
				}
				}
		}
	}

	@EventHandler
	public void onMatchStop(MatchStopEvent event){
			for(Player players: Bukkit.getOnlinePlayers()){
				for(HockeyPlayer player: event.getPlayers()){
				if(HGAPI.getPlayerManager().getHockeyPlayer(players.getName()) == null){
				if(addon.getConfig().getBoolean("Announce." + players.getName())){
					startRewards(event.getArena(), players);
				}
				}
			    }
		}
	}
	
	public void startRewards(Arena arena, Player player){
		   //From HockeyGameRecode
				if(arena.getFirstTeamScores() > arena.getSecondTeamScores()){
					HGAPI.sendMessage(player,ChatColor.YELLOW + "[" + ChatColor.WHITE + arena.getName() + ChatColor.YELLOW + "] " + ChatColor.GOLD + arena.getFirstTeam().getName() + Lang.TEAM_WIN.toString(), true);
					HGAPI.sendMessage(player,ChatColor.YELLOW + "[" + ChatColor.WHITE + arena.getName() + ChatColor.YELLOW + "] " + Lang.RESULT.toString() + ChatColor.RED + arena.getFirstTeamScores() + ChatColor.WHITE + " : " + ChatColor.BLUE + arena.getSecondTeamScores(), true);
				}else if(arena.getFirstTeamScores() < arena.getSecondTeamScores()){
					HGAPI.sendMessage(player,ChatColor.YELLOW + "[" + ChatColor.WHITE + arena.getName() + ChatColor.YELLOW + "] " + ChatColor.GOLD + arena.getSecondTeam().getName() + Lang.TEAM_WIN.toString(), true);
					HGAPI.sendMessage(player,ChatColor.YELLOW + "[" + ChatColor.WHITE + arena.getName() + ChatColor.YELLOW + "] " + Lang.RESULT.toString() + ChatColor.RED + arena.getFirstTeamScores() + ChatColor.WHITE + " : " + ChatColor.BLUE + arena.getSecondTeamScores(), true);
				}else if(arena.getFirstTeamScores() == arena.getSecondTeamScores()){
					HGAPI.sendMessage(player,ChatColor.YELLOW + "[" + ChatColor.WHITE + arena.getName() + ChatColor.YELLOW + "] " + Lang.TIE.toString(), true);
					HGAPI.sendMessage(player,ChatColor.YELLOW + "[" + ChatColor.WHITE + arena.getName() + ChatColor.YELLOW + "] " + Lang.RESULT.toString() + ChatColor.RED + arena.getFirstTeamScores() + ChatColor.WHITE + " : " + ChatColor.BLUE +arena.getSecondTeamScores(), true);
				}
		
	}
}