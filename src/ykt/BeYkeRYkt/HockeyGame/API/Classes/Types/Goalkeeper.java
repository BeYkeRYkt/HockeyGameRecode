package ykt.BeYkeRYkt.HockeyGame.API.Classes.Types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ykt.BeYkeRYkt.HockeyGame.API.Classes.ClassType;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;

public class Goalkeeper implements ClassType{

	private String name = "Goalkeeper";
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public ItemStack getHelmet() {
		ItemStack item = new ItemStack(Material.PUMPKIN);	
		return item;
	}

	@Override
	public ItemStack getChestplate() {
	ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
		return item;
	}

	@Override
	public ItemStack getLeggings() {
	    ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
		return item;
	}

	@Override
	public ItemStack getBoots() {
	    ItemStack item = new ItemStack(Material.LEATHER_BOOTS);	
		return item;
	}

	@Override
	public ItemStack getHockeyStick() {
	ItemStack item = new ItemStack(Material.DIAMOND_HOE);
	ItemMeta meta = item.getItemMeta();
	meta.setDisplayName(Lang.HOCKEY_STICK.toString());
	item.setItemMeta(meta);
	return item;
	}
	
}