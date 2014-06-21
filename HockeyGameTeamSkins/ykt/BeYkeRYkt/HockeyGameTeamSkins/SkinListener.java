package ykt.BeYkeRYkt.HockeyGameTeamSkins;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Events.MatchStartEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.PlayerAutobalanceEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.PlayerLeaveArenaEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;


public class SkinListener implements Listener{
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){
		String cmd = event.getMessage();
		Player player = event.getPlayer();
		
		if(cmd.equals("/hgts")){
			TeamSkins.getInstance().refreshConfig();
			HGAPI.sendMessage(player, ChatColor.GOLD + TeamSkins.getInstance().getConfig().getString("Lang.Reload"), true);
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onJoinDisguise(MatchStartEvent event){
		for(HockeyPlayer players: event.getPlayers()){
			if(TeamSkins.getInstance().getConfig().getString("Skins." + players.getTeam().getName()) == null) return;
			String type = TeamSkins.getInstance().getConfig().getString("Skins." + players.getTeam().getName());
			if(type.equalsIgnoreCase("none")) return;
			TeamSkins.getInstance().getDisguisePlugin().disguisePlayer(players.getBukkitPlayer(), type);
		}
	}
	
	@EventHandler
	public void onPlayerAutobalance(PlayerAutobalanceEvent event){
		HockeyPlayer player = event.getPlayer();
		TeamSkins.getInstance().getDisguisePlugin().undisguisePlayer(player.getBukkitPlayer());
		if(TeamSkins.getInstance().getConfig().getString("Skins." + event.getNewTeam().getName()) == null) return;
		String type = TeamSkins.getInstance().getConfig().getString("Skins." + event.getNewTeam().getName());
		if(type.equalsIgnoreCase("none")) return;
		TeamSkins.getInstance().getDisguisePlugin().disguisePlayer(player.getBukkitPlayer(), type);
	}

	@EventHandler
	public void onLeaveDisguise(PlayerLeaveArenaEvent event){
		HockeyPlayer player = event.getPlayer();
		TeamSkins.getInstance().getDisguisePlugin().undisguisePlayer(player.getBukkitPlayer());
	}
	
}
