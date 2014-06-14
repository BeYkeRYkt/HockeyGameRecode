package ykt.BeYkeRYkt.HockeyGameTeamSkins.Plugins;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ykt.BeYkeRYkt.HockeyGameTeamSkins.DisguisePlugin;
import de.robingrether.idisguise.api.DisguiseAPI;
import de.robingrether.idisguise.disguise.DisguiseType;
import de.robingrether.idisguise.disguise.MobDisguise;

public class iDisguisePlugin implements DisguisePlugin{

	private DisguiseAPI api = Bukkit.getServer().getServicesManager().getRegistration(DisguiseAPI.class).getProvider();;
	
	@Override
	public void disguisePlayer(Player player, String type) {
		if(type == null) return;
		if(DisguiseType.valueOf(type.toUpperCase()) != null){
		api.disguiseToAll(player, new MobDisguise(DisguiseType.valueOf(type.toUpperCase()), true));
		}
	}

	@Override
	public void undisguisePlayer(Player player) {
		if(api.isDisguised(player)){
			api.undisguiseToAll(player);
		}
	}	
}