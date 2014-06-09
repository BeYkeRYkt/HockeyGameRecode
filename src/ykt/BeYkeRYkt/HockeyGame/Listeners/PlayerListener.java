package ykt.BeYkeRYkt.HockeyGame.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.GUIMenu.CustomGUIMenu;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;

public class PlayerListener implements Listener{
	
	
	@EventHandler
	public void onPlayerJoinInv(PlayerJoinEvent event){
		Player player = event.getPlayer();
		HGAPI.getItemSaver().checkPlayer(player);
	}
	
	@EventHandler
	public void onPlayerGameMode(PlayerGameModeChangeEvent event){
		Player player = event.getPlayer();
		if(HGAPI.getPlayerManager().getPlayers().containsKey(player.getName())){
			HockeyPlayer hp = HGAPI.getPlayerManager().getHockeyPlayer(player.getName());
			if(hp.getArena().isRunning()){
			event.setCancelled(true);
			}
		
		}
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		String name = player.getName();
        if(HGAPI.getPlayerManager().getPlayers().containsKey(name)){
           HockeyPlayer hplayer = HGAPI.getPlayerManager().getHockeyPlayer(name);
           

           if(hplayer.getType() != null){
      		if(hplayer.getTeam().getWingers().contains(hplayer)){
       			hplayer.getTeam().removeWinger(hplayer);
    		}else if(hplayer.getTeam().getDefends().contains(hplayer)){
    			hplayer.getTeam().removeDefend(hplayer);
    		}else if(hplayer.getTeam().getGoalKeeper().equals(hplayer)){
    			hplayer.getTeam().removeGoalkeeper();
    		}
           }
      		
      		
    		hplayer.getArena().getPlayers().remove(hplayer);
    		hplayer.getTeam().getMembers().remove(hplayer);
    		HGAPI.getPlayerManager().removePlayer(hplayer.getName());
    		
    		if(hplayer.getArena().getPlayers().size() < 2){
    			hplayer.getArena().stopArena();
    		}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(block.getType() == Material.GOLD_BLOCK){
			if(HGAPI.getPlayerManager().getPlayers().containsKey(player.getName())){
				HockeyPlayer hp = HGAPI.getPlayerManager().getHockeyPlayer(player.getName());
			if(hp.getType() != null){
				if(hp.getArena().isRunning()) return;
				if(!hp.isReady()){
					hp.setReady(true);
					hp.getArena().broadcastMessage(ChatColor.YELLOW + player.getName() + Lang.PLAYER_READY.toString());
					
					//for(HockeyPlayer players: hp.getArena().getPlayers()){
					//if(players.isReady()){
					hp.getArena().startCountToStartRunnable();
					//}
					//}
					
				}else if(hp.isReady()){					
					for(HockeyPlayer players: hp.getArena().getPlayers()){
					if(players.isReady()){
					hp.getArena().getCountToStartRunnable().cancel();
					}
					}
					
					hp.setReady(false);
				}
			}else{
				HGAPI.sendMessage(player, Lang.PLAYER_NOT_READY.toString(), true);
			}
			}
			}
		}
		
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK ){
			if(HGAPI.getPlayerManager().getPlayers().containsKey(player.getName())){
				HockeyPlayer hp = HGAPI.getPlayerManager().getHockeyPlayer(player.getName());
			if(player.getItemInHand()!= null){
			
			for(Entity entity: player.getNearbyEntities(2, 2, 2)){
			if(entity instanceof Item){

				Item i = (Item) entity;
				
				if(i.getEntityId() == hp.getArena().getPuckEntity().getItem().getEntityId()){
				double speedbonus = 0.3;
				double beat = 0;
	 		    Vector vi = player.getEyeLocation().getDirection();
	 		    hp.getArena().getPuckEntity().setLastPlayer(hp);
				
				if(hp.getType().getName().equals("Winger")){
					
					beat = HGAPI.getPlugin().getConfig().getDouble("Game.PowerBeat.Winger");
					
					if(player.isSprinting()){
		    		   beat = speedbonus * beat;
					}
		    			 
		    		 if(player.isSneaking()){		    
		    			 vi.multiply(0.8);
		    		 }
		    		 
				}else if(hp.getType().getName().equals("Defender")){
					beat = HGAPI.getPlugin().getConfig().getDouble("Game.PowerBeat.Defender");
					
					if(player.isSprinting()){
		    		   beat = speedbonus * beat;
					}
		    			 
		    		 if(player.isSneaking()){		    
		    			 vi.multiply(0.8);
		    		 }
				}else if(hp.getType().getName().equals("Goalkeeper")){
					beat = HGAPI.getPlugin().getConfig().getDouble("Game.PowerBeat.Goalkeeper");
					
					if(player.isSprinting()){
		    		   beat = speedbonus * beat;
					}
		    			 
		    		 if(player.isSneaking()){		    
		    			 vi.multiply(0.8);
		    		 }
				}
				
				 vi.multiply(beat);
	    		 
	    		 entity.setVelocity(vi);
			}
				}
			}
			}
		}
		}
	}
	
	
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		if(HGAPI.getPlugin().getHArenaCommand().getCreators().contains(player)){
		  String message = event.getMessage();
		  Arena arena = new Arena(message, player.getWorld());
		  if(!HGAPI.getPlugin().getHArenaCommand().getArenas().containsKey(player.getName()) && !HGAPI.getArenaManager().getArenas().containsKey(message)){
			  HGAPI.getPlugin().getHArenaCommand().getArenas().put(player.getName(), arena);
			  event.setCancelled(true);
			  //player.sendMessage(Lang.TITLE.toString() + Lang.ENTER_NAME_THE_FIRST_TEAM);
			  HGAPI.sendMessage(player, Lang.ENTER_NAME_THE_FIRST_TEAM.toString(), true);
			  
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
			  //player.sendMessage(Lang.TITLE.toString() + Lang.ARENA_NAME_IS_TAKEN.toString());
			  HGAPI.sendMessage(player, Lang.ARENA_NAME_IS_TAKEN.toString(), true);
			  event.setCancelled(true);  
		  }
		}
	}
	

	@EventHandler
	public void onPlayerPickup(PlayerPickupItemEvent event) {
	Player player = event.getPlayer();
	if(HGAPI.getPlayerManager().getPlayers().containsKey(player.getName())){
		HockeyPlayer hp = HGAPI.getPlayerManager().getHockeyPlayer(player.getName());
		if(event.getItem() == null || hp.getArena().getPuckEntity().getItem() == null) return;
		if(event.getItem().getEntityId() == hp.getArena().getPuckEntity().getItem().getEntityId()){
		event.setCancelled(true);
		}
	}
  }
	    
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent event) {
	Player player = event.getPlayer();
	if(HGAPI.getPlayerManager().getPlayers().containsKey(player.getName())){
		event.setCancelled(true);
	}
  }

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
	Player player = event.getPlayer();
	if(HGAPI.getPlayerManager().getPlayers().containsKey(player.getName())){
		HockeyPlayer hp = HGAPI.getPlayerManager().getHockeyPlayer(player.getName());
		if(hp.getArena().isRunning()){
		event.setCancelled(true);
		}
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
                      
           //hplayer.getArena().leavePlayer(hplayer);

        if(hplayer.getType() != null){
   		if(hplayer.getTeam().getWingers().contains(hplayer)){
   			hplayer.getTeam().removeWinger(hplayer);
		}else if(hplayer.getTeam().getDefends().contains(hplayer)){
			hplayer.getTeam().removeDefend(hplayer);
		}else if(hplayer.getTeam().getGoalKeeper().equals(hplayer)){
			hplayer.getTeam().removeGoalkeeper();
		}
        }
   		
		hplayer.getArena().getPlayers().remove(hplayer);
		hplayer.getTeam().getMembers().remove(hplayer);
		HGAPI.getPlayerManager().removePlayer(hplayer.getName());
		
		if(hplayer.getArena().getPlayers().size() < 2){
			hplayer.getArena().stopArena();
		}
		
		}
	}
	
	
	@EventHandler
	  public void onDamagePlayers(EntityDamageByEntityEvent event) {
		Entity target = event.getEntity();
		if(target instanceof Player){
		Player player = (Player) target;
		String name = player.getName();
		if(HGAPI.getPlayerManager().getPlayers().containsKey(name)){
        if(player.getHealth() < 4){
	    player.setHealth(4);
        }
		}
		}
	}
}