package ykt.BeYkeRYkt.HockeyGameTeamSkins.Plugins;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import ykt.BeYkeRYkt.HockeyGameTeamSkins.DisguisePlugin;

public class LibsDisguisesPlugin implements DisguisePlugin{

	@Override
	public void disguisePlayer(Player player, String type) {
		// TODO Auto-generated method stub
		if(type == null) return;
		if(EntityType.valueOf(type.toUpperCase()) == null) return;
		DisguiseType disg = DisguiseType.getType(EntityType.valueOf(type.toUpperCase()));
		MobDisguise mobDisguise = new MobDisguise(disg, true);
		DisguiseAPI.disguiseToAll(player, mobDisguise);
	}

	@Override
	public void undisguisePlayer(Player player) {
		if(DisguiseAPI.isDisguised(player)){
		DisguiseAPI.undisguiseToAll(player);
		}
	}
	
}