package ykt.BeYkeRYkt.HockeyGame.API;

import ykt.BeYkeRYkt.HockeyGame.HG;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.ArenaManager;
import ykt.BeYkeRYkt.HockeyGame.API.Classes.ClassManager;
import ykt.BeYkeRYkt.HockeyGame.API.Signs.SignManager;
import ykt.BeYkeRYkt.HockeyGame.API.Team.PlayerManager;
import ykt.BeYkeRYkt.HockeyGame.API.Team.TeamManager;

public class HGAPI{
	
	private static ArenaManager arena;
	private static SignManager signs;
	private static HG plugin;
	private static ClassManager classes;
	private static PlayerManager players;
	private static TeamManager teams;
	
	public HGAPI(HG plugin){
		this.plugin = plugin;
		init();
	}
	
	public void init(){
		this.classes = new ClassManager(this);
		this.teams = new TeamManager(this);
		this.players = new PlayerManager(this);
		this.signs = new SignManager(this);
		this.arena = new ArenaManager(this);
	}
	
	public static ArenaManager getArenaManager(){
		return arena;
	}
	
	public static SignManager getSignManager(){
		return signs;
	}
	
	public static ClassManager getClassManager(){
		return classes;
	}
	
	public static PlayerManager getPlayerManager(){
		return players;
	}
	
	public static TeamManager getTeamManager(){
		return teams;
	}
	
	public static HG getPlugin(){
		return plugin;
	}
	
}