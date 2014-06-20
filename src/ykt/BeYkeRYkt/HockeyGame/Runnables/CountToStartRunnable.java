package ykt.BeYkeRYkt.HockeyGame.Runnables;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;

public class CountToStartRunnable extends BukkitRunnable{

	private Arena arena;
	private int seconds = HGAPI.getPlugin().getConfig().getInt("Game.CountToStart");

	public CountToStartRunnable(Arena arena){
		this.arena = arena;
	}
	
	public int getSeconds(){
		return seconds;
	}
		
	@Override
	public void run() {
	
		  for(HockeyPlayer players : arena.getPlayers()){
			 if(!players.isReady()){
				 arena.getCountToStartRunnable().cancel();
			  }
		   }

			//From: PlayerListener	
			if(arena.getPlayers().size() < HGAPI.getPlugin().getConfig().getInt("Game.MinPlayers")){
				arena.stopArena();
			}
		
		if(seconds == 30){
		    for(HockeyPlayer players: arena.getPlayers()){
				HGAPI.sendMessage(players.getBukkitPlayer(), "" + ChatColor.YELLOW + seconds + ChatColor.GRAY + "...", false);
			    HGAPI.playSound(players.getBukkitPlayer(), players.getBukkitPlayer().getLocation(), Sound.ITEM_BREAK, 1, 1);
			}
		}else if(seconds == 25){
		    for(HockeyPlayer players: arena.getPlayers()){
				HGAPI.sendMessage(players.getBukkitPlayer(), "" + ChatColor.YELLOW + seconds + ChatColor.GRAY + "...", false);
			    HGAPI.playSound(players.getBukkitPlayer(), players.getBukkitPlayer().getLocation(), Sound.ITEM_BREAK, 1, 1);
			}
		}else if(seconds == 20){
			 for(HockeyPlayer players: arena.getPlayers()){
					HGAPI.sendMessage(players.getBukkitPlayer(), "" + ChatColor.YELLOW + seconds + ChatColor.GRAY + "...", false);
				    HGAPI.playSound(players.getBukkitPlayer(), players.getBukkitPlayer().getLocation(), Sound.ITEM_BREAK, 1, 1);
			}
		}else if(seconds == 15){
			 for(HockeyPlayer players: arena.getPlayers()){
					HGAPI.sendMessage(players.getBukkitPlayer(), "" + ChatColor.YELLOW + seconds + ChatColor.GRAY + "...", false);
				    HGAPI.playSound(players.getBukkitPlayer(), players.getBukkitPlayer().getLocation(), Sound.ITEM_BREAK, 1, 1);
			}
		}
		
		
		
		if(seconds < 11 && seconds > 0){
		    for(HockeyPlayer players: arena.getPlayers()){
			HGAPI.sendMessage(players.getBukkitPlayer(), "" + ChatColor.YELLOW + seconds + ChatColor.GRAY + "...", false);
		    HGAPI.playSound(players.getBukkitPlayer(), players.getBukkitPlayer().getLocation(), Sound.ITEM_BREAK, 1, 1);
		    }
		}

      if(seconds == 0){
    	  arena.broadcastMessage(Lang.GAME_STARTED.toString());
    	  arena.startArena();
	  }
      
      seconds--;
	}
	
}