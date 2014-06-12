package ykt.BeYkeRYkt.HockeyGame.API.Arena;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Events.MatchStartEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.MatchStopEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.PlayerJoinArenaEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.PlayerLeaveArenaEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.RespawnPuckEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.StartPlayMusicEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;
import ykt.BeYkeRYkt.HockeyGame.API.Team.Team;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;
import ykt.BeYkeRYkt.HockeyGame.Runnables.ArenaRunnable;
import ykt.BeYkeRYkt.HockeyGame.Runnables.CountToStartRunnable;

public class Arena{
	
	private ArrayList<Location> red_gates = new ArrayList<Location>();
	private ArrayList<Location> blue_gates = new ArrayList<Location>();
	private String name;
	private ArrayList<HockeyPlayer> players = new ArrayList<HockeyPlayer>();
	private int max = HGAPI.getPlugin().getConfig().getInt("Game.MaxPlayers");
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
	private boolean firstgatefull = false;
	private boolean secondgatefull = false;
	private Team loser;
	private Team winner;
	
	
	public Arena(String arenaName){
		this.name = arenaName;
	}
	
	public Arena(String arenaName, World world){
		this.name = arenaName;
		this.world = world;
	}
	
	public boolean isFirstGatesFulled(){
		return firstgatefull;
	}
	
	public boolean isSecondGatesFulled(){
		return secondgatefull;
	}
	
	public void setFirstGatesFulled(boolean flag){
		this.firstgatefull = flag;
	}
	
	public void setSecondGatesFulled(boolean flag){
		this.secondgatefull = flag;
	}
	
	//SYSTEM
	public File getFile(){
		File file = new File(HGAPI.getPlugin().getDataFolder() + "/arenas/", name + ".yml");
		return file;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String arenaName){
		this.name = arenaName;
	}
	
	public ArrayList<HockeyPlayer> getPlayers(){
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
	public ArrayList<Location> getFirstTeamGates(){
		return red_gates;
	}
	
	public ArrayList<Location> getSecondTeamGates(){
		return blue_gates;
	}
	
	
	//ADD GATES
	public void addFirstTeamGate(Location loc){
		getFirstTeamGates().add(loc);
		//loc.setY(loc.getY() - 1.0);
		//getFirstTeamGates().add(loc);
		//loc.setY(loc.getY() + 2.0);
		//getFirstTeamGates().add(loc);
	}
	
	public void addSecondTeamGate(Location loc){
		getSecondTeamGates().add(loc);
		//loc.setY(loc.getY() - 1.0);
		//getSecondTeamGates().add(loc);
		//loc.setY(loc.getY() + 2.0);
		//getSecondTeamGates().add(loc);
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
		HGAPI.getItemSaver().savePlayer(player.getBukkitPlayer());
		player.getBukkitPlayer().getInventory().clear();
		player.getBukkitPlayer().getInventory().setArmorContents(null);
		player.getBukkitPlayer().updateInventory();
		player.setArena(this);
		player.setTeam(team);
				
		if(this.getFirstTeam().getName().equals(team.getName())){
			
		float pitch = player.getBukkitPlayer().getLocation().getPitch();
		float yaw = player.getBukkitPlayer().getLocation().getYaw();
		
		player.getBukkitPlayer().teleport(getFirstTeamLobbyLocation());
		
		getFirstTeam().getMembers().add(player);
		}else if(this.getSecondTeam().getName().equals(team.getName())){
			float pitch = player.getBukkitPlayer().getLocation().getPitch();
			float yaw = player.getBukkitPlayer().getLocation().getYaw();
			
		player.getBukkitPlayer().teleport(getSecondTeamLobbyLocation());
		getSecondTeam().getMembers().add(player);
		}
		
		player.getBukkitPlayer().setHealth(player.getBukkitPlayer().getMaxHealth());
		player.getBukkitPlayer().setFoodLevel(20);
		player.getBukkitPlayer().setGameMode(GameMode.SURVIVAL);
		
		
		getPlayers().add(player);
		HGAPI.getPlayerManager().addPlayer(player.getName(), player);
		
		broadcastMessage(ChatColor.YELLOW + player.getName() + Lang.PLAYER_JOIN_ARENA.toString() +ChatColor.GREEN + getName());
	}
	}
	
