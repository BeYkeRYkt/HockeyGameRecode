package ykt.BeYkeRYkt.HockeyGame.API.Addons;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import ykt.BeYkeRYkt.HockeyGame.HG;
import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;

public abstract class Addon{
  
	private boolean enable = true;
	private Logger logger = HGAPI.getPlugin().getLogger();
	private List<Listener> listeners = new ArrayList<Listener>();
	
	public abstract String getName();
	public abstract String getVersion();
	public abstract List<String> getAuthors();
	public abstract void onEnable();
	public abstract void onDisable();
	
	public Logger getLogger(){
		return logger;
	}
	
	public boolean isEnabled(){
		return enable;
	}
	public void setEnabled(boolean flag){
		this.enable = flag;
	}
	
	public List<Listener> getListeners(){
		return listeners;
	}
	
	public void registerListener(Listener listener){
		getListeners().add(listener);
	}
	
	public FileConfiguration getConfig(){
		return HGAPI.getPlugin().getConfig();
	}
	
	public void saveConfig(){
		getConfig().options().copyDefaults(true);
		HGAPI.getPlugin().saveConfig();
		getConfig().options().copyDefaults(false);
	}
}