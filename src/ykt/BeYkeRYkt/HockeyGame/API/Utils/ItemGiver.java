package ykt.BeYkeRYkt.HockeyGame.API.Utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import ykt.BeYkeRYkt.HockeyGame.API.Team.HockeyPlayer;

public class ItemGiver{
	
	public static void setItems(HockeyPlayer player, Color color){
		player.getBukkitPlayer().getInventory().setHelmet(getModifiedHelmet(player, color));
		player.getBukkitPlayer().getInventory().setChestplate(getModifiedChestplate(player, color));
		player.getBukkitPlayer().getInventory().setLeggings(getModifiedLeggings(player, color));
		player.getBukkitPlayer().getInventory().setBoots(getModifiedBoots(player, color));
		player.getBukkitPlayer().getInventory().addItem(player.getType().getHockeyStick());
		player.getBukkitPlayer().updateInventory();
	}
	
	private static ItemStack getModifiedHelmet(HockeyPlayer player, Color color){
		ItemStack item = player.getType().getHelmet();
		
		if(item.getType() != Material.PUMPKIN){
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		item.setItemMeta(meta);
		}
		
		return item;
	}
	
	private static ItemStack getModifiedChestplate(HockeyPlayer player, Color color){
		
		ItemStack item = player.getType().getChestplate();
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		item.setItemMeta(meta);
		return item;
	}
	
	private static ItemStack getModifiedLeggings(HockeyPlayer player, Color color){
		
		ItemStack item = player.getType().getLeggings();
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		item.setItemMeta(meta);
		return item;
	}
	
	private static ItemStack getModifiedBoots(HockeyPlayer player, Color color){
		
		ItemStack item = player.getType().getBoots();
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		item.setItemMeta(meta);
		return item;
	}
	
}