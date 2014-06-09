package ykt.BeYkeRYkt.HockeyGame.Runnables;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Puck;
import ykt.BeYkeRYkt.HockeyGame.API.Events.GoalEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;

public class ArenaRunnable extends BukkitRunnable{

	private Arena arena;
	private int seconds;
	private int lastgoalsec = -2;
	private Puck puck;
	
	public ArenaRunnable(Arena arena, Puck item, int seconds){
		this.arena = arena;
		this.puck = item;
		this.seconds = seconds;
	}
	
	public void setPuck(Puck puck){
		this.puck = puck;
	}
	
	@Override
	public void run() {
			for(Location loc: arena.getFirstTeamGates()){
			if(puck.getItem() != null){
			if(loc.getWorld().getName().equals(puck.getItem().getWorld().getName()) &&
			   loc.getBlockX() == puck.getItem().getLocation().getBlockX() &&
			   loc.getBlockY() == puck.getItem().getLocation().getBlockY() &&
			   loc.getBlockZ() == puck.getItem().getLocation().getBlockZ()){
			
			GoalEvent event = new GoalEvent(puck.getLastPlayer(), arena, puck);
			Bukkit.getPluginManager().callEvent(event);
			
			arena.broadcastMessage(ChatColor.YELLOW + puck.getLastPlayer().getName() + Lang.SCORED_GOAL.toString() + ChatColor.GOLD + arena.getFirstTeam().getName());
			HGAPI.spawnRandomFirework(arena.getWorld(), puck.getLastPlayer().getBukkitPlayer().getLocation());
			
			//HGAPI.playEffect(arena.getWorld(), puck.getItem().getLocation(), Effect.EXPLOSION_HUGE, 1);
			arena.getWorld().createExplosion(loc, 0);
			
			puck.getItem().getItemStack().setAmount(0);
			puck.getItem().remove();
			puck.clearItem();
			puck.clearPlayer();
			arena.addSecondTeamScore(1);
			
			lastgoalsec = seconds;
			seconds = seconds + 5;
			}
			}
			}
		
		
			for(Location loc: arena.getSecondTeamGates()){
			if(puck.getItem() != null){
			if(loc.getWorld().getName().equals(puck.getItem().getWorld().getName()) &&
			   loc.getBlockX() == puck.getItem().getLocation().getBlockX() &&
			   loc.getBlockY() == puck.getItem().getLocation().getBlockY() &&
			   loc.getBlockZ() == puck.getItem().getLocation().getBlockZ()){
			GoalEvent event = new GoalEvent(puck.getLastPlayer(), arena, puck);
			Bukkit.getPluginManager().callEvent(event);
			
			arena.broadcastMessage(ChatColor.YELLOW + puck.getLastPlayer().getName() + Lang.SCORED_GOAL.toString() + ChatColor.GOLD + arena.getSecondTeam().getName());
			HGAPI.spawnRandomFirework(arena.getWorld(), puck.getLastPlayer().getBukkitPlayer().getLocation());
					
			//HGAPI.playEffect(arena.getWorld(), puck.getItem().getLocation(), Effect.EXPLOSION_HUGE, 1);
			arena.getWorld().createExplosion(loc, 0);
			
			puck.getItem().getItemStack().setAmount(0);
			puck.getItem().remove();
			puck.clearItem();
			puck.clearPlayer();
			arena.addFirstTeamScore(1);
			
			lastgoalsec = seconds;
			seconds = seconds + 5;
			}
			}
		}
		
		if(seconds == lastgoalsec && seconds > 5){
			arena.respawnPuck();
		}
		
		
		if(seconds < 6 && seconds > 0){
		    for(HockeyPlayer players: arena.getPlayers()){
			HGAPI.sendMessage(players.getBukkitPlayer(), "" + ChatColor.YELLOW + seconds + ChatColor.GRAY + "...", false);
		    HGAPI.playSound(players.getBukkitPlayer(), players.getBukkitPlayer().getLocation(), Sound.ITEM_BREAK, 1, 1);
		    }
		}
		
		if(seconds == 0){
			if(arena.getFirstTeamScores() > arena.getSecondTeamScores()){
				HGAPI.sendMessageAll(ChatColor.GOLD + arena.getFirstTeam().getName() + Lang.TEAM_WIN.toString(), true);
				HGAPI.sendMessageAll(Lang.RESULT.toString() + ChatColor.RED + arena.getFirstTeamScores() + ChatColor.WHITE + " : " + ChatColor.BLUE + arena.getSecondTeamScores(), true);
				
				for(HockeyPlayer player: arena.getFirstTeam().getMembers()){
					HGAPI.spawnRandomFirework(arena.getWorld(), player.getBukkitPlayer().getLocation());
				}
				
			}else if(arena.getFirstTeamScores() < arena.getSecondTeamScores()){
				HGAPI.sendMessageAll(ChatColor.GOLD + arena.getSecondTeam().getName() + Lang.TEAM_WIN.toString(), true);
				HGAPI.sendMessageAll(Lang.RESULT.toString() + ChatColor.RED + arena.getFirstTeamScores() + ChatColor.WHITE + " : " + ChatColor.BLUE + arena.getSecondTeamScores(), true);
				
				for(HockeyPlayer player: arena.getSecondTeam().getMembers()){
					HGAPI.spawnRandomFirework(arena.getWorld(), player.getBukkitPlayer().getLocation());
				}
			}else if(arena.getFirstTeamScores() == arena.getSecondTeamScores()){
				HGAPI.sendMessageAll(Lang.TIE.toString(), true);
				HGAPI.sendMessageAll(Lang.RESULT.toString() + ChatColor.RED + arena.getFirstTeamScores() + ChatColor.WHITE + " : " + ChatColor.BLUE + arena.getSecondTeamScores(), true);
			}
			
			arena.stopArena();
		}
		
		seconds--;
	}
	
}