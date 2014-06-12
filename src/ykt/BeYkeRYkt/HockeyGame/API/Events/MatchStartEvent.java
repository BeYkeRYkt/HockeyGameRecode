package ykt.BeYkeRYkt.HockeyGame.API.Events;

import java.util.List;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;

public class MatchStartEvent extends Event implements Cancellable{

    private static final HandlerList handlers = new HandlerList();
	private List<HockeyPlayer> player;
	private Arena arena;
	private boolean cancel = false;
	
	public MatchStartEvent(List<HockeyPlayer> players, Arena arena){
		this.player = players;
		this.arena = arena;
	}
	
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}
	
	public List<HockeyPlayer> getPlayers(){
		return player;
	}
	
	public Arena getArena(){
		return arena;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean flag) {
		cancel = flag;
	}
	
	public static HandlerList getHandlerList() {     
		return handlers; 
	}
}