package ykt.BeYkeRYkt.HockeyGame.API.Arena;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Events.MatchStartEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.PlayerJoinArenaEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.PlayerLeaveArenaEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;
import ykt.BeYkeRYkt.HockeyGame.API.Team.Team;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;
import ykt.BeYkeRYkt.HockeyGame.Runnables.ArenaRunnable;
import ykt.BeYkeRYkt.HockeyGame.Runnables.CountToStartRunnable;

public class Arena{
	
	private List<Location> red_gates = new ArrayList<Location>();
	private List<Location> blue_gates = new ArrayList<Location>();
	private String name;
	private List<HockeyPlayer> players = new ArrayList<HockeyPlayer>();
	private int max = 12;
	private Team team1;
	private Team team2;
	private Location puck_loc;
	private ItemStack puck;
	private boolean running = false;
	private int red_score = 0;
	private int blue_scores = 0;
	private Location red_spawn;
	private Location red_lobby;
	private Location blue_lobby;
	private Location blue_spawn;
	private World world;
	private CountToStartRunnable countrunnable;
	private ArenaRunnable mainrun;
	private Puck puckentity;
	
	
	public Arena(String arenaName){
		this.name = arenaName;
	}
	
	public Arena(String arenaName, World world){
		this.name = arenaName;
		this.world = world;
	}
	
	
	//SYSTEM
	public String getName(){
		return name;
	}
	
	public void setName(String arenaName){
		this.name = arenaName;
	}
	
	public List<HockeyPlayer> getPlayers(){
		return players;
	}
	
	public int getMaxPlayers(){
		return max;
	}
	
	public void setMaxPlayers(int max){
		this.max = max;
	}

	public void setPuckLocation(Location loc){
		this.puck_loc = loc;
	}
	
	public Location getPuckLocation(){
		return puck_loc;
	}
	

	public void setPuck(ItemStack item){
		this.puck = item;
	}
	
