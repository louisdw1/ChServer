package louis.server.dewardt;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class MinigameCls implements Listener, CommandExecutor{
	public static  BiMap<Integer, MiniGameListener> minigames = HashBiMap.create();
	public static BiMap<Integer, ItemStack> items = HashBiMap.create();
	public static BiMap<Integer, Integer> ids = HashBiMap.create();

	public static String invName = ChatColor.BLACK+""+ChatColor.BOLD+"Minigames: ";
	public static void add(String name, ItemStack i, List<String> loreList, MiniGameListener m,int worldID)
	{
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(loreList);
		i.setItemMeta(meta);
		minigames.put(items.size(), m);
		ids.put(items.size(), worldID);
		items.put(items.size(), i);

	}
	public void addItemStack(ItemStack i, MiniGameListener m, int worldID){
		minigames.put(worldID, m);
		items.put(worldID, i);
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt)
	{
		Player player = evt.getPlayer();
		String world = "world_"+PlayerWorld.LOBBY;
		String playerWorld = PlayerData.getPlayerWorld(player);
		if(!playerWorld.equals(world))
		{
			//If player is not in lobby no point in continuing
			return;	
		}
		if(player.getInventory().getItemInHand().getType()==Material.RECORD_11)
		{
			Inventory inv = Bukkit.createInventory(player, 9,invName);
			int i = 0;
 			while(i<items.size())
			{
				inv.addItem(items.get(i));
				i++;
			}
 			player.openInventory(inv);
		}
	}
	@EventHandler
	public void inventoryEvent(InventoryClickEvent e)
	{
		Player p = (Player) e.getWhoClicked();
		String world = "world_"+PlayerWorld.LOBBY;
		String playerWorld = PlayerData.getPlayerWorld(p);
		if(!playerWorld.equals(world))
		{
			//Return if not in not said to be in Lobby! (Could be in creative!)
			return;
		}
		e.setCancelled(true);
		if(e.getInventory().getName()==invName)
		{
			ItemStack i = e.getCurrentItem();
			int id  = items.inverse().get(i);
			minigames.get(id).onPlayerJoin(p);
		}
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("join")||cmd.getName().equalsIgnoreCase("leave")){ // If the player typed /basic then do the following...
			if(!(sender instanceof Player))
			{
				sender.sendMessage("This is a player only command!");
			}
			Player player = (Player) sender;
			boolean found = false;
			int  i = 0;
			while(i<ids.size())
			{
				if(PlayerData.getPlayerWorld(player).equals("world_"+ids.get(i)))
				{
					if(cmd.getName().equalsIgnoreCase("join"))
					{
						minigames.get(i).onJoinCommand(sender, cmd, label, args);
					}else if(cmd.getName().equalsIgnoreCase("leave"))
					{
						minigames.get(i).onLeaveCommand(sender, cmd, label, args);
					}
				}
				i++;
			}

			return true;
		}
		return false;
	}
}
