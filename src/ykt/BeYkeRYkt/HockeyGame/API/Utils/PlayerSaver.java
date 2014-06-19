package ykt.BeYkeRYkt.HockeyGame.API.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;

public class PlayerSaver{
	
	//private FileConfiguration customConfig;
	//private File customFile;
	private HashMap<String, File> files = new HashMap<String, File>();
	private HashMap<String, FileConfiguration> configs = new HashMap<String, FileConfiguration>();
	
	public void reloadPlayerConfig(String playername) {
		if (!files.containsKey(playername)) {
			String folder = HGAPI.getPlugin().getDataFolder() + "/inventories";
			File file = new File(folder, playername + ".yml");
			files.put(playername, file);
		}
		YamlConfiguration customConfig = YamlConfiguration.loadConfiguration(files.get(playername));
		configs.put(playername, customConfig);

		// Look for defaults in the jar
		InputStream defConfigStream = HGAPI.getPlugin().getResource(playername + ".yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			configs.get(playername).setDefaults(defConfig);
		}
	}

	public FileConfiguration getPlayerConfig(String playername) {
		if (!configs.containsKey(playername)) {
			reloadPlayerConfig(playername);
		}
		return this.configs.get(playername);
	}

	public void savePlayerConfig(String playername) {
		if (!configs.containsKey(playername) || !files.containsKey(playername)) {
			return;
		}
		try {
			getPlayerConfig(playername).save(files.get(playername));
		} catch (IOException ex) {
			HGAPI.getPlugin().getLogger().log(Level.SEVERE,"Could not save config to " + files.get(playername), ex);
		}
	}
	
	
	public void loadAllPlayers(){
		String path = "plugins/HockeyGame/inventories" ; 

		   String files ;
		   File folder = new File ( path ) ;
		   folder.mkdirs();
		   File [ ] listOfFiles = folder. listFiles ( ) ; 
           
		   
		   for ( int i = 0 ; i < listOfFiles. length ; i ++ ) 
		   {

			   if ( listOfFiles[i].isFile( )) 
			   {
				   files = listOfFiles[i].getName();
			       if (files.endsWith(".yml"))
			       {
			 	       String name = files.replace(".yml", "");
			    	   reloadPlayerConfig(name);
			       }
			   }
		   }
	}
	
	public void savePlayer(Player player){
      //getPlayerConfig(player.getName()).set("Armor", player.getInventory().getArmorContents());
      //getPlayerConfig(player.getName()).set("Items", player.getInventory().getContents());
		
	  ItemStack[] invArmors = player.getInventory().getArmorContents();
		for (int i = 0; i < invArmors.length; i++) {
			ItemStack itemInInv = invArmors[i];
			if (itemInInv != null) if (itemInInv.getType() != Material.AIR) getPlayerConfig(player.getName()).set("Armor " + i, itemInInv);
	  }
		
		
      ItemStack[] invContents = player.getInventory().getContents();
		for (int i = 0; i < invContents.length; i++) {
			ItemStack itemInInv = invContents[i];
			if (itemInInv != null) if (itemInInv.getType() != Material.AIR) getPlayerConfig(player.getName()).set("Item " + i, itemInInv);
	  }
		
	  if(!HGAPI.checkOldMCVersion()){
	  getPlayerConfig(player.getName()).set("Health", player.getHealth());
	  }else{
	  //getPlayerConfig(player.getName()).set("Health", player.getHealth()); //ERRORS
	  }
      getPlayerConfig(player.getName()).set("Food", player.getFoodLevel());
      getPlayerConfig(player.getName()).set("Gamemode", player.getGameMode().name());
      getPlayerConfig(player.getName()).set("Location.World", player.getWorld().getName());
      getPlayerConfig(player.getName()).set("Location.X", player.getLocation().getBlockX());
      getPlayerConfig(player.getName()).set("Location.Y", player.getLocation().getBlockY());
      getPlayerConfig(player.getName()).set("Location.Z", player.getLocation().getBlockZ());
      getPlayerConfig(player.getName()).set("Location.Pitch", player.getLocation().getPitch());
      getPlayerConfig(player.getName()).set("Location.Yaw", player.getLocation().getYaw());
      savePlayerConfig(player.getName());   
      
      
      
      HGAPI.sendMessage(player, Lang.INV_SAVED.toString(), true);
     }
	
     public void loadPlayer(Player p){

    	 int ArmorinvSize = 4;
		 ItemStack[] invArmors = new ItemStack[ArmorinvSize];
		 for (int i = 0; i < ArmorinvSize; i++) {
			if (getPlayerConfig(p.getName()).contains("Armor " + i)) invArmors[i] = getPlayerConfig(p.getName()).getItemStack("Armor " + i);
			else invArmors[i] = new ItemStack(Material.AIR);
		}
		p.getInventory().setArmorContents(invArmors);
    	 
		//int invSize = 27;
		//Fix
		int invSize = 36;
		ItemStack[] invContents = new ItemStack[invSize];
		 for (int i = 0; i < invSize; i++) {
			if (getPlayerConfig(p.getName()).contains("Item " + i)) invContents[i] = getPlayerConfig(p.getName()).getItemStack("Item " + i);
			else invContents[i] = new ItemStack(Material.AIR);
		 }
		 p.getInventory().setContents(invContents);
    	 
    	 int x = getPlayerConfig(p.getName()).getInt("Location.X");
    	 int y = getPlayerConfig(p.getName()).getInt("Location.Y");
    	 int z = getPlayerConfig(p.getName()).getInt("Location.Z");
    	 float pitch = getPlayerConfig(p.getName()).getInt("Location.Pitch");
    	 float yaw = getPlayerConfig(p.getName()).getInt("Location.Yaw");
    	 
    	 Location loc = new Location(Bukkit.getWorld(getPlayerConfig(p.getName()).getString("Location.World")), x, y, z);
    	 loc.setPitch(pitch);
    	 loc.setYaw(yaw);
    	 
		 p.teleport(loc);
		 
		 if(!HGAPI.checkOldMCVersion()){
			 double health = getPlayerConfig(p.getName()).getDouble("Health");
			 p.setHealth(health);
		 }else{
		     //int health = getPlayerConfig(p.getName()).getInt("Health"); - ERRORS
		     //p.setHealth(health); - ERRORS
		 }
		 
		 int food = getPlayerConfig(p.getName()).getInt("Food");
		 p.setFoodLevel(food);
		 
		 String gamemode = getPlayerConfig(p.getName()).getString("Gamemode");
		 p.setGameMode(GameMode.valueOf(gamemode));
    	 
         files.get(p.getName()).delete();
         files.remove(p.getName());
         configs.remove(p.getName());
         
         HGAPI.sendMessage(p, Lang.INV_RESTORED.toString(), true);
      }
	
     public void checkPlayer(Player player){
    	 if(files.containsKey(player.getName()) && configs.containsKey(player.getName())){
    		 player.getInventory().clear();
    		 player.updateInventory();
    		 loadPlayer(player);
    	 }
     }
}
