package ykt.BeYkeRYkt.HockeyGame.API.Signs;

import java.util.HashMap;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Signs.Types.DefendSign;
import ykt.BeYkeRYkt.HockeyGame.API.Signs.Types.GoalKeeperSign;
import ykt.BeYkeRYkt.HockeyGame.API.Signs.Types.JoinSign;
import ykt.BeYkeRYkt.HockeyGame.API.Signs.Types.WingerSign;

public class SignManager{
	
	private HashMap<String, SignType> signs = new HashMap<String, SignType>();
	private HGAPI api;
	
	public SignManager(HGAPI api){
		this.api = api;
		
		addSign("[Join]", new JoinSign(api));
		addSign("Defender", new DefendSign(api));
		addSign("Winger", new WingerSign(api));
		addSign("Goalkeeper", new GoalKeeperSign(api));
		
	}
	
	public HashMap<String, SignType> getSigns(){
		return signs;
	}

	public void addSign(String line, SignType type){
		getSigns().put(line, type);
	}
	
	public void removeSign(String line){
		getSigns().remove(line);
	}
	
	public SignType getSign(String name){
		return getSigns().get(name);
	}
}