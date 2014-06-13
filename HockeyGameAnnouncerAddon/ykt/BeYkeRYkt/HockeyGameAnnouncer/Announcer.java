package ykt.BeYkeRYkt.HockeyGameAnnouncer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Addons.Addon;

public class Announcer extends Addon{

	@Override
	public List<String> getAuthors() {
		List<String> list = new ArrayList<String>();
		list.add("BeYkeRYkt");
		return list;
	}

	@Override
	public String getName() {
		return "Announcer";
	}

	@Override
	public String getVersion() {
		return "1.0Beta";
	}

	@Override
	public void onDisable() {
		//Null
		getLogger().info("I'm disabled! D:");
	}

	@Override
	public void onEnable() {
		FileConfiguration fc = getConfig();
		try {
		if (!new File(getDataFolder(), "config.yml").exists()) {
			fc.options().header(getName() + " v" + getVersion()
							+ "\nby BeYkeRYkt");
			fc.addDefault("Announce.BeYkeRYkt", true);
			fc.addDefault("Lang.Disable", "Announcer disabled for: ");
			fc.addDefault("Lang.Enable", "Announcer enabled for: ");
			fc.options().copyDefaults(true);
			saveConfig();
			fc.options().copyDefaults(false);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}		

		registerListener(new AnnouncerListener(this));
		getLogger().info("I'm enabled! :D");
	}
	
	public void setPlayer(Player player, boolean flag){
		getConfig().set("Announce." + player.getName(), flag);
		getConfig().options().copyDefaults(true);
		saveConfig();
		getConfig().options().copyDefaults(false);
	}
	
}