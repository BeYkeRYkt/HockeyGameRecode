package ykt.BeYkeRYkt.HockeyGame.API.Events;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Puck;

public class RespawnPuckEvent extends Event implements Cancellable{

    private static final HandlerList handlers = new HandlerList();
	private boolean cancel = false;
	private Puck puck;
    private Location loc;
	private Arena arena;
	
	public RespawnPuckEvent(Arena arena, Puck puck, Location loc){
		this.arena = arena;
		this.puck = puck;
		this.loc = loc;
	}
	
	public Arena getArena(){
		return arena;
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public Puck getPuck(){
		return puck;
	}
	
	public void setLocation(Location loc){
		this.loc = loc;
	}
	
	public void setPuck(Puck puck){
		this.puck = puck;
	}
	
	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean flag) {
	cancel = flag;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {     
		return handlers; 
	}
}