	public void leavePlayer(HockeyPlayer player){
		
		PlayerLeaveArenaEvent event = new PlayerLeaveArenaEvent(player, this);
		Bukkit.getPluginManager().callEvent(event);
		
		if(!event.isCancelled()){
		Team team = player.getTeam();
		player.getBukkitPlayer().getInventory().clear();
		player.getBukkitPlayer().getInventory().setArmorContents(null);
		player.getBukkitPlayer().updateInventory();
				
		if(player.getType() != null){
		if(team.getWingers().contains(player)){
			team.removeWinger(player);
		}else if(team.getDefends().contains(player)){
			team.removeDefend(player);
		}else if(team.getGoalKeeper().equals(player)){
			team.removeGoalkeeper();
		}
		}
		
		getPlayers().remove(player);
		team.getMembers().remove(player);
		HGAPI.getPlayerManager().removePlayer(player.getName());
		
		//Fixing gamemode
        HGAPI.getItemSaver().loadPlayer(player.getBukkitPlayer());
		
		if(getWinnerTeam() != null && team.getName().equals(getWinnerTeam().getName())){
			rewardsWinner(player);
		}else if(getLoserTeam() != null && team.getName().equals(getLoserTeam().getName())){
			rewardsLoser(player);
		}
		
		broadcastMessage(ChatColor.YELLOW + player.getName() + Lang.PLAYER_LEAVE_ARENA.toString() + ChatColor.GREEN + getName());
			
		if(isRunning()){
		if(getPlayers().size() < 2){
			stopArena();
		}
		}else if(!isRunning()){
		if(getPlayers().size() < 2){
			if(getCountToStartRunnable() != null){
			getCountToStartRunnable().cancel();
			}
		}
		}
		
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
		countrunnable = null;
		
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
			
			//Food
			for(HockeyPlayer players: getPlayers()){
				players.getBukkitPlayer().getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
			}
			
			setPuckEntity(puck);
			startMainRunnable(puck);
			
			if(getPlayers().size() < HGAPI.getPlugin().getConfig().getInt("Game.MinPlayers")){
				stopArena();
				return;
			}
			
			if(HGAPI.getPlugin().getConfig().getBoolean("Game.MusicMatch")){
				startPlayMusic();
			}
			
		}
	}

