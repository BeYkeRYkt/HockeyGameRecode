package ykt.BeYkeRYkt.HockeyGame.API.Signs.Types;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Classes.ClassType;
import ykt.BeYkeRYkt.HockeyGame.API.Signs.SignType;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.ItemGiver;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;

public class DefendSign implements SignType{

	private HGAPI api;
	
	public DefendSign(HGAPI api){
		this.api = api;
	}
	
	@Override
	public void handleCreateSign(SignChangeEvent event) {
		String className = event.getLine(1);
		ClassType type = api.getClassManager().getClass(className);
		
	    if(type != null){
			event.setLine(0, ChatColor.RED + "[" + HGAPI.getPlugin().getName() + "]");
			event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.SUCCESS_SIGN_CREATE.toString());
			event.getBlock().getState().update(true);
	    }else{
	    	event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.CLASS_DOES_NOT_EXIT.toString());
			event.setCancelled(true);
			event.getBlock().breakNaturally();
	    }
	}

	@Override
	public void handleClickSign(PlayerInteractEvent event) {
		String className = ((Sign) event.getClickedBlock().getState()).getLine(1);
		ClassType type = api.getClassManager().getClass(className);
	    if(type != null){
			HockeyPlayer player = api.getPlayerManager().getHockeyPlayer(event.getPlayer().getName());
	    	if(player != null){
	    		if(player.getTeam().getDefends().size() < 2){
	    		if(player.getType() != null && !player.getType().getName().equals(type.getName())){
	    		   player.getBukkitPlayer().sendMessage(Lang.TITLE.toString() + Lang.CHANGE_CLASS.toString());
	    		   player.getBukkitPlayer().getInventory().clear();
	    		   player.getBukkitPlayer().updateInventory();

	    		   if(player.getTeam().getWingers().contains(player)){
	    			   player.getTeam().removeWinger(player);
	    		   }else if(player.getTeam().getGoalKeeper().equals(player)){
	    			   player.getTeam().removeGoalkeeper();
	    		   }
	    		   
	    		   player.setType(type);
	    		   ItemGiver.setItems(player, player.getTeam().getColor());
	    		   player.getTeam().addDefend(player);
	    		}else if(player.getType() == null){
		    		   player.getBukkitPlayer().sendMessage(Lang.TITLE.toString() + Lang.CHANGE_CLASS.toString());
		    		   player.getBukkitPlayer().getInventory().clear();
		    		   player.getBukkitPlayer().updateInventory();
		    		   player.setType(type);
		    		   ItemGiver.setItems(player, player.getTeam().getColor());
		    		   player.getTeam().addDefend(player);
	    		}
	    	}else{
	    		 player.getBukkitPlayer().sendMessage(Lang.TITLE.toString() + Lang.CLASS_FULL.toString());
	    	}
	    	}
	    }else{
	    	event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.CLASS_DOES_NOT_EXIT.toString());
			event.setCancelled(true);
	    }
	}

	@Override
	public void handleDestroy(BlockBreakEvent event) {
    	event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.SUCCESS_SIGN_REMOVE.toString());
	}
	
}