	public ItemStack getPuck(){
		return puck;
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public void setRunning(boolean flag){
		this.running = flag;
	}
	
	public World getWorld(){
		return world;
	}
	
	public void setWorld(String worldname){
		if(Bukkit.getWorld(worldname) != null){
		this.world = Bukkit.getWorld(worldname);
		}
	}
	
	//red
	public Location getFirstTeamLobbyLocation(){
		return red_lobby;
	}
	
	public Location getFirstTeamSpawnLocation(){
		return red_spawn;
	}
	
	public void setFirstTeamLobbyLocation(Location loc){
		this.red_lobby = loc;
	}
	
	public void setFirstTeamSpawnLocation(Location loc){
		this.red_spawn = loc;
	}
	
	//blue
	public Location getSecondTeamLobbyLocation(){
		return blue_lobby;
	}
	
	public Location getSecondTeamSpawnLocation(){
		return blue_spawn;
	}
	
	public void setSecondTeamLobbyLocation(Location loc){
		this.blue_lobby = loc;
	}
	
	public void setSecondTeamSpawnLocation(Location loc){
		this.blue_spawn = loc;
	}
	
	//TEAMS
	public Team getFirstTeam(){
		return team1;
	}
	
	public Team getSecondTeam(){
		return team2;
	}
	
	public void setFirstTeam(Team team){
		this.team1 = team;
	}
	
	public void setSecondTeam(Team team){
		this.team2 = team;
	}
	
	
	
	
	//GATES
	public List<Location> getFirstTeamGates(){
		return red_gates;
	}
	
	public List<Location> getSecondTeamGates(){
		return blue_gates;
	}
	
	
	//ADD GATES
	public void addFirstTeamGate(Location loc){
		getFirstTeamGates().add(loc);
	}
	
	public void addSecondTeamGate(Location loc){
		getSecondTeamGates().add(loc);
	}
	
	
	//REMOVE GATES
	public void removeFirstTeamGate(Location loc){
		getFirstTeamGates().remove(loc);
	}
	
	public void removeSecondTeamGate(Location loc){
		getSecondTeamGates().remove(loc);
	}
	
	//GOALS
	
	public int getFirstTeamScores(){
		return red_score;
	}
	
	public int getSecondTeamScores(){
		return blue_scores;
	}
	
	public void addFirstTeamScore(int scores){
		this.red_score = getFirstTeamScores() + scores;
	}
	
	public void addSecondTeamScore(int scores){
		this.blue_scores = getSecondTeamScores() + scores;
	}
	
	//PLAYERS
	public void joinPlayer(HockeyPlayer player, Team team){
		
		PlayerJoinArenaEvent event = new PlayerJoinArenaEvent(player, this);
		Bukkit.getPluginManager().callEvent(event);
		
		if(!event.isCancelled()){
		//Save.inv
		player.getBukkitPlayer().getInventory().clear();
		player.getBukkitPlayer().updateInventory();
		player.setArena(this);
		player.setTeam(team);
				
		if(this.getFirstTeam().getName().equals(team.getName())){
		player.getBukkitPlayer().teleport(getFirstTeamLobbyLocation());
		getFirstTeam().getMembers().add(player);
		}else if(this.getSecondTeam().getName().equals(team.getName())){
		player.getBukkitPlayer().teleport(getSecondTeamLobbyLocation());
		getSecondTeam().getMembers().add(player);
		}
		
		getPlayers().add(player);
		HGAPI.getPlayerManager().addPlayer(player.getName(), player);
	}
	}
	
	public void leavePlayer(HockeyPlayer player){
		
		PlayerLeaveArenaEvent event = new PlayerLeaveArenaEvent(player, this);
		Bukkit.getPluginManager().callEvent(event);
		
		if(!event.isCancelled()){
		Team team = player.getTeam();
		player.getBukkitPlayer().getInventory().clear();
		player.getBukkitPlayer().updateInventory();
		//Save.load
		
		if(team.getWingers().contains(player)){
			team.removeWinger(player);
		}else if(team.getDefends().contains(player)){
			team.removeDefend(player);
		}else if(team.getGoalKeeper().equals(player)){
			team.removeGoalkeeper();
		}
		getPlayers().remove(player);
		HGAPI.getPlayerManager().removePlayer(player.getName());
		
		//player.getBukkitPlayer().teleport(home);
		}
	}
	
	private void setupPuck(){
		String material_config = HGAPI.getPlugin().getConfig().getString("Game.puck.material");
		ItemStack item = new ItemStack(Material.getMaterial(material_config));
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Lang.PUCK_NAME.toString());
		item.setItemMeta(meta);
		setPuck(item);
	}
	
	public void startArena(){
		MatchStartEvent event = new MatchStartEvent(getPlayers(), this);
		Bukkit.getPluginManager().callEvent(event);
		getCountToStartRunnable().cancel();
		
		if(!event.isCancelled()){

			if(getPuck() == null){
			setupPuck();
			}
			
			Item item = getWorld().dropItemNaturally(getPuckLocation(), getPuck());
			
			//MASS TELEPORT
			getFirstTeam().MassTeleportToLocation(getFirstTeamSpawnLocation());
			getSecondTeam().MassTeleportToLocation(getSecondTeamSpawnLocation());
			
			setRunning(true);
			
			Puck puck = new Puck(this, item);
			
			setPuckEntity(puck);
			startMainRunnable(puck);
			
			if(HGAPI.getPlugin().getConfig().getBoolean("Game.MusicMatch")){
				startPlayMusic();
			}
		}
	}