	private void startPlayMusic() {	
		StartPlayMusicEvent event = new StartPlayMusicEvent(this, getPuckLocation());
		Bukkit.getPluginManager().callEvent(event);
		
		if(!event.isCancelled()){
		Random random = new Random();
		int amount = random.nextInt(9);
		
		if(amount == 0){
			event.setRecord(Material.RECORD_3);
			getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
			}else if(amount == 1){
				event.setRecord(Material.RECORD_4);
				getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord() );
			}else if(amount == 2){
				event.setRecord(Material.RECORD_5);
				getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
			}else if(amount == 3){
				event.setRecord(Material.RECORD_6);
				getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
			}else if(amount == 4){
				event.setRecord(Material.RECORD_7);
				getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
	        }else if(amount == 5){
				event.setRecord(Material.RECORD_8);
	        	getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
		    }else if(amount == 6){
				event.setRecord(Material.RECORD_9);
		    	getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
	        }else if(amount == 7){
				event.setRecord(Material.RECORD_10);
	        	getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
	        }else if(amount == 8){
				event.setRecord(Material.RECORD_12);
	        	getWorld().playEffect(getPuckLocation(), Effect.RECORD_PLAY, event.getRecord());
	        }
		
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
			HGAPI.sendMessage(players.getBukkitPlayer(), message, true);
		}
	}

	public CountToStartRunnable getCountToStartRunnable(){
		return this.countrunnable ;
	}
	
	public void startCountToStartRunnable() {
	   //Check...
	   for(HockeyPlayer players : getPlayers()){
		   if(!players.isReady()) return;
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
		RespawnPuckEvent event = new RespawnPuckEvent(this, puck, getPuckLocation());
		Bukkit.getPluginManager().callEvent(event);
		
		if(!event.isCancelled()){
		setPuckEntity(puck);
		getMainRunnable().setPuck(puck);
		
		
		HGAPI.playEffect(getWorld(), getPuckLocation(), Effect.ENDER_SIGNAL, 1);
		HGAPI.playEffect(getWorld(), getPuckLocation(), Effect.MOBSPAWNER_FLAMES, 1);
		HGAPI.playEffect(getWorld(), getPuckLocation(), Effect.SMOKE, 1);
		
		broadcastMessage(Lang.MATCH_CONTINUES.toString());
		}else if(event.isCancelled()){
			item.remove();
		}
	}

	public void rewardsWinner(HockeyPlayer player){
		for(String materialID: HGAPI.getPlugin().getWinnersRewards()){
			ItemStack item = HGAPI.parseString(materialID);
			player.getBukkitPlayer().getInventory().addItem(item);
		}
	}
	
	public void rewardsLoser(HockeyPlayer player){
		for(String materialID: HGAPI.getPlugin().getLosersRewards()){
			ItemStack item = HGAPI.parseString(materialID);
			player.getBukkitPlayer().getInventory().addItem(item);
		}
	}
	
	public void startRewards(){
		   //Rewards
				if(getFirstTeamScores() > getSecondTeamScores()){
					HGAPI.sendMessageAll(ChatColor.GOLD + getFirstTeam().getName() + Lang.TEAM_WIN.toString(), true);
					HGAPI.sendMessageAll(Lang.RESULT.toString() + ChatColor.RED + getFirstTeamScores() + ChatColor.WHITE + " : " + ChatColor.BLUE + getSecondTeamScores(), true);
					
					for(HockeyPlayer player: getFirstTeam().getMembers()){
						HGAPI.spawnRandomFirework(getWorld(), player.getBukkitPlayer().getLocation());
					}
					
					setWinnerTeam(getFirstTeam());
					setLoserTeam(getSecondTeam());
				}else if(getFirstTeamScores() < getSecondTeamScores()){
					HGAPI.sendMessageAll(ChatColor.GOLD + getSecondTeam().getName() + Lang.TEAM_WIN.toString(), true);
					HGAPI.sendMessageAll(Lang.RESULT.toString() + ChatColor.RED + getFirstTeamScores() + ChatColor.WHITE + " : " + ChatColor.BLUE + getSecondTeamScores(), true);
					
					for(HockeyPlayer player: getSecondTeam().getMembers()){
						HGAPI.spawnRandomFirework(getWorld(), player.getBukkitPlayer().getLocation());
					}
					
					setWinnerTeam(getSecondTeam());
					setLoserTeam(getFirstTeam());
				}else if(getFirstTeamScores() == getSecondTeamScores()){
					HGAPI.sendMessageAll(Lang.TIE.toString(), true);
					HGAPI.sendMessageAll(Lang.RESULT.toString() + ChatColor.RED + getFirstTeamScores() + ChatColor.WHITE + " : " + ChatColor.BLUE +getSecondTeamScores(), true);
				}
		
	}
	
	public void stopArena() {   	
	   MatchStopEvent event = new MatchStopEvent(getPlayers(), this);
	   Bukkit.getPluginManager().callEvent(event);
		
	   if(!event.isCancelled()){
	   setRunning(false);
	   
		if(HGAPI.getPlugin().getConfig().getBoolean("Game.MusicMatch")){
			getWorld().playEffect(getPuckLocation(), Effect.RECORD_PLAY, 0);
		}
	   
	   if(getCountToStartRunnable() != null){
		   getCountToStartRunnable().cancel();
	   }
	   
	   if(getMainRunnable() != null){
	   getMainRunnable().cancel();
	   }
	   
	   //Fixing ConcurrentModificationException...
	   for(Iterator<HockeyPlayer> it = getPlayers().iterator(); it.hasNext(); ){
		   HockeyPlayer player = it.next();
		   it.remove();
		   leavePlayer(player);
	   }
	   
	   if(getPuckEntity() != null){
		   
	   if(getPuckEntity().getItem() != null && !getPuckEntity().getItem().isDead()){
		getPuckEntity().getItem().remove();
	   }
		getPuckEntity().clearItem();
		getPuckEntity().clearPlayer();	
	   }
		
	   red_score = 0;
	   blue_scores = 0;
	   countrunnable = null;
	   mainrun = null;
	   puckentity = null;
	   winner = null;
	   loser = null;
	   }
	   
	}
	
	public Team getWinnerTeam(){
		return winner;
	}
	
	public void setWinnerTeam(Team team){
		this.winner = team;
	}
	
	public Team getLoserTeam(){
		return loser;
	}
	
	public void setLoserTeam(Team team){
		this.loser = team;
	}
	
}

	
	