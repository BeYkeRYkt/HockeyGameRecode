package ykt.BeYkeRYkt.HockeyGame.API.Team;

import java.util.HashMap;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
public class PlayerManager{
	
	private HashMap<String, HockeyPlayer> players = new HashMap<String, HockeyPlayer>();
	private HGAPI api;
	
	public PlayerManager(HGAPI api){
		this.api = api;
		
	}
	
	public HashMap<String, HockeyPlayer> getPlayers(){
		return players;
	}

	public void addPlayer(String line, HockeyPlayer player){
		getPlayers().put(line, player);
	}
	
	public void removePlayer(String line){
		getPlayers().remove(line);
	}
	
	public HockeyPlayer getHockeyPlayer(String name){
		return getPlayers().get(name);
	}
}