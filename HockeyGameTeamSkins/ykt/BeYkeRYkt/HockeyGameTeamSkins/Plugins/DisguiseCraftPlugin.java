package ykt.BeYkeRYkt.HockeyGameTeamSkins.Plugins;

import org.bukkit.entity.Player;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.disguise.Disguise;
import pgDev.bukkit.DisguiseCraft.disguise.DisguiseType;
import ykt.BeYkeRYkt.HockeyGameTeamSkins.DisguisePlugin;

public class DisguiseCraftPlugin implements DisguisePlugin{

	@Override
	public void disguisePlayer(Player player, String type) {
		if(type == null) return;
		if(DisguiseType.fromString(type) == null) return;
		DisguiseType newType = DisguiseType.fromString(type);
		DisguiseCraft.getAPI().disguisePlayer(player, new Disguise(DisguiseCraft.getAPI().newEntityID(), newType));
	}

	@Override
	public void undisguisePlayer(Player player) {
		if(DisguiseCraft.getAPI().isDisguised(player)){
			DisguiseCraft.getAPI().undisguisePlayer(player);
		}
	}	
}