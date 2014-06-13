package ykt.BeYkeRYkt.HockeyGame.API.Events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;

public class StartPlayMusicEvent extends Event implements Cancellable{

    private static final HandlerList handlers = new HandlerList();
	private boolean cancel = false;
	private Material record;
	private Location loc;
	private Arena arena;
	
	public StartPlayMusicEvent(Arena arena, Location loc, Material record){
		this.arena = arena;
		this.loc = loc;
		setRecord(record);
	}
	
	public Arena getArena(){
		return arena;
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public Material getRecord(){
		return record;
	}
	
	public void setRecord(Material record){
		this.record = record;
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