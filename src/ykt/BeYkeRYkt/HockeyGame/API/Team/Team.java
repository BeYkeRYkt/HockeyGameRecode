package ykt.BeYkeRYkt.HockeyGame.API.Team;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;

public class Team{
	
	private String name;
	private List<HockeyPlayer> players = new ArrayList<HockeyPlayer>();
	private List<HockeyPlayer> wingers = new ArrayList<HockeyPlayer>();
	private List<HockeyPlayer> defend = new ArrayList<HockeyPlayer>();
	private HockeyPlayer goalkeeper;
	private Color color;
	
	
	public Team(String name){
		this.name = name;
	}
	
    //SYSTEM
	public int getMaxMembers(){
		return 6;
	}
	
	public String getName() {
		return name;
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public void MassTeleportToLocation(Location location) {
        for(HockeyPlayer players: getMembers()){
        	players.getBukkitPlayer().teleport(location);
        }
	}
	
	//GET MEMBERS
	public List<HockeyPlayer> getMembers(){
		return players;
	}
	
	public List<HockeyPlayer> getWingers(){
		return wingers;
	}
	
	public List<HockeyPlayer> getDefends(){
		return defend;
	}
	
	public HockeyPlayer getGoalKeeper(){
		return goalkeeper;
	}
	
	//SET MEMBERS
	public void addWinger(HockeyPlayer player){
		getWingers().add(player);
	}
	
	public void addDefend(HockeyPlayer player){
		getDefends().add(player);
	}
	
	public void setGoalkeeper(HockeyPlayer player){
		this.goalkeeper = player;
	}
	
	
	//REMOVE MEMBERS
	public void removeWinger(HockeyPlayer player){
		getWingers().remove(player);
	}
	
	public void removeDefend(HockeyPlayer player){
		getDefends().remove(player);
	}
	
	public void removeGoalkeeper(){
		this.goalkeeper = null;
	}
}