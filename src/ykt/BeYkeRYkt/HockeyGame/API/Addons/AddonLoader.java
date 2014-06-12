package ykt.BeYkeRYkt.HockeyGame.API.Addons;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.bukkit.ChatColor;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;


public class AddonLoader {

	private static ClassLoader loader;
	private static ClassLoader jarloader;
	
	public static List<Addon> load(String directory) {

		List<Addon> Addons = new ArrayList<Addon>();

		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdir();
		}


		try {
			loader = new URLClassLoader(new URL[] { dir.toURI().toURL() },
					Addon.class.getClassLoader());
		} catch (MalformedURLException ex) {
			HGAPI.getPlugin().getLogger().warning("AddonLoader encountered an exception: ");
			ex.printStackTrace();
			return Addons;
		}

		boolean loaded;
		String lastnameoa = "";

		for (File file : dir.listFiles()) {	

			loaded = false;
			lastnameoa = file.getName();

			if (!file.getName().endsWith(".class")) {
				if (!file.getName().endsWith(".jar")) {
					HGAPI.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.GRAY + file.getName() + " :Unknown format.");
					lastnameoa = "";
					continue;
				}else{
					HGAPI.getPlugin().getLogger().info("FOUND A JAR!");
					try{
		                JarFile jarFile = new JarFile(file.getAbsolutePath());

		                Enumeration<JarEntry> e = jarFile.entries();

		                URL[] urls = { new URL("jar:file:" + file.getAbsolutePath()+"!/") };
		                jarloader = null;
		                jarloader = new URLClassLoader(urls,Addon.class.getClassLoader());
//		                jarloader = URLClassLoader.newInstance(urls);

		                while (e.hasMoreElements()) {
		                    JarEntry je = (JarEntry) e.nextElement();
		                    if(!je.isDirectory() && je.getName().endsWith(".class")){


		                    if (je != null && je.getName() != null){

		                    // -6 because of .class
		                    String className = je.getName().substring(0,je.getName().length()-6);
		                    className = className.replace('/', '.');
		                    try{
		                    	//HGAPI.getPlugin().getLogger().info(className + jarFile.getName());
	    						Class<?> aclass = jarloader.loadClass(className);				
	    						Object object = aclass.newInstance();
	    						if (!(object instanceof Addon)) {
	    							//HGAPI.getPlugin().getLogger().warning("Not a valid add-on: " + aclass.getSimpleName());
	    						}else{
	    						Addon a = (Addon) object;	

	    						if (a == null || a.getName() == null){
			    					HGAPI.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.RED + je.getName() + " is invalid!");
			    					//continue;
	    						}

	    						Addons.add(a);
	    						//a.onLoad();
	    						loaded = true;
	    						je = null;
	    						}
	    					} catch (Exception ex) {
	    						HGAPI.getPlugin().getLogger().warning("A " + ex.getLocalizedMessage() + " caused " + className + " to fail to load!");
	    						ex.printStackTrace();
	    					} catch (Error ex) {
	    						HGAPI.getPlugin().getLogger().warning("A " + ex.getLocalizedMessage() + " caused " + className + " to fail to load!");
	    						ex.printStackTrace();
	    					}
		                    }
		                    }
		                }


		                e = null; 
		                jarloader = null;
		                jarFile.close();
		                jarFile = null;

					}catch (IOException e){
						System.err.println("Error: " + e.getMessage());
					}
					loaded = false;

				}
				continue;
			}
			String name = file.getName().substring(0,
					file.getName().lastIndexOf("."));
			try {
				Class<?> aclass = loader.loadClass(name);				
				Object object = aclass.newInstance();
				if (!(object instanceof Addon)) {
					//HGAPI.getPlugin().getLogger().warning("Not a valid add-on: " + aclass.getSimpleName());
				}else{
				Addon a = (Addon) object;	
				//a.onLoad();
				Addons.add(a);
				}
			} catch (Exception ex) {
				HGAPI.getPlugin().getLogger().warning("A " + ex.getLocalizedMessage() + " caused " + name + " to fail to load!");
				ex.printStackTrace();
			} catch (Error ex) {
				HGAPI.getPlugin().getLogger().warning("A " + ex.getLocalizedMessage() + " caused " + name + " to fail to load!");
				ex.printStackTrace();
			}
		}





		if (Addons.size() == 0) {
			HGAPI.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[HockeyGame] Folder is empty.");
		}
		
		return Addons;
	}
}