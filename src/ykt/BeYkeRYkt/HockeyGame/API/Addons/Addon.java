package ykt.BeYkeRYkt.HockeyGame.API.Addons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;

public abstract class Addon{
  
	private boolean enable = true;
	private AddonLogger logger = new AddonLogger(this);
	private List<Listener> listeners = new ArrayList<Listener>();
	private List<Permission> permissions = new ArrayList<Permission>();
	private FileConfiguration newConfig = null;
	private File configFile = new File(getDataFolder(), "config.yml");
	
	public abstract String getName();
	public abstract String getVersion();
	public abstract List<String> getAuthors();
	public abstract void onEnable();
	public abstract void onDisable();
	
	public AddonLogger getLogger(){
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
	
	public List<Permission> getPermissions(){
		return permissions;
	}
	
	public void registerListener(Listener listener){
		getListeners().add(listener);
	}

	public void registerPermission(Permission permission){
		getPermissions().add(permission);
	}
	
	//Bukkit
	public FileConfiguration getConfig(){
        if (newConfig == null) {
            reloadConfig();
        }
        return newConfig;
	}
	
	//Bukkit
	public void reloadConfig() {
        newConfig = YamlConfiguration.loadConfiguration(configFile);

        final InputStream defConfigStream = getResource("config.yml");
        if (defConfigStream == null) {
            return;
        }
        
        final YamlConfiguration defConfig;
        //1.5.2
        if(HGAPI.checkOldMCVersion()){
            if (defConfigStream != null) {
                defConfig = YamlConfiguration.loadConfiguration(defConfigStream);

                newConfig.setDefaults(defConfig);
            }
         // 1.5.2  >
        }else if(!HGAPI.checkOldMCVersion()){
        
        if(FileConfiguration.UTF8_OVERRIDE) {
            defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8));
        } else {
            final byte[] contents;
            defConfig = new YamlConfiguration();
            try {
                contents = ByteStreams.toByteArray(defConfigStream);
            } catch (final IOException e) {
                getLogger().log(Level.SEVERE, "Unexpected failure reading config.yml", e);
                return;
            }

            final String text = new String(contents, Charset.defaultCharset());
            if (!text.equals(new String(contents, Charsets.UTF_8))) {
                getLogger().warning("Default system encoding may have misread config.yml from plugin jar");
            }

            try {
                defConfig.loadFromString(text);
            } catch (final InvalidConfigurationException e) {
                getLogger().log(Level.SEVERE, "Cannot load configuration from jar", e);
            }
        }
        newConfig.setDefaults(defConfig);
        }
    }

	//Bukkit
    public InputStream getResource(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        }

        try {
            URL url = AddonLoader.getClassLoader().getResource(filename);

            if (url == null) {
                return null;
            }

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException ex) {
            return null;
        }
    }

	//Bukkit
    public void saveConfig() {
        try {
            getConfig().save(configFile);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not save config to " + configFile, ex);
        }
    }
	
	public File getDataFolder(){
		return new File(HGAPI.getPlugin().getDataFolder() + "/addons/" + getName());
	}
}