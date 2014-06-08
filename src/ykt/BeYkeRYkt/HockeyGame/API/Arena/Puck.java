package ykt.BeYkeRYkt.HockeyGame.API.Arena;

import org.bukkit.entity.Item;

import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;

public class Puck{
	
	private Arena arena;
	private HockeyPlayer lastPlayer;
	private Item item;
	
	public Puck(Arena arena, Item item){
		this.arena = arena;
		this.item = item;
	}
	
	public HockeyPlayer getLastPlayer(){
		return lastPlayer;
	}
	
	public void setLastPlayer(HockeyPlayer player){
		this.lastPlayer = player;
	}
	
	public void clearPlayer(){
		this.lastPlayer = null;
	}
	
	public Item getItem(){
		return item;
	}
	
	public void clearItem(){
		this.item = null;
	}
	
}