package ykt.BeYkeRYkt.HockeyGame.API.Events;

import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;

public class MatchStopEvent extends Event{

    private static final HandlerList handlers = new HandlerList();
	private List<HockeyPlayer> player;
	private Arena arena;
	
	public MatchStopEvent(List<HockeyPlayer> players, Arena arena){
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
}