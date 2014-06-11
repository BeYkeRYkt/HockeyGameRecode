package ykt.BeYkeRYkt.HockeyGame.API.Signs.Types;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Signs.SignType;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;
import ykt.BeYkeRYkt.HockeyGame.API.Team.Team;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;

public class JoinSign implements SignType{

	private HGAPI api;
	
	public JoinSign(HGAPI api){
		this.api = api;
	}
	
	@Override
	public void handleCreateSign(SignChangeEvent event) {
		String arenaName = event.getLine(2);
		String teamName = event.getLine(3);
	    if(api.getArenaManager().getArena(arenaName) != null){
	    Arena arena = api.getArenaManager().getArena(arenaName);
	    if(arena.getTeam(teamName) != null){
	    Team team = arena.getTeam(teamName);
			event.setLine(0, ChatColor.RED + "[" + HGAPI.getPlugin().getName() + "]");
			//event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.SUCCESS_SIGN_CREATE.toString());
			HGAPI.sendMessage(event.getPlayer(), Lang.SUCCESS_SIGN_CREATE.toString(), false);
			event.getBlock().getState().update(true);
	    }else if(arena.getTeam(teamName) == null){
			HGAPI.sendMessage(event.getPlayer(), Lang.TEAM_DOES_NOT_EXIT.toString(), false);
			event.setCancelled(true);
			event.getBlock().breakNaturally();
	    }
	    }else if(api.getArenaManager().getArena(arenaName) == null){
			HGAPI.sendMessage(event.getPlayer(), Lang.ARENA_DOES_NOT_EXIT.toString(), false);
			event.setCancelled(true);
			event.getBlock().breakNaturally();
	    }
	}

	@Override
	public void handleClickSign(PlayerInteractEvent event) {
		// TODO Auto-generated method stub
		String arenaName = ((Sign) event.getClickedBlock().getState()).getLine(2);
		String teamName = ((Sign) event.getClickedBlock().getState()).getLine(3);
	    Arena arena = api.getArenaManager().getArena(arenaName);
	    Player player = event.getPlayer();
	    
	    if(arena != null){  	
	    	if(HGAPI.getPlayerManager().getHockeyPlayer(player.getName()) != null) return;
			boolean run = arena.isRunning();
	    	if(!run){
			   if(teamName.equals(arena.getFirstTeam().getName())){
                //Saver.save();
			    HockeyPlayer hplayer = new HockeyPlayer(player);
			    
			    if(arena.getFirstTeam().getMembers().size() < arena.getFirstTeam().getMaxMembers()){
			    arena.joinPlayer(hplayer, arena.getFirstTeam());
			    HGAPI.playSound(player, player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
			    }else{
		    		//player.sendMessage(Lang.ARENA_FULL.toString());
		    		HGAPI.sendMessage(player, Lang.ARENA_FULL.toString(), run);
			    }
			    
			   }else if(teamName.equals(arena.getSecondTeam().getName())){
				 //Saver.save();
				 HockeyPlayer hplayer = new HockeyPlayer(player);
				 
				 if(arena.getSecondTeam().getMembers().size() < arena.getSecondTeam().getMaxMembers()){
				 arena.joinPlayer(hplayer, arena.getSecondTeam());
				 HGAPI.playSound(player, player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
				 }else{
			    	//player.sendMessage(Lang.ARENA_FULL.toString());
			    	HGAPI.sendMessage(player, Lang.ARENA_FULL.toString(), run); 
				 }
			   }
	    	}else{
	    		//player.sendMessage(Lang.GAME_RUNNING.toString());
	    		HGAPI.sendMessage(player, Lang.GAME_RUNNING.toString(), run);
	    	}
	    }else{
	    	//event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.ARENA_DOES_NOT_EXIT.toString());
	    	HGAPI.sendMessage(event.getPlayer(), Lang.ARENA_DOES_NOT_EXIT.toString(), false);
	    }
	}

	@Override
	public void handleDestroy(BlockBreakEvent event) {
    	//event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.SUCCESS_SIGN_REMOVE.toString());
    	HGAPI.sendMessage(event.getPlayer(), Lang.SUCCESS_SIGN_REMOVE.toString(), false);
	}
	
}