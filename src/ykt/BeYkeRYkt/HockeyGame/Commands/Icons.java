package ykt.BeYkeRYkt.HockeyGame.Commands;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;

//Ported from PaperRecipe
public class Icons{
	
	public static ItemStack getChangeLang(){
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(Lang.ICON_CHANGE_LANG.toString());
		
		List<String> langs = getLangList();
		langs.add(0, Lang.AVAILABLE_LANGUAGES.toString());

		meta.setLore(langs);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getLang(String name){
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(name);
		File lang = new File(HGAPI.getPlugin().getDataFolder() + "/lang/",  ChatColor.stripColor(name) + ".yml");
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
		
		List<String> langs = new ArrayList<String>();
		langs.add(Lang.AUTHOR_OF_TRANSLATION.toString());
		langs.add(ChatColor.YELLOW + conf.getString("author-translate"));
		

		meta.setLore(langs);
		item.setItemMeta(meta);
		return item;
	}
		
	public static ItemStack getArenas(){
		ItemStack item = new ItemStack(Material.SLIME_BALL);
		ItemMeta meta = item.getItemMeta();

		List<String> lore = new ArrayList<String>();
		
		lore.add(0, Lang.AVAILABLE_ARENAS.toString());
		for(String arena: HGAPI.getArenaManager().getArenas().keySet()){	
		List<String> arenas = new ArrayList<String>();	
		
        arenas.add(arena);
        
        lore.add(arenas.indexOf(arena) + 1, ChatColor.GOLD + arena);
		}
		
		meta.setLore(lore);
		meta.setDisplayName(Lang.ICON_ARENAS.toString());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getArena(String name){
		ItemStack item = new ItemStack(Material.SLIME_BALL);
		ItemMeta meta = item.getItemMeta();
		List<String> list = new ArrayList<String>();

		list.add(0, Lang.ICON_JOIN_CLICK.toString());
		list.add(1, Lang.TEAMS.toString());
		
		Arena arena = HGAPI.getArenaManager().getArena(name);
		
		list.add(2, ChatColor.WHITE + arena.getFirstTeam().getName());
		list.add(3, ChatColor.WHITE + arena.getSecondTeam().getName());
		list.add(4, Lang.PLAYERS.toString());
		
		
		if(arena.getPlayers().isEmpty()){
			list.add(5, ChatColor.YELLOW + "-");
		}else{
			
		for(HockeyPlayer players: arena.getPlayers()){
			list.add(arena.getPlayers().indexOf(players) + 5,ChatColor.YELLOW + players.getName());
		}
		}
		
		meta.setLore(list);
		meta.setDisplayName(ChatColor.YELLOW + name);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getTeam(Arena arena, String name){
		ItemStack item = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		List<String> list = new ArrayList<String>();

		list.add(0, Lang.PLAYERS.toString());
		
		if(arena.getTeam(name).getMembers().isEmpty()){
			list.add(1, ChatColor.YELLOW + "-");
		}else{
			
		for(HockeyPlayer players: arena.getTeam(name).getMembers()){
			list.add(arena.getPlayers().indexOf(players) + 1,ChatColor.YELLOW + players.getName());
		}
		}
		
		meta.setColor(arena.getTeam(name).getColor());
		meta.setLore(list);
		meta.setDisplayName(ChatColor.YELLOW + name);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getArenaLeave(String name){
		ItemStack item = new ItemStack(Material.GRASS);
		ItemMeta meta = item.getItemMeta();
		List<String> list = new ArrayList<String>();

		list.add(Lang.ICON_LEAVE_CLICK.toString());
		
		meta.setLore(list);
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
		
	public static List<String> getLangList(){
		File dir = new File(HGAPI.getPlugin().getDataFolder(), "lang");
		List<String> lang = new ArrayList<String>();

		String[] dirlist = dir.list();
		int amount = dirlist.length;
		dir.mkdir();
		
		for(int i = 0; i < amount; ++i){
			String File = dirlist[i];
			if(File.contains(".yml")){
				String name = File.replace(".yml", "");
				lang.add(i, ChatColor.YELLOW + name);
			}
		}
		return lang;
	}

	public static ItemStack getReload() {
		ItemStack item = new ItemStack(Material.SLIME_BALL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Lang.ICON_RELOAD.toString());
		item.setItemMeta(meta);
		return item;
	}
}