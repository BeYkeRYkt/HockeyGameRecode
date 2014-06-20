package ykt.BeYkeRYkt.HockeyGame.API.Team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Classes.ClassType;

public class HockeyPlayer{
	
	private String name;
	private ClassType type = null;
	private Team team = null;
	private Arena arena = null;
	private boolean ready = false;
	private boolean teleport = false;
	
	public HockeyPlayer(Player player){
		this.name = player.getName();
	}
	
	public String getName(){
		return name;
	}
	
	public Arena getArena(){
		return arena;
	}
	
	public boolean getAllowTeleport(){
		return teleport;
	}
	
	public void setAllowTeleport(boolean flag){
		this.teleport = flag;
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
		return Bukkit.getPlayer(name);
	}
	
	public boolean isReady(){
		return ready;
	}
	
	public void setReady(boolean flag){
		this.ready = flag;
	}
}