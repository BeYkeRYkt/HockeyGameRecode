package ykt.BeYkeRYkt.HockeyGame.API.Events;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;

public class GoalEvent extends Event{
	
    private static final HandlerList handlers = new HandlerList();
	private HockeyPlayer player;
	private Arena arena;
	private Entity entity;
    
    public GoalEvent(HockeyPlayer player, Arena arena, Entity entity){
    	this.player = player;
    	this.arena = arena;
    	this.entity = entity;
    }
    
    public HockeyPlayer getPlayer(){
    	return player;
    }
    
    public Arena getArena(){
    	return arena;
    }
    
    public Entity getPuck(){
    	return entity;
    }
    
    
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	
	
}