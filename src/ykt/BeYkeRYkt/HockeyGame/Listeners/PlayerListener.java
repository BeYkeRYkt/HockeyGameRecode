package ykt.BeYkeRYkt.HockeyGame.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.GUIMenu.CustomGUIMenu;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;
import ykt.BeYkeRYkt.HockeyGame.API.Team.Team;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		if(HGAPI.getPlugin().getHArenaCommand().getCreators().contains(player)){
		  String message = event.getMessage();
		  Arena arena = new Arena(message);
		  if(!HGAPI.getPlugin().getHArenaCommand().getArenas().containsKey(player.getName()) && !HGAPI.getArenaManager().getArenas().containsKey(message)){
			  HGAPI.getPlugin().getHArenaCommand().getArenas().put(player.getName(), arena);
			  event.setCancelled(true);
			  player.sendMessage(Lang.TITLE.toString() + Lang.ENTER_NAME_THE_FIRST_TEAM);
			  
			  int size = 9 * 4;
			  CustomGUIMenu menu = new CustomGUIMenu("Select the first team", size);
				  List keys = new ArrayList(HGAPI.getTeamManager().getTeams().keySet());
				  for (int i = 0; i < keys.size(); i++) {
					    String obj = (String) keys.get(i);
						  ItemStack item = new ItemStack(Material.LEATHER_HELMET);
						  LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
						  meta.setDisplayName(HGAPI.getTeamManager().getTeam(obj).getName());
						  meta.setColor( HGAPI.getTeamManager().getTeam(obj).getColor());
						  item.setItemMeta(meta);
					    
						menu.addItem(item, keys.indexOf(obj));
					}
			  
			  
			  player.openInventory(menu.getInventory());
			  
		  }else{
			  player.sendMessage(Lang.TITLE.toString() + Lang.ARENA_NAME_IS_TAKEN.toString());
			  event.setCancelled(true);  
		  }
		}
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
	Player player = event.getPlayer();
	if(HGAPI.getPlayerManager().getPlayers().containsKey(player.getName())){
		event.setCancelled(true);
	}
  }
	
	
	@EventHandler
	public void onBlockDestroyEvent(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(HGAPI.getPlayerManager().getPlayers().containsKey(player.getName())){
			event.setCancelled(true);
		}
	}
	
	
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(HGAPI.getPlayerManager().getPlayers().containsKey(player.getName())){
			event.setCancelled(true);
		}
	}
	
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String name = player.getName();
        if(HGAPI.getPlayerManager().getPlayers().containsKey(name)){
           HockeyPlayer hplayer = HGAPI.getPlayerManager().getHockeyPlayer(name);
                      
           if(hplayer.getType().getName().equals("Winger")){
        	   hplayer.getTeam().removeWinger(hplayer);
           }else if(hplayer.getType().getName().equals("Defender")){
        	   hplayer.getTeam().removeDefend(hplayer);
           }else if(hplayer.getType().getName().equals("Goalkeeper")){
        	   hplayer.getTeam().removeGoalkeeper();
           }
           
           hplayer.getArena().getPlayers().remove(hplayer);
           hplayer.getTeam().getMembers().remove(hplayer);
           HGAPI.getPlayerManager().getPlayers().remove(name);
		}
	}
	
	@EventHandler
	  public void onDamagePlayers(EntityDamageByEntityEvent event) {
		Entity target = event.getEntity();
		if(target instanceof Player){
		Player player = (Player) target;
		String name = player.getName();
		if(HGAPI.getPlayerManager().getPlayers().containsKey(name)){
        if(player.getHealthScale() < 4){
	    player.setHealthScale(4);
        }
		}
		}
	}
}