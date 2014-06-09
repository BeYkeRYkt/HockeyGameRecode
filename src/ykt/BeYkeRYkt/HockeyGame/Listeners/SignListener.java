package ykt.BeYkeRYkt.HockeyGame.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;
import ykt.BeYkeRYkt.HockeyGame.API.Utils.Lang;


public class SignListener implements Listener{
	
	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		Player player = e.getPlayer();
		if (e.getLine(0).equalsIgnoreCase("[HockeyGame]")) {
			if (!player.hasPermission("hg.setup")) {
				//player.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMISSION);
				HGAPI.sendMessage(player, Lang.NO_PERMISSION.toString(), false);
				e.setCancelled(true);
				e.getBlock().breakNaturally();
				return;
			}
			String line = e.getLine(1);
			if (HGAPI.getSignManager().getSigns().containsKey(line)) {
				HGAPI.getSignManager().getSigns().get(line).handleCreateSign(e);
			}
		}
	}

	@EventHandler
	public void onSignClick(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (!(e.getClickedBlock().getState() instanceof Sign)) {
			return;
		}
		Sign sign = (Sign) e.getClickedBlock().getState();
		if (sign.getLine(0).equalsIgnoreCase(ChatColor.RED + "[" + HGAPI.getPlugin().getName() + "]")) {
			String line = sign.getLine(1);
			if (HGAPI.getSignManager().getSigns().containsKey(line)) {
				HGAPI.getSignManager().getSigns().get(line).handleClickSign(e);
			}
		}
	}

	@EventHandler
	public void onSignDestroy(BlockBreakEvent e) {
		if (!(e.getBlock().getState() instanceof Sign)) {
			return;
		}
		Player player = e.getPlayer();
		Sign sign = (Sign) e.getBlock().getState();
		if (sign.getLine(0).equalsIgnoreCase(ChatColor.RED + "[" + ChatColor.WHITE + HGAPI.getPlugin().getName() + ChatColor.RED + "]")) {
			if (!player.hasPermission("hg.setup")) {
				//player.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMISSION);
				HGAPI.sendMessage(player, Lang.NO_PERMISSION.toString(), false);
				e.setCancelled(true);
				return;
			}
			String line = sign.getLine(1);
			if (HGAPI.getSignManager().getSigns().containsKey(line)) {
				HGAPI.getSignManager().getSigns().get(line).handleDestroy(e);
			}
		}
	}
}