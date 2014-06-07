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
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;

public class HArenaCommands implements CommandExecutor{

	private HashMap<String, Arena> arenas = new HashMap<String, Arena>();
	private List<Player> creators = new ArrayList<Player>();
	
	
	public List<Player> getCreators(){
		return creators;
	}
	
	public HashMap<String, Arena> getArenas(){
		return arenas;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLable, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("harena")){
		if(args.length == 0){
			if(!getCreators().contains(player)){
			//player.sendMessage(Lang.TITLE.toString() + Lang.START_CREATE_ARENA.toString());
			HGAPI.sendMessage(player, Lang.START_CREATE_ARENA.toString());
			getCreators().add(player);			
			}
		}else if(args.length == 1){// others 
			if(args[0].equalsIgnoreCase("setlobby")){
				if(getCreators().contains(player) && getArenas().containsKey(player.getName())){
					Arena arena = getArenas().get(player.getName());
					if(arena.getFirstTeamLobbyLocation() == null){
					arena.setFirstTeamLobbyLocation(player.getLocation());
					//player.sendMessage(Lang.TITLE.toString() + Lang.SECOND_TEAM_SET_LOBBY.toString());
					HGAPI.sendMessage(player, Lang.SECOND_TEAM_SET_LOBBY.toString());
					}else if(arena.getFirstTeamLobbyLocation() != null & arena.getSecondTeamLobbyLocation() == null){
					arena.setSecondTeamLobbyLocation(player.getLocation());
					//player.sendMessage(Lang.TITLE.toString() + Lang.FIRST_TEAM_SET_SPAWN.toString());
					HGAPI.sendMessage(player, Lang.FIRST_TEAM_SET_SPAWN.toString());
					}
				}
			}else if(args[0].equalsIgnoreCase("setspawn")){
				if(getCreators().contains(player) && getArenas().containsKey(player.getName())){
					Arena arena = getArenas().get(player.getName());
					if(arena.getFirstTeamSpawnLocation() == null){
						arena.setFirstTeamSpawnLocation(player.getLocation());
						//player.sendMessage(Lang.TITLE.toString() + Lang.SECOND_TEAM_SET_SPAWN.toString());
						HGAPI.sendMessage(player, Lang.SECOND_TEAM_SET_SPAWN.toString());
						}else if(arena.getFirstTeamSpawnLocation() != null & arena.getSecondTeamSpawnLocation() == null){
						arena.setSecondTeamSpawnLocation(player.getLocation());
						//player.sendMessage(Lang.TITLE.toString() + Lang.PUCK_SET_SPAWN.toString());
						HGAPI.sendMessage(player, Lang.PUCK_SET_SPAWN.toString());
					}
				}
			}else if(args[0].equalsIgnoreCase("setpuckspawn")){
				if(getCreators().contains(player) && getArenas().containsKey(player.getName())){
					Arena arena = getArenas().get(player.getName());
					arena.setPuckLocation(player.getLocation());
					//player.sendMessage(Lang.TITLE.toString() + Lang.SET_GATES.toString());
					HGAPI.sendMessage(player, Lang.SET_GATES.toString());
				}
			}else if(args[0].equalsIgnoreCase("setfirstgate")){
				if(getCreators().contains(player) && getArenas().containsKey(player.getName())){
					Arena arena = getArenas().get(player.getName());
					arena.addFirstTeamGate(player.getLocation());
					//player.sendMessage(Lang.TITLE.toString() + Lang.GATE_STORED.toString());
					HGAPI.sendMessage(player, Lang.GATE_STORED.toString());
				}
			}else if(args[0].equalsIgnoreCase("setsecondgate")){
				if(getCreators().contains(player) && getArenas().containsKey(player.getName())){
					Arena arena = getArenas().get(player.getName());
					arena.addSecondTeamGate(player.getLocation());
					//player.sendMessage(Lang.TITLE.toString() + Lang.GATE_STORED.toString());
					HGAPI.sendMessage(player, Lang.GATE_STORED.toString());
				}
			}else if(args[0].equalsIgnoreCase("save")){
				if(getCreators().contains(player) && getArenas().containsKey(player.getName())){
					Arena arena = getArenas().get(player.getName());
					saveArena(arena, player);
				}
			}else if(args[0].equalsIgnoreCase("cancel")){
				if(getCreators().contains(player) && getArenas().containsKey(player.getName())){
					this.getArenas().remove(player.getName());
					this.getCreators().remove(player);
					HGAPI.sendMessage(player, Lang.CREATE_ARENA_CANCELLED.toString());
				}
			}
		}
		}
	    }else if(sender instanceof ConsoleCommandSender){
	    	
	    }
		return true;
	}

	private void saveArena(Arena arena , Player player) {
		if(arena.getSecondTeamGates().isEmpty()){
			//player.sendMessage(Lang.TITLE.toString() + Lang.SECOND_TEAM_EMPTY_GATES.toString());
			HGAPI.sendMessage(player, Lang.SECOND_TEAM_EMPTY_GATES.toString());
			return;
		}
		if(arena.getFirstTeamGates().isEmpty()){
			//player.sendMessage(Lang.TITLE.toString() + Lang.FIRST_TEAM_EMPTY_GATES.toString());
			HGAPI.sendMessage(player, Lang.FIRST_TEAM_EMPTY_GATES.toString());
			return;
		}	
		if(arena.getFirstTeamLobbyLocation() == null){
			//player.sendMessage(Lang.TITLE.toString() + Lang.SECOND_TEAM_LOBBY_NULL.toString());
			HGAPI.sendMessage(player, Lang.SECOND_TEAM_LOBBY_NULL.toString());
			return;
		}
		if(arena.getSecondTeamLobbyLocation() == null){
			//player.sendMessage(Lang.TITLE.toString()+ Lang.FIRST_TEAM_LOBBY_NULL.toString());
			HGAPI.sendMessage(player, Lang.FIRST_TEAM_LOBBY_NULL.toString());
			return;
		}
		if(arena.getFirstTeamSpawnLocation() == null){
			//player.sendMessage(Lang.TITLE.toString() + Lang.FIRST_TEAM_SPAWN_NULL.toString());
			HGAPI.sendMessage(player, Lang.FIRST_TEAM_SPAWN_NULL.toString());
			return;
		}
		if(arena.getSecondTeamSpawnLocation() == null){
			//player.sendMessage(Lang.TITLE.toString() + Lang.SECOND_TEAM_SPAWN_NULL.toString());
			HGAPI.sendMessage(player, Lang.SECOND_TEAM_SPAWN_NULL.toString());
			return;
		}		
		if(arena.getPuckLocation() == null){
			//player.sendMessage(Lang.TITLE.toString() + Lang.PUCK_SPAWN_NULL.toString());
			HGAPI.sendMessage(player, Lang.PUCK_SPAWN_NULL.toString());
			return;
		}
		if(arena.getFirstTeam() == null){
			//player.sendMessage(Lang.TITLE.toString() + Lang.FIRST_TEAM_NULL.toString());
			HGAPI.sendMessage(player, Lang.FIRST_TEAM_NULL.toString());
			return;
		}
		if(arena.getSecondTeam() == null){
			//player.sendMessage(Lang.TITLE.toString() + Lang.SECOND_TEAM_NULL.toString());
			HGAPI.sendMessage(player, Lang.SECOND_TEAM_NULL.toString());
			return;
		}
		
		
		HGAPI.getArenaManager().save(arena);
		
		HGAPI.getArenaManager().addArena(arena);
		this.getArenas().remove(player.getName());
		this.getCreators().remove(player);
		//player.sendMessage(Lang.TITLE.toString() + Lang.ARENA_SAVED.toString());
		HGAPI.sendMessage(player, Lang.ARENA_SAVED.toString());
	}
}