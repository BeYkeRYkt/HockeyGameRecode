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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Classes.ClassType;
import ykt.BeYkeRYkt.HockeyGame.API.Events.MatchStartEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.MatchStopEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.PlayerAutobalanceEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.PlayerJoinArenaEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.PlayerLeaveArenaEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.RespawnPuckEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Events.StartPlayMusicEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;
import ykt.BeYkeRYkt.HockeyGame.API.Team.Team;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.ItemGiver;
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
		player.setAllowTeleport(true);
				
		if(this.getFirstTeam().getName().equals(team.getName())){

		player.getBukkitPlayer().teleport(getFirstTeamLobbyLocation());
		
		getFirstTeam().getMembers().add(player);
		}else if(this.getSecondTeam().getName().equals(team.getName())){

		player.getBukkitPlayer().teleport(getSecondTeamLobbyLocation());
		
		getSecondTeam().getMembers().add(player);
		}
		
		if(HGAPI.checkOldMCVersion()){
		//player.getBukkitPlayer().setHealth(20); - ERRORS
		player.getBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 4, 20));
		}else if(!HGAPI.checkOldMCVersion()){
		player.getBukkitPlayer().setHealth(player.getBukkitPlayer().getMaxHealth());
		}
		
		player.getBukkitPlayer().setFoodLevel(20);
		player.getBukkitPlayer().setGameMode(GameMode.SURVIVAL);
		
		
		getPlayers().add(player);
		HGAPI.getPlayerManager().addPlayer(player.getName(), player);
		
		broadcastMessage(ChatColor.YELLOW + player.getName() + Lang.PLAYER_JOIN_ARENA.toString() +ChatColor.GREEN + getName());
	}
	}
	
	public void leavePlayer(HockeyPlayer player, boolean loadinv){
		
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
		if(loadinv){
        HGAPI.getItemSaver().loadPlayer(player.getBukkitPlayer());
		}
		
		if(getWinnerTeam() != null && team.getName().equals(getWinnerTeam().getName())){
			rewardsWinner(player);
		}else if(getLoserTeam() != null && team.getName().equals(getLoserTeam().getName())){
			rewardsLoser(player);
		}
		
		broadcastMessage(ChatColor.YELLOW + player.getName() + Lang.PLAYER_LEAVE_ARENA.toString() + ChatColor.GREEN + getName());

		//AutoBalance.
		//int min = HGAPI.getPlugin().getConfig().getInt("Game.MinPlayers") / 2;
		//if(getFirstTeam().getMembers().size() < min || getSecondTeam().getMembers().size() < min){
		//stopArena();
		//}
		
		//Link: ArenaRunnable
		//if(getPlayers().size() < HGAPI.getPlugin().getConfig().getInt("Game.MinPlayers")){
			//stopArena();
		//}
		
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
			
			if(getPlayers().size() < HGAPI.getPlugin().getConfig().getInt("Game.MinPlayers")){
				stopArena();
			}
			
			//AutoBalance
			//int min = HGAPI.getPlugin().getConfig().getInt("Game.MinPlayers") / 2;
			//if(getFirstTeam().getMembers().size() < min || getSecondTeam().getMembers().size() < min){
			//stopArena();
			//return;
			//}
			
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
				players.setAllowTeleport(false);
			}
			
			setPuckEntity(puck);
			startMainRunnable(puck);

			if(HGAPI.getPlugin().getConfig().getBoolean("Game.MusicMatch")){
				startPlayMusic();
			}
			
		}
	}
	
	private void rejoinAutoBalance(HockeyPlayer player, Team team){
		PlayerAutobalanceEvent event = new PlayerAutobalanceEvent(player, player.getTeam(), team);
		Bukkit.getPluginManager().callEvent(event);
		
		if(!event.isCancelled()){
		ClassType type = player.getType();
		player.getBukkitPlayer().getInventory().clear();
		player.getBukkitPlayer().getInventory().setArmorContents(null);
		player.getBukkitPlayer().updateInventory();

			if(player.getTeam().getWingers().contains(player)){
				player.getTeam().removeWinger(player);
			}else if(player.getTeam().getDefends().contains(player)){
				player.getTeam().removeDefend(player);
			}else if(player.getTeam().getGoalKeeper().equals(player)){
				player.getTeam().removeGoalkeeper();
			}

		player.getTeam().getMembers().remove(player);
		
		team.getMembers().add(player);
		player.setTeam(team);
		player.setAllowTeleport(true);
		
		if(type.getName().equals("Winger")){
			player.getTeam().addWinger(player);
		}else if(type.getName().equals("Defender")){
			player.getTeam().addDefend(player);
		}else if(type.getName().equals("Goalkeeper")){
			if(player.getTeam().getGoalKeeper() == null){
			player.getTeam().setGoalkeeper(player);
			}else{
				player.getBukkitPlayer().getInventory().clear();
				player.getBukkitPlayer().getInventory().setArmorContents(null);
				player.getBukkitPlayer().updateInventory();
				player.setType(HGAPI.getClassManager().getClass("Winger"));
			}
		}
		ItemGiver.setItems(player, player.getTeam().getColor());
		Location location = null;
			
		if(team.getName().equals(getFirstTeam().getName())){
	    location = getFirstTeamSpawnLocation().clone();
		}else if(team.getName().equals(getSecondTeam().getName())){
		location = getSecondTeamSpawnLocation().clone();
		}
		
    	location.setPitch(player.getBukkitPlayer().getLocation().getPitch());
    	location.setYaw(player.getBukkitPlayer().getLocation().getYaw());
		player.getBukkitPlayer().teleport(location);
		player.setAllowTeleport(false);
		broadcastMessage(ChatColor.AQUA + "Autobalancing...");
		}
	}
	
	//AutoBalance
	//Original Source code: https://github.com/Razz0991/Minigames/blob/master/Minigames/com/pauldavdesign/mineauz/minigames/scoring/CTFType.java
	public void autobalance(){
		for(int i = 0; i < getPlayers().size(); i++){
			int team = -1;
			if(getSecondTeam().getMembers().contains(getPlayers().get(i))){
				team = 1;
			}else if(getFirstTeam().getMembers().contains(getPlayers().get(i))){
				team = 0;
			}

			if(team == 1){
				if(getFirstTeam().getMembers().size() < getSecondTeam().getMembers().size() - 1){
					HockeyPlayer player = getPlayers().get(i);					
					rejoinAutoBalance(player, getFirstTeam());

					team = 0;
				}
			}else if(team == 0){
				if(getSecondTeam().getMembers().size() < getFirstTeam().getMembers().size() - 1){
					HockeyPlayer player = getPlayers().get(i);		
					rejoinAutoBalance(player, getSecondTeam());
					
					team = 1;
				}
			}else{
				if(getFirstTeam().getMembers().size() < getSecondTeam().getMembers().size() - 1){
					HockeyPlayer player = getPlayers().get(i);
					rejoinAutoBalance(player, getFirstTeam());
					
					team = 0;
				}else if(getSecondTeam().getMembers().size() < getFirstTeam().getMembers().size() - 1){
					HockeyPlayer player = getPlayers().get(i);
					rejoinAutoBalance(player, getSecondTeam());
					
					team = 1;
				}
			}
		}
	}
	
	private void startPlayMusic() {	
		Random random = new Random();
		int amount = random.nextInt(9);
		
		if(amount == 0){
			StartPlayMusicEvent event = new StartPlayMusicEvent(this, getPuckLocation(), Material.RECORD_3);
			Bukkit.getPluginManager().callEvent(event);
			
			if(!event.isCancelled()){
			getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
			}
			
			}else if(amount == 1){
				StartPlayMusicEvent event = new StartPlayMusicEvent(this, getPuckLocation(), Material.RECORD_4);
				Bukkit.getPluginManager().callEvent(event);
				
				if(!event.isCancelled()){
				getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
				}
			}else if(amount == 2){
				StartPlayMusicEvent event = new StartPlayMusicEvent(this, getPuckLocation(), Material.RECORD_5);
				Bukkit.getPluginManager().callEvent(event);
				
				if(!event.isCancelled()){
				getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
				}
			}else if(amount == 3){
				StartPlayMusicEvent event = new StartPlayMusicEvent(this, getPuckLocation(), Material.RECORD_6);
				Bukkit.getPluginManager().callEvent(event);
				
				if(!event.isCancelled()){
				getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
				}
			}else if(amount == 4){
				StartPlayMusicEvent event = new StartPlayMusicEvent(this, getPuckLocation(), Material.RECORD_7);
				Bukkit.getPluginManager().callEvent(event);
				
				if(!event.isCancelled()){
				getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
				}
	        }else if(amount == 5){
				StartPlayMusicEvent event = new StartPlayMusicEvent(this, getPuckLocation(), Material.RECORD_8);
				Bukkit.getPluginManager().callEvent(event);
				
				if(!event.isCancelled()){
				getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
				}
		    }else if(amount == 6){
				StartPlayMusicEvent event = new StartPlayMusicEvent(this, getPuckLocation(), Material.RECORD_9);
				Bukkit.getPluginManager().callEvent(event);
				
				if(!event.isCancelled()){
				getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
				}
	        }else if(amount == 7){
				StartPlayMusicEvent event = new StartPlayMusicEvent(this, getPuckLocation(), Material.RECORD_10);
				Bukkit.getPluginManager().callEvent(event);
				
				if(!event.isCancelled()){
				getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
				}
	        }else if(amount == 8){
				StartPlayMusicEvent event = new StartPlayMusicEvent(this, getPuckLocation(), Material.RECORD_12);
				Bukkit.getPluginManager().callEvent(event);
				
				if(!event.isCancelled()){
				getWorld().playEffect(event.getLocation(), Effect.RECORD_PLAY, event.getRecord());
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
	   if(getPlayers().size() < HGAPI.getPlugin().getConfig().getInt("Game.MinPlayers")) return;
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
					broadcastMessage(ChatColor.GOLD + getFirstTeam().getName() + Lang.TEAM_WIN.toString());
					broadcastMessage(Lang.RESULT.toString() + ChatColor.RED + getFirstTeamScores() + ChatColor.WHITE + " : " + ChatColor.BLUE + getSecondTeamScores());
					
					for(HockeyPlayer player: getFirstTeam().getMembers()){
						HGAPI.spawnRandomFirework(getWorld(), player.getBukkitPlayer().getLocation());
					}
					
					setWinnerTeam(getFirstTeam());
					setLoserTeam(getSecondTeam());
				}else if(getFirstTeamScores() < getSecondTeamScores()){
					broadcastMessage(ChatColor.GOLD + getSecondTeam().getName() + Lang.TEAM_WIN.toString());
					broadcastMessage(Lang.RESULT.toString() + ChatColor.RED + getFirstTeamScores() + ChatColor.WHITE + " : " + ChatColor.BLUE + getSecondTeamScores());
					
					for(HockeyPlayer player: getSecondTeam().getMembers()){
						HGAPI.spawnRandomFirework(getWorld(), player.getBukkitPlayer().getLocation());
					}
					
					setWinnerTeam(getSecondTeam());
					setLoserTeam(getFirstTeam());
				}else if(getFirstTeamScores() == getSecondTeamScores()){
					broadcastMessage(Lang.TIE.toString());
					broadcastMessage(Lang.RESULT.toString() + ChatColor.RED + getFirstTeamScores() + ChatColor.WHITE + " : " + ChatColor.BLUE +getSecondTeamScores());
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
		   leavePlayer(player, true);
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

	
	