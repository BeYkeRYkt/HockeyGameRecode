package ykt.BeYkeRYkt.HockeyGame;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Addons.Addon;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Team.Team;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;
import ykt.BeYkeRYkt.HockeyGame.Commands.HockeyCommands;
import ykt.BeYkeRYkt.HockeyGame.Listeners.GUIListener;
import ykt.BeYkeRYkt.HockeyGame.Listeners.PlayerListener;
import ykt.BeYkeRYkt.HockeyGame.Listeners.SignListener;

public class HG extends JavaPlugin{
	
	private HGAPI api;
	private static YamlConfiguration LANG;
	private static File LANG_FILE;
	private String lang;
	private HockeyCommands hockey;
	private HashMap<String, Arena> arenas = new HashMap<String, Arena>();
	private List<Player> teams_creators = new ArrayList<Player>();
	private List<Player> arena_creators = new ArrayList<Player>();

	private HashMap<String, Team> teams = new HashMap<String, Team>();
    
	
	@Override
	public void onEnable(){
		//Hello peoples! I'm recorded plugin by BeYkeRYkt :D
		
		//Creating and checking config
		createConfig(false);
		checkConfig();
		
		File teams = new File(getDataFolder(), "/teams/");
		if(!teams.exists()){
			teams.mkdirs();
		}
		
		File arenas = new File(getDataFolder(), "/arenas/");
		if(!arenas.exists()){
		    arenas.mkdirs();
		}
		
		initLangDir();
		
		this.lang = getConfig().getString("Lang");
		
		loadLang();
		
		this.api = new HGAPI(this);
		
		this.hockey = new HockeyCommands();
		
		Bukkit.getPluginManager().registerEvents(new SignListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
		getCommand("hockey").setExecutor(hockey);
		
		//setupPermissions(); - Removed	
		
		//mcstats
		try {
		   Metrics metrics = new Metrics(this);
		   metrics.start();
		} catch (IOException e) {
		   // Failed to submit the stats :-(
		}
		
		
		//Update
		if(this.getConfig().getBoolean("Enable-updater")){
			this.getLogger().info("Enabling update system...");
			new UpdateContainer(this.getFile());
		}
	}
	
	
	private void createConfig(boolean recreate) {
		
		//REMOVED!
		//if(recreate){
			//File file = new File(getDataFolder(), "config.yml");
			//if (!file.exists()) {
				//file.delete();
			//}
		//}
		
		PluginDescriptionFile pdfFile = this.getDescription();
		try {
			FileConfiguration fc = getConfig();
			if (!new File(getDataFolder(), "config.yml").exists()) {
				fc.options().header(
						"HockeyGame v" + pdfFile.getVersion()
								+ " Configuration" + "\nHave fun :3"
								+ "\nby BeYkeRYkt");
				fc.addDefault("Enable-updater", true);
				fc.addDefault("Lang", "English");
				fc.addDefault("Game.AutoBalance", false);
				fc.addDefault("Game.MatchTimer", 200);
				fc.addDefault("Game.CountToStart", 30);
				fc.addDefault("Game.MinPlayers", 2);
				fc.addDefault("Game.MaxPlayers", 12);
				fc.addDefault("Game.MaxWingers", 3);
				fc.addDefault("Game.MaxDefenders", 2);
				fc.addDefault("Game.MusicMatch", true);
				fc.addDefault("Game.puck.material", "RECORD_7");
				fc.addDefault("Game.PowerBeat.Winger", 0.6);
				fc.addDefault("Game.PowerBeat.Defender", 0.4);
				fc.addDefault("Game.PowerBeat.Goalkeeper", 0.3);
							
				//Rewards
				List<String> win = new ArrayList<String>();
				win.add("266:3");
				win.add("264:1");
				
				List<String> loss = new ArrayList<String>();
				loss.add("1:1");
				
				fc.addDefault("Game.Rewards.Winners", win);
				fc.addDefault("Game.Rewards.Losers", loss);
				
				//Whitelist
				List<String> list = new ArrayList<String>();
				list.add("hockey");
				list.add("msg");
				list.add("reload");
				list.add("kick");
				list.add("ban");
				
				fc.addDefault("Whitelist-commands", list);
				fc.options().copyDefaults(true);
				saveConfig();
				fc.options().copyDefaults(false);
				
				//Lang fix
				this.lang = "English";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}


	private void checkConfig() {
		reloadConfig(); //fix
		boolean save = false;
		if(getConfig().get("Enable-updater") == null){
			getConfig().set("Enable-updater", true);
			save = true;
		}
		
		if(getConfig().getString("Lang") == null){
			getConfig().set("Lang", "English");
			save = true;
		}
		
		if(getConfig().get("Game.AutoBalance") == null){
			getConfig().set("Game.AutoBalance", false);
			save = true;
		}
		
		if(getConfig().get("Game.MatchTimer") == null){
			getConfig().set("Game.MatchTimer", 200);
			save = true;
		}
		
		if(getConfig().get("Game.CountToStart") == null){
			getConfig().set("Game.CountToStart", 30);
			save = true;
		}
		
		if(getConfig().get("Game.MinPlayers") == null){
			getConfig().set("Game.MinPlayers", 2);
			save = true;
		}
		
		if(getConfig().get("Game.MaxPlayers") == null){
			getConfig().set("Game.MaxPlayers", 12);
			save = true;
		}
		
		if(getConfig().get("Game.MaxWingers") == null){
			getConfig().set("Game.MaxWingers", 3);
			save = true;
		}
		
		if(getConfig().get("Game.MaxDefenders") == null){
			getConfig().set("Game.MaxDefenders", 3);
			save = true;
		}
		
		if(!getConfig().getBoolean("Game.MusicMatch")){
			getConfig().set("Game.MusicMatch", true);
			save = true;
		}
		
		if(getConfig().get("Game.puck.material") == null){
			getConfig().set("Game.puck.material", "RECORD_7");
			save = true;
		}
		
		if(getConfig().get("Game.PowerBeat.Winger") == null){
			getConfig().set("Game.PowerBeat.Winger", 0.6);
			save = true;
		}
		
		if(getConfig().get("Game.PowerBeat.Defender") == null){
			getConfig().set("Game.PowerBeat.Defender", 0.4);
			save = true;
		}
		
		if(getConfig().get("Game.PowerBeat.Goalkeeper") == null){
			getConfig().set("Game.PowerBeat.Goalkeeper", 0.3);
			save = true;
		}
		
		if(getConfig().get("Game.Rewards.Winners") == null){
			List<String> win = new ArrayList<String>();
			win.add("266:3");
			win.add("264:1");

			getConfig().set("Game.Rewards.Winners", win);
			save = true;
		}
		
		if(getConfig().get("Game.Rewards.Losers") == null){
			List<String> loss = new ArrayList<String>();
			loss.add("1:1");

			getConfig().set("Game.Rewards.Losers", loss);
			save = true;
		}
		
		if(getConfig().get("Whitelist-commands") == null){
		List<String> list = new ArrayList<String>();
		list.add("hockey");
		list.add("msg");
		list.add("reload");
		list.add("kick");
		list.add("ban");
		
		getConfig().set("Whitelist-commands", list);
		save = true;
		}
		
		if(save){
		getConfig().options().copyDefaults(true);
		saveConfig();
		getConfig().options().copyDefaults(false);
		}
	}


	//Original code by cnaude: https://github.com/cnaude/Scavenger/blob/master/src/main/java/com/cnaude/scavenger/ScavengerConfig.java
	private void initLangDir() {
		File dataFolder = new File(getDataFolder() + "/lang/");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        ArrayList<String> langFiles = new ArrayList();
        langFiles.add("English.yml");
        langFiles.add("Russian.yml");
        langFiles.add("Portuguese.yml");
        langFiles.add("German.yml");
        

        for (String fName : langFiles) {
            File file = new File(getDataFolder() + "/lang/" + fName);
            if (!file.exists()) {
                try {
                    InputStream in = getResource("lang/" + fName);
                    //byte[] buf = new byte[1024];
                    //int len;
                    //try (OutputStream out = new FileOutputStream(file)) {
                        //while ((len = in.read(buf)) > 0) {
                           //out.write(buf, 0, len);
                        //}
                    //}
    	            if (in != null) {
	                YamlConfiguration config = YamlConfiguration.loadConfiguration(in);
	                config.save(file);
    	            }
	                
                } catch (IOException ex) {
                   getLogger().info(ex.getMessage());
                }
            }
        }
	}

	private void setupPermissions() {
		Bukkit.getPluginManager().addPermission(new Permission("hg.setup", PermissionDefault.FALSE));
		Bukkit.getPluginManager().addPermission(new Permission("hg.admin", PermissionDefault.FALSE));
		Bukkit.getPluginManager().addPermission(new Permission("hg.playing", PermissionDefault.FALSE));
	}

	@Override
	public void onDisable(){
		 for(Iterator<Addon> it = HGAPI.getAddonManager().getAddons().iterator(); it.hasNext(); ){
			   Addon addon = it.next();
			   it.remove();
			   HGAPI.getAddonManager().removeAddon(addon);
		 }

		for(Arena arena: HGAPI.getArenaManager().getArenas().values()){
			//HGAPI.getArenaManager().save(arena);
			arena.stopArena();
		}
		 		
		HandlerList.unregisterAll(this);
		this.api = null;
		this.hockey = null;
		this.lang = null;
		this.LANG = null;
		this.LANG_FILE = null;
		this.arena_creators.clear();
		this.arenas.clear();
		this.teams.clear();
		this.teams_creators.clear();
	}
	
	public void reloadLang(){	
		this.lang = getConfig().getString("Lang");
		loadLang();
	}
	
	
	public void reloadPlugin(){
		onDisable();
		onEnable();
	}
	
	public List<String> getWhitelistCommands(){
		return getConfig().getStringList("Whitelist-commands");
	}
	
	public List<String> getWinnersRewards(){
		return getConfig().getStringList("Game.Rewards.Winners");
	}
	
	public List<String> getLosersRewards(){
		return getConfig().getStringList("Game.Rewards.Losers");
	}
	
	public HockeyCommands getHockeyCommands(){
		return hockey;
	}

	public List<Player> getArenaCreators(){
		return arena_creators;
	}
	
	public List<Player> getTeamCreators(){
		return teams_creators;
	}
	
	public HashMap<String, Arena> getDevArenas(){
		return arenas;
	}
	
	public HashMap<String, Team> getDevTeams(){
		return teams;
	}
	
	/**
	 * Load the lang.yml file.
	 * @return The lang.yml config.
	 */
	
	public void loadLang() {
	    File lang = new File(getDataFolder() + "/lang/", this.lang + ".yml");
	    File dir = new File(getDataFolder(), "/lang/");
	    if (!lang.exists()) {
	        try {
	            getDataFolder().mkdir();
	            dir.mkdir();
	            lang.createNewFile();
	            InputStream defConfigStream = this.getResource("lang/" + this.lang + ".yml");
	            if (defConfigStream != null) {
	                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	                defConfig.save(lang);
	                Lang.setFile(defConfig);
	            }

	        } catch(IOException e) {
	            e.printStackTrace(); // So they notice
	            getLogger().severe("[HockeyGame] Couldn't create language file.");
	            getLogger().severe("[HockeyGame] This is a fatal error. Now reloading...");
	            dir.mkdir();
	            loadLang();
	        }
	    }
	    YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
	    for(Lang item:Lang.values()) {
	        if (conf.getString(item.getPath()) == null) {
	            conf.set(item.getPath(), item.getDefault());
	        }
	    }
	    Lang.setFile(conf);
	    LANG = conf;
	    LANG_FILE = lang;
	    try {
	        conf.save(getLangFile());
	    } catch(IOException e) {
	        getLogger().log(Level.WARNING, "HockeyGame: Failed to save " + this.lang + ".yml.");
	        getLogger().log(Level.WARNING, "HockeyGame: Report this stack trace to BeYkeRYkt.");
	        e.printStackTrace();
	    }
	}
	
	/**
	* Gets the lang.yml config.
	* @return The lang.yml config.
	*/
	public YamlConfiguration getLang() {
       return LANG;
	}

	 
	/**
	* Get the lang.yml file.
	* @return The lang.yml file.
	*/
	public File getLangFile() {
	    return LANG_FILE;
	}
}