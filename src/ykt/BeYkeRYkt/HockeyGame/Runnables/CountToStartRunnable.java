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
	private int seconds = 5;

	public CountToStartRunnable(Arena arena){
		this.arena = arena;
	}
		
	@Override
	public void run() {
	
		for(HockeyPlayer players : arena.getPlayers()){
		    players.getBukkitPlayer().sendMessage(Lang.TITLE.toString() + ChatColor.YELLOW + seconds + ChatColor.GRAY + "...");
		    HGAPI.playSound(arena.getWorld(), players.getBukkitPlayer().getLocation(), Sound.ITEM_BREAK, 1, 1);
		}
		
		
      if(seconds == 0){
    	  arena.startArena();
	  }
      
      seconds--;
	}
	
}