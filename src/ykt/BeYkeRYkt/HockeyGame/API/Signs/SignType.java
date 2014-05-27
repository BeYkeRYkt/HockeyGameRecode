package ykt.BeYkeRYkt.HockeyGame.API.Signs;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface SignType{
	
	public void handleCreateSign(SignChangeEvent event);
	
	public void handleClickSign(PlayerInteractEvent event);
	
	public void handleDestroy(BlockBreakEvent event);
	
}