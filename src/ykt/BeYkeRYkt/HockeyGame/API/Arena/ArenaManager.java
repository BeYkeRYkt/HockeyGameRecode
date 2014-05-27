package ykt.BeYkeRYkt.HockeyGame.API.Arena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Events.MatchStartEvent;
import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;
import ykt.BeYkeRYkt.HockeyGame.API.Team.Team;

public class ArenaManager{
	
	private HGAPI api;
	private HashMap<String, Arena> arenas = new HashMap<String, Arena>();
	
	public ArenaManager(HGAPI api){
		this.api = api;
	}
	
	public void startMatch(Arena arena){

		arena.startArena();
	}
	
	
	public HashMap<String, Arena> getArenas(){
		return arenas;
	}
	
	public void addArena(Arena arena){
		getArenas().put(arena.getName(), arena);
	}
	
	public void removeArena(Arena arena){
		getArenas().remove(arena.getName());
	}
	
	public boolean isRunning(Arena arena){
		return arena.isRunning();
	}
	
	public Arena getArena(String name){
		return getArenas().get(name);
	}
	
	public void loadAllArenas(){
		   String path = "plugins/HockeyGame/arenas" ; 

		   String files ;
		   File folder = new File ( path ) ;
		   File [ ] listOfFiles = folder. listFiles ( ) ; 

		   for ( int i = 0 ; i < listOfFiles. length ; i ++ ) 
		   {

			   if ( listOfFiles[i].isFile( )) 
			   {
				   files = listOfFiles[i].getName();
			       if (files.endsWith(".yml"))
			       {
			    	  api.getPlugin().getLogger().info("load " + files.toString());
			          LoadArena(files);
			        }
			   }
		   }
	}

