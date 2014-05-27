package ykt.BeYkeRYkt.HockeyGame.API.Signs.Types;

import org.bukkit.ChatColor;
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
	    Arena arena = api.getArenaManager().getArena(arenaName);
	    Team team = api.getTeamManager().getTeam(teamName);
	    if(arena != null && team != null){
			event.setLine(0, ChatColor.RED + "[" + HGAPI.getPlugin().getName() + "]");
			event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.SUCCESS_SIGN_CREATE.toString());
			event.getBlock().getState().update(true);
	    }else{
	    	event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.ARENA_DOES_NOT_EXIT.toString());
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
			boolean run = arena.isRunning();
	    	if(!run){
	    	  if(arena.getPlayers().size() < arena.getMaxPlayers()){
			   if(teamName.equals(arena.getFirstTeam().getName())){

			    HockeyPlayer hplayer = new HockeyPlayer(player);
			    arena.joinPlayer(hplayer, arena.getFirstTeam());
			    
			   }else if(teamName.equals(arena.getSecondTeam().getName())){
				 //Saver.save();
				 HockeyPlayer hplayer = new HockeyPlayer(player);
				    arena.joinPlayer(hplayer, arena.getSecondTeam());
			   }
	    	}else{
	    		player.sendMessage(Lang.ARENA_FULL.toString());
	    	}
	    	}else{
	    		player.sendMessage(Lang.GAME_RUNNING.toString());
	    	}
	    }else{
	    	event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.ARENA_DOES_NOT_EXIT.toString());
	    }
	}

	@Override
	public void handleDestroy(BlockBreakEvent event) {
		// TODO Auto-generated method stub
    	event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.SUCCESS_SIGN_REMOVE.toString());
	}
	
}