package ykt.BeYkeRYkt.HockeyGame.API.Classes;

import java.util.HashMap;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Classes.Types.Defender;
import ykt.BeYkeRYkt.HockeyGame.API.Classes.Types.Goalkeeper;
import ykt.BeYkeRYkt.HockeyGame.API.Classes.Types.Winger;

public class ClassManager{
	
	private HGAPI api;
	private HashMap<String, ClassType> classes = new HashMap<String, ClassType>();
	
	public ClassManager(HGAPI api){
		this.api = api;
		init();
	}

	private void init() {
		addClass(new Defender());
		addClass(new Winger());
		addClass(new Goalkeeper());
	}
	
	public HashMap<String, ClassType> getClasses(){
		return classes;
	}
	
	public void addClass(ClassType type){
		getClasses().put(type.getName(), type);
	}
	
	public void removeClass(String name){
		getClasses().remove(name);
	}
	
	public ClassType getClass(String name){
		return getClasses().get(name);
	}
}