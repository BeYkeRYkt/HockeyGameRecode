package ykt.BeYkeRYkt.HockeyGame.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Team.Team;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;

public class HTeamCommands implements CommandExecutor{

	private HashMap<String, Team> teams = new HashMap<String, Team>();
	private List<Player> creators = new ArrayList<Player>();
	
	public List<Player> getCreators(){
		return creators;
	}
	
	public HashMap<String, Team> getTeams(){
		return teams;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLable, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
	         if(player.hasPermission("hg.admin")){
		if(cmd.getName().equalsIgnoreCase("hteam")){
			if(!getCreators().contains(player)){
			//player.sendMessage(Lang.TITLE.toString() + Lang.START_CREATE_ARENA.toString());
			HGAPI.sendMessage(player, Lang.START_CREATE_TEAM.toString(), true);
			getCreators().add(player);			
			}
		}
	    }else{
	    	HGAPI.sendMessage(player, Lang.NO_PERMISSION.toString(), true);
	    }
		}else if(sender instanceof ConsoleCommandSender){
			
		}
		return true;
	}
}