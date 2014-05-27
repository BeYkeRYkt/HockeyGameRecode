package ykt.BeYkeRYkt.HockeyGame.API.Team;

import org.bukkit.entity.Player;

import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Classes.ClassType;

public class HockeyPlayer{
	
	private Player player = null;
	private ClassType type = null;
	private Team team = null;
	private Arena arena = null;
	private boolean ready = false;
	
	public HockeyPlayer(Player player){
		this.player = player;
	}
	
	public Arena getArena(){
		return arena;
	}
	
	public void setArena(Arena arena){
		this.arena = arena;
	}
	
	public ClassType getType(){
		return type;
	}
	
	public void setType(ClassType type){
		this.type = type;
	}
	
	
	public Team getTeam(){
		return team;
	}
	
	public void setTeam(Team team){
		this.team = team;
	}
	
	public Player getBukkitPlayer(){
		return player;
	}
	
	public boolean isReady(){
		return ready;
	}
	
	public void setReady(boolean flag){
		this.ready = flag;
	}
}