package ykt.BeYkeRYkt.HockeyGame.API.Events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;

public class PlayerJoinArenaEvent extends Event implements Cancellable{

    private static final HandlerList handlers = new HandlerList();
	private HockeyPlayer player;
	private Arena arena;
	private boolean cancel = false;
	
	public PlayerJoinArenaEvent(HockeyPlayer player, Arena arena){
		this.player = player;
		this.arena = arena;
	}
	
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}
	
	public HockeyPlayer getPlayer(){
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
}