	private void startPlayMusic() {	
		Random random = new Random();
		int amount = random.nextInt(9);
		
		if(amount == 0){
			getWorld().playEffect(getPuckLocation(), Effect.RECORD_PLAY, Material.RECORD_3);
			}else if(amount == 1){
				getWorld().playEffect(getPuckLocation(), Effect.RECORD_PLAY, Material.RECORD_4);
			}else if(amount == 2){
				getWorld().playEffect(getPuckLocation(), Effect.RECORD_PLAY, Material.RECORD_5);
			}else if(amount == 3){
				getWorld().playEffect(getPuckLocation(), Effect.RECORD_PLAY, Material.RECORD_6);
			}else if(amount == 4){
				getWorld().playEffect(getPuckLocation(), Effect.RECORD_PLAY, Material.RECORD_7);
	        }else if(amount == 5){
	        	getWorld().playEffect(getPuckLocation(), Effect.RECORD_PLAY, Material.RECORD_8);
		    }else if(amount == 6){
		    	getWorld().playEffect(getPuckLocation(), Effect.RECORD_PLAY, Material.RECORD_9);
	        }else if(amount == 7){
	        	getWorld().playEffect(getPuckLocation(), Effect.RECORD_PLAY, Material.RECORD_10);
	        }else if(amount == 8){
	        	//getWorld().playEffect(getPuckLocation(), Effect.RECORD_PLAY, Material.RECORD_11);
	        	getWorld().playEffect(getPuckLocation(), Effect.RECORD_PLAY, Material.RECORD_12);
	        }
	}

	public Team getTeam(String teamName) {
		
		if(teamName.equals(getFirstTeam().getName())){
			return getFirstTeam();
		}else if(teamName.equals(getSecondTeam().getName())){
			return getSecondTeam();
		}
		
		return null;
	}
	
	
	public void broadcastMessage(String message){
		for(HockeyPlayer players: getPlayers()){
			HGAPI.sendMessage(players.getBukkitPlayer(), message);
		}
	}

	public CountToStartRunnable getCountToStartRunnable(){
		return this.countrunnable ;
	}
	
	public void startCountToStartRunnable() {
	   //Check...
	   for(HockeyPlayer players : getPlayers()){
		   if(!players.isReady()){
			   return;
		   }
	   }
	   
	   this.countrunnable = new CountToStartRunnable(this);
	   getCountToStartRunnable().runTaskTimer(HGAPI.getPlugin(), 0, 20);
	}
		
	public Puck getPuckEntity(){
		return puckentity;
	}
	
	public void setPuckEntity(Puck puck){
		this.puckentity = puck;
	}
	
	public ArenaRunnable getMainRunnable(){
		return mainrun;
	}
	
	public void startMainRunnable(Puck puck){
				
		int seconds = HGAPI.getPlugin().getConfig().getInt("Game.MatchTimer");
		this.mainrun = new ArenaRunnable(this, puck, seconds);
		getMainRunnable().runTaskTimer(HGAPI.getPlugin(), 0, 20);
		
	}
	
	public void respawnPuck(){
		Item item = getWorld().dropItemNaturally(getPuckLocation(), getPuck());
		Puck puck = new Puck(this, item);
		setPuckEntity(puck);
		getMainRunnable().setPuck(puck);
		
		HGAPI.playEffect(getWorld(), getPuckLocation(), Effect.ENDER_SIGNAL, 1);
		HGAPI.playEffect(getWorld(), getPuckLocation(), Effect.MOBSPAWNER_FLAMES, 1);
		HGAPI.playEffect(getWorld(), getPuckLocation(), Effect.SMOKE, 1);
		
		broadcastMessage(Lang.MATCH_CONTINUES.toString());
	}

	public void stopArena() {
	   for(HockeyPlayer player: getPlayers()){
		   leavePlayer(player);
		   setRunning(false);
	   }
	   
		getPuckEntity().getItem().remove();
		getPuckEntity().clearItem();
		getPuckEntity().clearPlayer();
	   
	   getMainRunnable().cancel();
	   red_score = 0;
	   blue_scores = 0;
	   countrunnable = null;
	   mainrun = null;
	   puckentity = null;
		
	}
	
}
	
	