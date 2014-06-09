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
	
		if(seconds < 6 && seconds > 0){
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