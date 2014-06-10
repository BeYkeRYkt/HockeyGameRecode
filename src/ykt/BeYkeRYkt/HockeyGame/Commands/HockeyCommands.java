package ykt.BeYkeRYkt.HockeyGame.Commands;

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

         CustomGUIMenu menu = new CustomGUIMenu(ChatColor.DARK_AQUA + "[HockeyGame]", 9);
         menu.addItem(Icons.getArenas(), 0);
		 if(HGAPI.getPlayerManager().getHockeyPlayer(player.getName()) != null){	 
			 menu.addItem(Icons.getArenaLeave(Lang.ICON_ARENA_LEAVE.toString()), 1);
		 }
		 
		 if(player.hasPermission("hg.admin")){
		 menu.addItem(Icons.getReload(), 8);
		 menu.addItem(Icons.getChangeLang(), 2);
		 
		 //Delete arenas and teams
		 menu.addItem(Icons.getArenaManager(), 3);
		 menu.addItem(Icons.getTeamManager(), 4);
		 
		 if(HGAPI.getPlugin().getArenaCreators().contains(player)){		
		menu.addItem(Icons.getCancel(), 7);
		
		if(HGAPI.getPlugin().getDevArenas().containsKey(player.getName())){
		Arena arena = HGAPI.getPlugin().getDevArenas().get(player.getName());
		if(arena.getPuckLocation() != null){
			menu.addItem(Icons.getNextStage(), 5);
		}
		}
		
		}else if(HGAPI.getPlugin().getTeamCreators().contains(player)){
			menu.addItem(Icons.getCancel(), 7);
		}
		 }
			
		 player.openInventory(menu.getInventory());
         
		
		}
	    }else{
	    	HGAPI.sendMessage(player, Lang.NO_PERMISSION.toString(), true);
	    }
		}else if(sender instanceof ConsoleCommandSender){
			
		}
		return true;
	}
}