package ykt.BeYkeRYkt.HockeyGameTeamSkins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Addons.Addon;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;
import ykt.BeYkeRYkt.HockeyGame.API.Team.Team;
import ykt.BeYkeRYkt.HockeyGameTeamSkins.Plugins.DisguiseCraftPlugin;
import ykt.BeYkeRYkt.HockeyGameTeamSkins.Plugins.LibsDisguisesPlugin;
import ykt.BeYkeRYkt.HockeyGameTeamSkins.Plugins.iDisguisePlugin;

public class TeamSkins extends Addon{

	private static TeamSkins addon;
	private DisguisePlugin disguise;
	private boolean found = false;

	@Override
	public List<String> getAuthors() {
		List<String> list = new ArrayList<String>();
		list.add("BeYkeRYkt");
		return list;
	}

	@Override
	public String getName() {
		return "TeamSkins";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void onDisable() {
		for(HockeyPlayer player: HGAPI.getPlayerManager().getPlayers().values()){
			getDisguisePlugin().undisguisePlayer(player.getBukkitPlayer());
		}
		this.disguise = null;
	}

	@Override
	public void onEnable() {
		this.addon = this;
		FileConfiguration fc = getConfig();
		try {
		if (!new File(getDataFolder(), "config.yml").exists()) {
			fc.options().header(getName() + " v" + getVersion()
							+ "\nby BeYkeRYkt");
			for(Team team: HGAPI.getTeamManager().getTeams().values()){
				fc.addDefault("Skins." + team.getName(), "none");
			}
			
			
			fc.addDefault("Lang.Reload", "Reload config was successful!");
			fc.options().copyDefaults(true);
			saveConfig();
			fc.options().copyDefaults(false);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		initDisguisePlugin();
		registerListener(new SkinListener());
		
	}
	
	private void initDisguisePlugin() {
		if(Bukkit.getServer().getPluginManager().getPlugin("DisguiseCraft") == null) {
			getLogger().info("Not found DisguiseCraft.");
		}else{
			this.disguise = new DisguiseCraftPlugin();
			getLogger().info("Founded DisguiseCraft!");
			found = true;
			return;
		}
		
		if(Bukkit.getServer().getPluginManager().getPlugin("iDisguise") == null) {
			getLogger().info("Not found iDisguise.");
		}else{
			this.disguise = new iDisguisePlugin();
			getLogger().info("Founded iDisguise!");
			found = true;
			return;
		}
		
		if(Bukkit.getServer().getPluginManager().getPlugin("LibsDisguises") == null) {
			getLogger().info("Not found LibsDisguises.");
		}else if(Bukkit.getServer().getPluginManager().getPlugin("LibsDisguises") != null){
			this.disguise = new LibsDisguisesPlugin();
			getLogger().info("Founded LibsDisguises!");
			found = true;
			return;
		}
		
		if(!found){
			HGAPI.getAddonManager().disableAddon(this);
		}
	}

	public static TeamSkins getInstance(){
		return addon;
	}
	
	public DisguisePlugin getDisguisePlugin(){
		return disguise;
	}
	
	public void refreshConfig(){
		for(Team team: HGAPI.getTeamManager().getTeams().values()){
			if(getConfig().get("Skins." + team.getName()) == null){
				getConfig().set("Skins." + team.getName(), "none");
			}
		}
		getConfig().options().copyDefaults(true);
		saveConfig();
		getConfig().options().copyDefaults(false);
	}
	
}