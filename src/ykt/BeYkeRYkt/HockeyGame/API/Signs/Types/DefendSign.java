package ykt.BeYkeRYkt.HockeyGame.API.Signs.Types;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
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
	    if(api.getClassManager().getClass(className) != null){
			event.setLine(0, ChatColor.RED + "[" + HGAPI.getPlugin().getName() + "]");
			//event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.SUCCESS_SIGN_CREATE.toString());
			HGAPI.sendMessage(event.getPlayer(), Lang.SUCCESS_SIGN_CREATE.toString(), false);
			event.getBlock().getState().update(true);
	    }else{
	    	//event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.CLASS_DOES_NOT_EXIT.toString());
	    	HGAPI.sendMessage(event.getPlayer(), Lang.CLASS_DOES_NOT_EXIT.toString(), false);
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
		    		   //player.getBukkitPlayer().sendMessage(Lang.TITLE.toString() + Lang.CHANGE_CLASS.toString());
		    		   HGAPI.sendMessage(player.getBukkitPlayer(), Lang.CHANGE_CLASS.toString(), false);
		    		   HGAPI.playSound(player.getBukkitPlayer(), player.getBukkitPlayer().getLocation(), Sound.HORSE_ARMOR, 1, 1);
	    		   player.getBukkitPlayer().getInventory().clear();
	    		   player.getBukkitPlayer().updateInventory();

	    		   if(player.getTeam().getWingers().contains(player)){
	    			   player.getTeam().removeWinger(player);
	    		   }else if(player.getTeam().getGoalKeeper() != null && player.getTeam().getGoalKeeper().equals(player)){
	    			   player.getTeam().removeGoalkeeper();
	    		   }
	    		   
	    		   player.setType(type);
	    		   ItemGiver.setItems(player, player.getTeam().getColor());
	    		   player.getTeam().addDefend(player);
	    		}else if(player.getType() == null){
		    		   //player.getBukkitPlayer().sendMessage(Lang.TITLE.toString() + Lang.CHANGE_CLASS.toString());
		    		   HGAPI.sendMessage(player.getBukkitPlayer(), Lang.CHANGE_CLASS.toString(), false);
		    		   HGAPI.playSound(player.getBukkitPlayer(), player.getBukkitPlayer().getLocation(), Sound.HORSE_ARMOR, 1, 1);
		    		   player.getBukkitPlayer().getInventory().clear();
		    		   player.getBukkitPlayer().updateInventory();
		    		   player.setType(type);
		    		   ItemGiver.setItems(player, player.getTeam().getColor());
		    		   player.getTeam().addDefend(player);
	    		}

				if(!player.isReady()){
					player.setReady(true);
					player.getArena().broadcastMessage(ChatColor.YELLOW + player.getName() + Lang.PLAYER_READY.toString());
					
					player.getArena().startCountToStartRunnable();
				}
	    		
		    	}else{
		    		 //player.getBukkitPlayer().sendMessage(Lang.TITLE.toString() + Lang.CLASS_FULL.toString());
		    		 HGAPI.sendMessage(player.getBukkitPlayer(),  Lang.CLASS_FULL.toString(), false);
		    	}
		    	}
		    }else{
		    	//event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.CLASS_DOES_NOT_EXIT.toString());
		    	HGAPI.sendMessage(event.getPlayer(), Lang.CLASS_DOES_NOT_EXIT.toString(), false);
		    }
		}

		@Override
		public void handleDestroy(BlockBreakEvent event) {
	    	//event.getPlayer().sendMessage(Lang.TITLE.toString() + Lang.SUCCESS_SIGN_REMOVE.toString());
			HGAPI.sendMessage(event.getPlayer(), Lang.SUCCESS_SIGN_REMOVE.toString(), false);
		}
	
}