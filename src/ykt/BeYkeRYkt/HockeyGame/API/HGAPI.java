package ykt.BeYkeRYkt.HockeyGame.API;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import ykt.BeYkeRYkt.HockeyGame.HG;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.Arena;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.ArenaManager;
import ykt.BeYkeRYkt.HockeyGame.API.Classes.ClassManager;
import ykt.BeYkeRYkt.HockeyGame.API.Signs.SignManager;
import ykt.BeYkeRYkt.HockeyGame.API.Team.PlayerManager;
import ykt.BeYkeRYkt.HockeyGame.API.Team.TeamManager;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.PlayerSaver;

public class HGAPI{
	
	private static ArenaManager arena;
	private static SignManager signs;
	private static HG plugin;
	private static ClassManager classes;
	private static PlayerManager players;
	private static TeamManager teams;
	private static PlayerSaver saver;
	private static List<Color> colors;
	
	public HGAPI(HG plugin){
		this.plugin = plugin;
		init();
	}
	
	public void init(){
		this.classes = new ClassManager(this);
		this.teams = new TeamManager(this);
		this.players = new PlayerManager(this);
		this.signs = new SignManager(this);
		this.arena = new ArenaManager(this);
		this.saver = new PlayerSaver();
		this.colors = new ArrayList<Color>();
		saver.loadAllPlayers();
		
		colors.add(Color.AQUA);
		colors.add(Color.BLACK);
		colors.add(Color.BLUE);
		colors.add(Color.FUCHSIA);
		colors.add(Color.GRAY);
		colors.add(Color.GREEN);
		colors.add(Color.LIME);
		colors.add(Color.MAROON);
		colors.add(Color.NAVY);
		colors.add(Color.OLIVE);
		colors.add(Color.ORANGE);
		colors.add(Color.PURPLE);
		colors.add(Color.RED);
		colors.add(Color.SILVER);
		colors.add(Color.TEAL);
		colors.add(Color.WHITE);
		colors.add(Color.YELLOW);
	}
	
	public static ArenaManager getArenaManager(){
		return arena;
	}
	
	public static SignManager getSignManager(){
		return signs;
	}
	
	public static ClassManager getClassManager(){
		return classes;
	}
	
	public static PlayerManager getPlayerManager(){
		return players;
	}
	
	public static TeamManager getTeamManager(){
		return teams;
	}
	
	public static HG getPlugin(){
		return plugin;
	}
	
	public static PlayerSaver getItemSaver(){
		return saver;
	}
	
	public static List<Color> getColors(){
		return colors;
	}
	
	public static void sendMessage(Player player, String message, boolean sound){
		player.sendMessage(Lang.TITLE.toString() + message);
		if(sound){
		playSound(player, player.getLocation(), Sound.ITEM_PICKUP, 1, 1);
		}
	}
	
	public static void sendMessageAll(String message, boolean sound){
		for(Player players: Bukkit.getOnlinePlayers()){
			sendMessage(players, message, sound);
		}
	}
	
	/**
	 * playSound for players
	 * 
	 * @param loc
	 * @param sound
	 * @param Volume - Default 1
	 * @param Pitch - Default 1
	 */
	public static void playSound(Player player, Location loc, Sound sound, int Volume, int Pitch){
		player.playSound(loc, sound, Volume , Pitch);
	}
	
	public static void playEffect(World world, Location loc, Effect effect, int data){
		world.playEffect(loc, effect, data);
	}
	
	public static void spawnRandomFirework(World world,Location loc){
		//Spawn the Firework
        Firework fw = (Firework) world.spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        //random
        Random random = new Random();

        //sets type
        int rt = random.nextInt(5) + 1;
        Type type = Type.BALL;
        if (rt == 1) type = Type.BALL;
        if (rt == 2) type = Type.BALL_LARGE;
        if (rt == 3) type = Type.BURST;
        if (rt == 4) type = Type.CREEPER;
        if (rt == 5) type = Type.STAR;

        //colors
        //To be Added
        int r = random.nextInt(256);
        int b = random.nextInt(256);
        int g = random.nextInt(256);
        Color c1 = Color.fromRGB(r, g, b);

        r = random.nextInt(256);
        b = random.nextInt(256);
        g = random.nextInt(256);
        Color c2 = Color.fromRGB(r, g, b);


        //effect
        FireworkEffect effect = FireworkEffect.builder().flicker(random.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(random.nextBoolean()).build();

        //applied effects
        fwm.addEffect(effect);

        //random power! moar sulphur!
        int rp = random.nextInt(1) + 1;
        fwm.setPower(rp);

        //aaaaaand set it
        fw.setFireworkMeta(fwm);
	}
	
	public static void checkAndSave(Player player, Arena arena, Location loc){
		if(arena == null) return;
		if(arena.getFirstTeamLobbyLocation() == null){
			arena.setFirstTeamLobbyLocation(loc);
			HGAPI.sendMessage(player, Lang.SECOND_TEAM_SET_LOBBY.toString(), true);
			return;
		}
		if(arena.getSecondTeamLobbyLocation() == null){
			arena.setSecondTeamLobbyLocation(loc);
			HGAPI.sendMessage(player, Lang.FIRST_TEAM_SET_SPAWN.toString(), true);
			return;
		}
		if(arena.getFirstTeamSpawnLocation() == null){
			arena.setFirstTeamSpawnLocation(loc);
			HGAPI.sendMessage(player, Lang.SECOND_TEAM_SET_SPAWN.toString(), true);
			return;
		}
		if(arena.getSecondTeamSpawnLocation() == null){
			arena.setSecondTeamSpawnLocation(loc);
			HGAPI.sendMessage(player, Lang.PUCK_SET_SPAWN.toString(), true);
			return;
		}
		if(arena.getPuckLocation() == null){
			arena.setPuckLocation(loc);
			HGAPI.sendMessage(player, Lang.SET_FIRST_GATES.toString() + Lang.ICON_NEXT_STAGE, true);
			return;
		}
		
		if(!arena.isFirstGatesFulled()){
			arena.addFirstTeamGate(loc);
			HGAPI.sendMessage(player, Lang.GATE_STORED.toString(), true);
			return;
		}
		
		if(!arena.isSecondGatesFulled()){
			arena.addSecondTeamGate(loc);
			HGAPI.sendMessage(player, Lang.GATE_STORED.toString(), true);
			return;
		}

        HGAPI.getArenaManager().save(arena);
		
		HGAPI.getArenaManager().addArena(arena);
		HGAPI.getPlugin().getDevArenas().remove(player.getName());
		HGAPI.getPlugin().getArenaCreators().remove(player);
		HGAPI.sendMessage(player, Lang.ARENA_SAVED.toString(), true);
	}
}