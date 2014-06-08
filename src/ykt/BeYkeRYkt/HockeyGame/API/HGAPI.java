package ykt.BeYkeRYkt.HockeyGame.API;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.FireworkEffect.Type;

import ykt.BeYkeRYkt.HockeyGame.HG;
import ykt.BeYkeRYkt.HockeyGame.API.Arena.ArenaManager;
import ykt.BeYkeRYkt.HockeyGame.API.Classes.ClassManager;
import ykt.BeYkeRYkt.HockeyGame.API.Signs.SignManager;
import ykt.BeYkeRYkt.HockeyGame.API.Team.PlayerManager;
import ykt.BeYkeRYkt.HockeyGame.API.Team.TeamManager;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;

public class HGAPI{
	
	private static ArenaManager arena;
	private static SignManager signs;
	private static HG plugin;
	private static ClassManager classes;
	private static PlayerManager players;
	private static TeamManager teams;
	
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
	
	
	
	public static void sendMessage(Player player, String message){
		player.sendMessage(Lang.TITLE.toString() + message);
		playSound(player.getWorld(), player.getLocation(), Sound.ITEM_PICKUP, 1, 1);
	}
	
	public static void sendMessageAll(String message){
		for(Player players: Bukkit.getOnlinePlayers()){
			sendMessage(players, message);
		}
	}
	
	/**
	 * playSound for players
	 * 
	 * @param world
	 * @param loc
	 * @param sound
	 * @param Volume - Default 1
	 * @param Pitch - Default 1
	 */
	public static void playSound(World world, Location loc, Sound sound, int Volume, int Pitch){
		world.playSound(loc, sound, Volume , Pitch);
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
	
}