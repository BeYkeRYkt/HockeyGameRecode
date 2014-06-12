package ykt.BeYkeRYkt.HockeyGame.AddonTest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Addons.Addon;

public class AddonTest extends Addon{

	@Override
	public List<String> getAuthors() {
		List<String> list = new ArrayList<String>();
		list.add("BeYkeRYkt");
		return list;
	}

	@Override
	public String getName() {
		return "AddonTest";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "1.0";
	}

	@Override
	public void onDisable() {
		getLogger().info("I'm disabled! D:");
	}

	@Override
	public void onEnable() {
		getLogger().info("I'm enabled! :D");
		
	    registerListener(new TestListener());
		
		getConfig().set("AddonTest.test", "test");
		saveConfig();
	}
	
}