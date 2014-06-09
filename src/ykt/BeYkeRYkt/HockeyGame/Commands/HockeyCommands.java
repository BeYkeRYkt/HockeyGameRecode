package ykt.BeYkeRYkt.HockeyGame.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.GUIMenu.CustomGUIMenu;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;

public class HockeyCommands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLable, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
	         if(player.hasPermission("hg.playing")){
		if(cmd.getName().equalsIgnoreCase("hockey")){
		if(args.length == 0){
         CustomGUIMenu menu = new CustomGUIMenu(ChatColor.DARK_AQUA + "[HockeyGame]", 9);
         menu.addItem(Icons.getArenas(), 0);
		 if(HGAPI.getPlayerManager().getHockeyPlayer(player.getName()) != null){	 
			 menu.addItem(Icons.getArenaLeave(Lang.ICON_ARENA_LEAVE.toString()), 2);
		 }
		 
		 if(player.hasPermission("hg.admin")){
		 menu.addItem(Icons.getReload(), 8);
		 menu.addItem(Icons.getChangeLang(), 1);
		 }
			
		 player.openInventory(menu.getInventory());
		
         
		}
		}
	    }
		}else if(sender instanceof ConsoleCommandSender){
			
		}
		return true;
	}
}