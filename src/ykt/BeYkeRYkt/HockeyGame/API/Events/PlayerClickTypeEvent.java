package ykt.BeYkeRYkt.HockeyGame.API.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import ykt.BeYkeRYkt.HockeyGame.API.Classes.ClassType;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;

public class PlayerClickTypeEvent extends Event{

    private static final HandlerList handlers = new HandlerList();
	private HockeyPlayer player;
	private ClassType type;
	
	public PlayerClickTypeEvent(HockeyPlayer player, ClassType type){
		this.player = player;
		this.type = type;
	}
	
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}
	
	public HockeyPlayer getPlayer(){
		return player;
	}
	
	public ClassType getPlayerClass(){
		return type;
	}
}