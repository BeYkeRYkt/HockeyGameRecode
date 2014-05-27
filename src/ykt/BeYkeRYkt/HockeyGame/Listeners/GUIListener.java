package ykt.BeYkeRYkt.HockeyGame.Listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.GUIMenu.CustomGUIMenu;
import ykt.BeYkeRYkt.HockeyGame.API.Team.Team;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;


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
					player.sendMessage(Lang.TITLE.toString() + Lang.ENTER_NAME_THE_SECOND_TEAM);
					player.closeInventory();
					int size = 9 * 4;
					CustomGUIMenu menu = new CustomGUIMenu("Select the second team", size);

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
						player.sendMessage(Lang.TITLE.toString() + Lang.FIRST_TEAM_SET_LOBBY);
						player.closeInventory();
					}
				}
			}
		}
	}
	
}