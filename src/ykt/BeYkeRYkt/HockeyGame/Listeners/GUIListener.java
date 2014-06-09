package ykt.BeYkeRYkt.HockeyGame.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.GUIMenu.CustomGUIMenu;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;
import ykt.BeYkeRYkt.HockeyGame.API.Team.Team;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;
import ykt.BeYkeRYkt.HockeyGame.Commands.Icons;


public class GUIListener implements Listener{
	
	
	/**
	 * 
	 * FOR GUI
	 * 
	 *
	 */
	
	@EventHandler
	public void onStandartClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked(); 
		ItemStack clicked = event.getCurrentItem(); 
		Inventory inventory = event.getInventory(); 
		String name = inventory.getTitle();
		
		if(clicked != null && clicked.getType() != Material.AIR){
			if(HGAPI.getPlugin().getName().equals(name)){
            //Плагин меню короч
				
			}else if("Select the first team".equals(name)){
				//Команды короч
				for(Team teams: HGAPI.getTeamManager().getTeams().values()){
				if(teams.getName().equals(clicked.getItemMeta().getDisplayName())){
					Team team = HGAPI.getTeamManager().getTeam(clicked.getItemMeta().getDisplayName());
					Arena arena = HGAPI.getPlugin().getHArenaCommand().getArenas().get(player.getName());
					arena.setFirstTeam(team);	
					//player.sendMessage(Lang.TITLE.toString() + Lang.ENTER_NAME_THE_SECOND_TEAM);
					HGAPI.sendMessage(player, Lang.ENTER_NAME_THE_SECOND_TEAM.toString(), true);
					
					player.closeInventory();
					int size = 9 * 4;
					CustomGUIMenu menu = new CustomGUIMenu("Select the second team", size);

					  List keys = new ArrayList(HGAPI.getTeamManager().getTeams().keySet());
					  
					  //Experimental
					  keys.remove(arena.getFirstTeam().getName());
					  
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
				}
				}
				
				event.setCancelled(true);
			}else if("Select the second team".equals(name)){
				//Команды короч
				for(Team teams: HGAPI.getTeamManager().getTeams().values()){
					if(teams.getName().equals(clicked.getItemMeta().getDisplayName())){
						Team team = HGAPI.getTeamManager().getTeam(clicked.getItemMeta().getDisplayName());
						Arena arena = HGAPI.getPlugin().getHArenaCommand().getArenas().get(player.getName());
						arena.setSecondTeam(team);	
						//player.sendMessage(Lang.TITLE.toString() + Lang.FIRST_TEAM_SET_LOBBY);
						HGAPI.sendMessage(player, Lang.FIRST_TEAM_SET_LOBBY.toString(), true);
						player.closeInventory();
					}
				}
				event.setCancelled(true);
			}else if((ChatColor.DARK_AQUA + "[HockeyGame]").equals(name)){
				if(Icons.getChangeLang().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					player.closeInventory();
					
					CustomGUIMenu menu = new CustomGUIMenu(Icons.getChangeLang().getItemMeta().getDisplayName(), 45);
					
					for(String lang: Icons.getLangList()){
						menu.addItem(Icons.getLang(lang) ,Icons.getLangList().indexOf(lang));
					}
					
					player.openInventory(menu.getInventory());
					
				}else if(Icons.getArenas().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					player.closeInventory();
					
					CustomGUIMenu menu = new CustomGUIMenu(Icons.getArenas().getItemMeta().getDisplayName(), 45);
					
					List<String> musor = new ArrayList<String>();
					
					for(String arenas: HGAPI.getArenaManager().getArenas().keySet()){
						musor.add(arenas);
					}
					
					for(String arena: musor){
					menu.addItem(Icons.getArena(arena), musor.indexOf(arena));
					}
					
					player.openInventory(menu.getInventory());
				}else if(Icons.getReload().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					player.closeInventory();
					HGAPI.getPlugin().reloadPlugin();
					HGAPI.sendMessage(player, Lang.PLUGIN_RESTARTED.toString(), true);
				}else if(Icons.getArenaLeave(Lang.ICON_ARENA_LEAVE.toString()).getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					HockeyPlayer hplayer = HGAPI.getPlayerManager().getHockeyPlayer(player.getName());
					hplayer.getArena().leavePlayer(hplayer);	
				}
				
				event.setCancelled(true);
			}else if(Icons.getChangeLang().getItemMeta().getDisplayName().equals(name)){ 
				
				if(Icons.getLangList().contains(clicked.getItemMeta().getDisplayName())){
				
				HGAPI.getPlugin().getConfig().set("Lang", ChatColor.stripColor(clicked.getItemMeta().getDisplayName()));
				HGAPI.getPlugin().getConfig().options().copyDefaults(true);
				HGAPI.getPlugin().saveConfig();
				HGAPI.getPlugin().getConfig().options().copyDefaults(false);
				HGAPI.getPlugin().reloadLang();
				player.closeInventory();
				HGAPI.sendMessage(player, Lang.PLUGIN_RESTARTED.toString(), true);
			    }
				
				event.setCancelled(true);
			}else if(Icons.getArenas().getItemMeta().getDisplayName().equals(name)){
				
				if(HGAPI.getArenaManager().getArenas().keySet().contains(ChatColor.stripColor(clicked.getItemMeta().getDisplayName()))){
					
					Arena arena = HGAPI.getArenaManager().getArena(ChatColor.stripColor(clicked.getItemMeta().getDisplayName()));
					
					if(arena.isRunning()){
						player.closeInventory();
						HGAPI.sendMessage(player, Lang.GAME_RUNNING.toString(), true);
					}else if(arena.getPlayers().size() == arena.getMaxPlayers()){
						player.closeInventory();
						HGAPI.sendMessage(player, Lang.ARENA_FULL.toString(), true); 
					}else{
						CustomGUIMenu menu = new CustomGUIMenu(clicked.getItemMeta().getDisplayName(), 9);
						
						menu.addItem(Icons.getTeam(arena, arena.getFirstTeam().getName()), 0);
						menu.addItem(Icons.getTeam(arena, arena.getSecondTeam().getName()), 1);
						
						player.openInventory(menu.getInventory());
					}
				}
				
				event.setCancelled(true);
			}else if(HGAPI.getArenaManager().getArenas().containsKey(ChatColor.stripColor(name))){
				if(HGAPI.getTeamManager().getTeams().containsKey(ChatColor.stripColor(clicked.getItemMeta().getDisplayName()))){
			    	if(HGAPI.getPlayerManager().getHockeyPlayer(player.getName()) != null) return;
					Arena arena = HGAPI.getArenaManager().getArena(ChatColor.stripColor(name));
					Team team = arena.getTeam(ChatColor.stripColor(clicked.getItemMeta().getDisplayName()));
					
				    HockeyPlayer hplayer = new HockeyPlayer(player);
					
					arena.joinPlayer(hplayer, team);
				    HGAPI.playSound(player, player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
				    
				    player.closeInventory();
				}
				
				event.setCancelled(true);
			}
		}
	}
	
}