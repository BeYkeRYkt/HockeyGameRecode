package ykt.BeYkeRYkt.HockeyGame.API.Addons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;

/**
 * 
 * Modified version manager from SkyrimMagic v3.9
 * 
 * @author BeYkeRYkt
 *
 */
public class AddonManager {
	
	public List<Addon> addons = new ArrayList<Addon>();
	private HGAPI api;

	public AddonManager(HGAPI api){
		this.api = api;
		loadAddonAll();
	}

	public void loadAddon(Addon addon){
		enableAddon(addon);
		addons.add(addon);
	}
	
	public void enableAddon(Addon addon){
		addon.onEnable();
		addon.setEnabled(true);
		addAddonListeners(addon);
		String authors = "";
		
		if(addon.getAuthors().size() >= 2){
		for (String at : addon.getAuthors()){
			authors += ", " + at;
		}
		}else if(addon.getAuthors().size() <= 1){
			authors = addon.getAuthors().get(0);
		}

		authors.replaceFirst(", ", "");
		HGAPI.getPlugin().getLogger().info("Enabled Addon: " + addon.getName() + " made by " + authors);		
	}
	
	public void loadAddonAll(){
		File dir = new File(HGAPI.getPlugin().getDataFolder() + "/addons/");
		if (!dir.isDirectory())
			dir.mkdirs();

		for(Addon addon: AddonLoader.load(dir.getAbsolutePath())){
		   loadAddon(addon);	
		}
	}
	
	public List<Addon> getAddons(){
		return addons;
	}
	
	public Addon getAddon(String name){
		for(Addon addon: getAddons()){
			if(addon.getName().equals(name)){
				return addon;
			}
		}
		return null;
	}
	
	public void removeAddon(Addon addon){
		disableAddon(addon);
		addons.remove(addon);
	}
	
	public void disableAddon(Addon addon){
		removeAddonListeners(addon);
		addon.onDisable();
		addon.setEnabled(false);
		
		HGAPI.getPlugin().getLogger().info("Disabled Addon: " + addon.getName());	
	}

	public void addAddonListeners(Addon addon){
		for(Listener listener: addon.getListeners()){
			Bukkit.getPluginManager().registerEvents(listener, HGAPI.getPlugin());
		}
	}
	
	public void removeAddonListeners(Addon addon){	
		if(addon.getListeners() == null) return;
		 for(Iterator<Listener> it = addon.getListeners().iterator(); it.hasNext(); ){
			Listener listener = it.next();
			it.remove();
			HandlerList.unregisterAll(listener);
			addon.getListeners().remove(listener);
		}
		addon.getListeners().remove(addon.getName());
	}
}