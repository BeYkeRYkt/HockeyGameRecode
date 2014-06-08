package ykt.BeYkeRYkt.HockeyGame;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;
import ykt.BeYkeRYkt.HockeyGame.Commands.HArenaCommands;
import ykt.BeYkeRYkt.HockeyGame.Listeners.GUIListener;
import ykt.BeYkeRYkt.HockeyGame.Listeners.PlayerListener;
import ykt.BeYkeRYkt.HockeyGame.Listeners.SignListener;

public class HG extends JavaPlugin{
	
	private HGAPI api;
	private static YamlConfiguration LANG;
	private static File LANG_FILE;
	private String lang;
	private HArenaCommands harena;
	
	@Override
	public void onLoad(){
		this.api = new HGAPI(this);
		this.harena = new HArenaCommands();
	}
	
	@Override
	public void onEnable(){
		//Hello peoples! I'm recorded plugin by BeYkeRYkt :D
		
		PluginDescriptionFile pdfFile = this.getDescription();
		try {
			FileConfiguration fc = getConfig();
			if (!new File(getDataFolder(), "config.yml").exists()) {
				fc.options().header(
						"HockeyGame v" + pdfFile.getVersion()
								+ " Configuration" + "\nHave fun :3"
								+ "\nby BeYkeRYkt");
				fc.addDefault("Enable-updater", true);
				fc.addDefault("Debug", false);
				fc.addDefault("Lang", "English");
				fc.addDefault("Game.MatchTimer", 200);
				fc.addDefault("Game.puck.material", "RECORD_7");
				fc.addDefault("Game.MusicMatch", true);
				fc.addDefault("Game.PowerBeat.Winger", 0.6);
				fc.addDefault("Game.PowerBeat.Defender", 0.5);
				fc.addDefault("Game.PowerBeat.Goalkeeper", 0.3);
				fc.options().copyDefaults(true);
				saveConfig();
				fc.options().copyDefaults(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.lang = getConfig().getString("Lang");
		
		loadLang();
		Bukkit.getPluginManager().registerEvents(new SignListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
		getCommand("harena").setExecutor(harena);
		setupPermissions();
		
		File teams = new File(api.getPlugin().getDataFolder(), "/teams/");
		if(teams.exists()){
		HGAPI.getTeamManager().loadAllTeams();
		}else{
			teams.mkdirs();
		}
		
		File arenas = new File(api.getPlugin().getDataFolder(), "/arenas/");
		if(arenas.exists()){
		HGAPI.getArenaManager().loadAllArenas();
		}else{
			arenas.mkdirs();
		}
	}
	
	private void setupPermissions() {
		Bukkit.getPluginManager().addPermission(new Permission("hg.setup", PermissionDefault.FALSE));
	}

	@Override
	public void onDisable(){
		for(Arena arena: HGAPI.getArenaManager().getArenas().values()){
		HGAPI.getArenaManager().save(arena);
		}
	}
	
	public HArenaCommands getHArenaCommand(){
		return harena;
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
	            InputStream defConfigStream = this.getResource("/lang/" + this.lang + ".yml");
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