	public void LoadArena(String fn) {
	    try {
	      File f = new File("plugins/HockeyGame/arenas/"+fn);
	      if (!f.exists()) {
	        HGAPI.getPlugin().getLogger().info("[HockeyGame] plugins/HockeyGame/arenas/"+fn+" not found; Creating one.");
	        return;
	      }
	      
	      String name = fn.replace(".yml", "");
	      Arena arena = new Arena(name);
	      arena.setMaxPlayers(12);
	      
	      Scanner snr = new Scanner(f);

	      String txt = "";
	      double locX;
	      double locY;
	      double locZ;
	      Location loc;
	            
	      String an;
	      an = snr.nextLine().trim();
    	  HGAPI.getPlugin().getLogger().info("Arena name: " + an);
	      String wn = snr.nextLine().trim();
	      //World
	      HGAPI.getPlugin().getLogger().info("World name: " + wn);
	      arena.setWorld(wn);
	      
	      //MaxPlayers
	      int amount = Integer.parseInt(snr.nextLine().trim());
	      HGAPI.getPlugin().getLogger().info("MaxPlayers: " + amount);
	      arena.setMaxPlayers(amount);
	      
	      //First team
	      String team1name = snr.nextLine().trim();
	      HGAPI.getPlugin().getLogger().info("FirstTeam Name: " + team1name);
	      Team team1 = api.getTeamManager().getTeam(team1name);
	      arena.setFirstTeam(team1);
	      
	      //Second Team
	      String team2name = snr.nextLine().trim();
	      HGAPI.getPlugin().getLogger().info("SecondTeam Name: " + team2name);
	      Team team2 = api.getTeamManager().getTeam(team2name);
	      arena.setSecondTeam(team2);
	      
	      
	      
	      
	      //Get First Lobby team
	      txt = snr.nextLine().trim();
    	  locX = Double.parseDouble( txt.split("%")[0] );
    	  locY = Double.parseDouble( txt.split("%")[1] );
    	  locZ = Double.parseDouble( txt.split("%")[2] );
    	  Location locA = new Location(HGAPI.getPlugin().getServer().getWorld(wn), locX, locY, locZ);
    	  HGAPI.getPlugin().getLogger().info("Lobby for first team: " + txt);
          arena.setFirstTeamLobbyLocation(locA);
    	  
    	  //Get Second Lobby team
    	  txt = snr.nextLine().trim();
    	  locX = Double.parseDouble( txt.split("%")[0] );
    	  locY = Double.parseDouble( txt.split("%")[1] );
    	  locZ = Double.parseDouble( txt.split("%")[2] );
    	  Location locB = new Location(HGAPI.getPlugin().getServer().getWorld(wn), locX, locY, locZ);
    	  
    	  HGAPI.getPlugin().getLogger().info("Lobby for second team: " + txt);
    	  arena.setSecondTeamLobbyLocation(locB);
    	  
	      //Get First spawn team
	      txt = snr.nextLine().trim();
    	  locX = Double.parseDouble( txt.split("%")[0] );
    	  locY = Double.parseDouble( txt.split("%")[1] );
    	  locZ = Double.parseDouble( txt.split("%")[2] );
    	  Location locASpawn = new Location(HGAPI.getPlugin().getServer().getWorld(wn), locX, locY, locZ);
    	  HGAPI.getPlugin().getLogger().info("Spawnpoint for first team: " + txt);
          arena.setFirstTeamSpawnLocation(locASpawn);
    	  
    	  //Get Second spawn team
    	  txt = snr.nextLine().trim();
    	  locX = Double.parseDouble( txt.split("%")[0] );
    	  locY = Double.parseDouble( txt.split("%")[1] );
    	  locZ = Double.parseDouble( txt.split("%")[2] );
    	  Location locBSpawn = new Location(HGAPI.getPlugin().getServer().getWorld(wn), locX, locY, locZ);
    	  
    	  HGAPI.getPlugin().getLogger().info("Spawnpoint for second team: " + txt);
    	  arena.setSecondTeamSpawnLocation(locBSpawn);

    	  
    	  HGAPI.getPlugin().getLogger().info("-- Goal Locations A--");

    	  //Get Goals from Team 1
	      
	      while(true) {
	    	  txt = snr.nextLine().trim();
	    	  HGAPI.getPlugin().getLogger().info("A goal:" + txt);

	    	  if(txt.contains( "---"))
	    	  	break;
	    	  locX = Double.parseDouble( txt.split("%")[0] );
	    	  locY = Double.parseDouble( txt.split("%")[1] );
	    	  locZ = Double.parseDouble( txt.split("%")[2] );
	    	  loc = new Location(HGAPI.getPlugin().getServer().getWorld(wn), locX, locY, locZ);
	    	  arena.addFirstTeamGate(loc);
	      }
	      
	      //get Goals from Team 2
	      
	      HGAPI.getPlugin().getLogger().info("-- Goal Locations B--");
	      
	      while(true) {
	    	  txt = snr.nextLine().trim();
	    	  HGAPI.getPlugin().getLogger().info("B goal:" + txt);
	    	  if(txt.contains( "---"))
	    	  	break;
	    	  locX = Double.parseDouble( txt.split("%")[0] );
	    	  locY = Double.parseDouble( txt.split("%")[1] );
	    	  locZ = Double.parseDouble( txt.split("%")[2] );
	    	  loc = new Location(HGAPI.getPlugin().getServer().getWorld(wn), locX, locY, locZ);
	    	  arena.addSecondTeamGate(loc);
	      }
	      
	      //Get Puck Position
	      
	      txt = snr.nextLine().trim();
    	  locX = Double.parseDouble( txt.split("%")[0] );
    	  locY = Double.parseDouble( txt.split("%")[1] );
    	  locZ = Double.parseDouble( txt.split("%")[2] );
    	  Location mloc = new Location(HGAPI.getPlugin().getServer().getWorld(wn), locX, locY, locZ);

    	  HGAPI.getPlugin().getLogger().info("Puck position:" + txt);
    	  
    	  arena.setPuckLocation(mloc);
    	  //CLose the Scanner
	      snr.close();

	      
	      HGAPI.getPlugin().getLogger().info("Arena loaded " + an);
          HGAPI.getArenaManager().addArena(arena);

	      
	    } catch (Exception e) {
	      HGAPI.getPlugin().getLogger().warning("[HockeyGame] Error while loading plugins/HockeyGame/arenas/" + fn);
	      HGAPI.getPlugin().getLogger().warning("Error: " + e);
	      HGAPI.getPlugin().getLogger().warning("Full Error: ");
	      e.printStackTrace();
	    }
	  }
	
