package louis.server.dewardt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Lobby implements Listener {
	static Location lobby = new Location(Bukkit.getWorld("world_"+PlayerWorld.LOBBY),50F, 74F ,-141F);
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt)
	{
		Player player = evt.getPlayer();
		if(PlayerData.getPlayerWorld(evt.getPlayer())==null)
		{
			Bukkit.broadcastMessage(ChatColor.BLUE+"Welcome "+ChatColor.YELLOW+player.getName()+ChatColor.BLUE+" to the server! Be nice! "+ChatColor.RED+ChatColor.BOLD+"For now...");
			goToLobby(player);
		}
		if(PlayerData.getPlayerWorld(player)=="world_"+PlayerWorld.LOBBY)
		{
			goToLobby(player);			
			
		}
	}
	public static void goToLobby(Player player)
	{
		PlayerData.setWorld("world_"+PlayerWorld.LOBBY, player);
		ItemStack i = new ItemStack(Material.RECORD_11);
		PlayerInventory inv = player.getInventory();
		inv.clear();
		inv.addItem(i);
		player.teleport(lobby);
	}
	@EventHandler
	public void onHungerChange(FoodLevelChangeEvent evt)
	{
		if(evt.getEntity().getWorld()==Bukkit.getWorld("world_"+PlayerWorld.LOBBY))
		{
			if(evt.getEntityType()==EntityType.PLAYER)
			{
				Player player = (Player) evt.getEntity();
				player.setFoodLevel(20);
			}
		}
	}
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent evt)
	{
		if(evt.getEntity().getWorld()==Bukkit.getWorld("world_"+PlayerWorld.LOBBY))
		{
			
			if(evt.getEntity().getType()!=EntityType.PLAYER)
			{
				evt.setCancelled(true);
			}
			   
		}
	}
}