	public void save(Arena arena){
		String fn = "plugins/HockeyGame/arenas/" + arena.getName() + ".yml";
		try {
		      File oldFile = new File(fn);
		      if (!oldFile.getParentFile().isDirectory()) {
		        oldFile.mkdirs();
		      }
		      
		      oldFile.delete();

		      File newFile = new File(fn);
		      newFile.createNewFile();
		      
		      String toWrite = "";

		      PrintWriter pw = new PrintWriter(new File(fn));
		      //Write Arena Name
		      pw.write(""+arena.getName()+"\r\n");
		      //Write Worldname
		      pw.write("" + arena.getWorld().getName().toString() + "\r\n");
		      //Write maxPlayers
		      pw.write("" +arena.getMaxPlayers() + "\r\n");
		      //Write FirstTeam name
		      pw.write("" +arena.getFirstTeam().getName() + "\r\n");
		      //Write SecondTeam name
		      pw.write("" +arena.getSecondTeam().getName() + "\r\n");
		      //LocALobby
		      toWrite = arena.getFirstTeamLobbyLocation().getX() + "%" + arena.getFirstTeamLobbyLocation().getY() + "%" + arena.getFirstTeamLobbyLocation().getZ();
		      pw.write(""+ toWrite +"\r\n");
		      //LocBLobby
		      toWrite = arena.getSecondTeamLobbyLocation().getX() + "%" + arena.getSecondTeamLobbyLocation().getY() + "%" + arena.getSecondTeamLobbyLocation().getZ();
		      pw.write(""+ toWrite +"\r\n");

		      //LocASpawn
		      toWrite = arena.getFirstTeamSpawnLocation().getX() + "%" + arena.getFirstTeamSpawnLocation().getY() + "%" + arena.getFirstTeamSpawnLocation().getZ();
		      pw.write(""+ toWrite +"\r\n");
		      //LocBSpawn
		      toWrite = arena.getSecondTeamSpawnLocation().getX() + "%" + arena.getSecondTeamSpawnLocation().getY() + "%" + arena.getSecondTeamSpawnLocation().getZ();
		      pw.write(""+ toWrite +"\r\n");
		      
		      //Goals from Team A
		      toWrite = "";
			  for (Object item: arena.getFirstTeamGates()) {
				  pw.write( "" + ((Location) item).getX() + "%" + ((Location) item).getY() + "%" + ((Location) item).getZ() + "\r\n");
			  }
			  pw.write("---\r\n");

			  //Goals from Team B
		      toWrite = "";
			  for (Object item: arena.getSecondTeamGates()) {
				  pw.write( "" + ((Location) item).getX() + "%" + ((Location) item).getY() + "%" + ((Location) item).getZ() + "\r\n");
			  }
			  pw.write("---\r\n");
		      
			  //Puck Loc
			  toWrite = arena.getPuckLocation().getX() + "%" + arena.getPuckLocation().getY() + "%" + arena.getPuckLocation().getZ();
			  pw.write(""+ toWrite +"\r\n");

			  
		      pw.close();
		    } catch (Exception e) {
		      HGAPI.getPlugin().getLogger().warning("[HockeyGame] Error while writing new " + fn);
		      e.printStackTrace();
		    }
	}
}