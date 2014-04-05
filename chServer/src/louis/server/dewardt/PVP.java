package louis.server.dewardt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PVP implements MiniGameListener{
	Location spawnLoc = new Location(Bukkit.getWorld("world_"+PlayerWorld.PVP), 100, 100 , 100);
	static String name = 
			ChatColor.BOLD+""+ChatColor.RED+"PVP";
	static String desc = 
			ChatColor.BOLD+""+ChatColor.BLUE+"All out PVP with\n"+
			ChatColor.BOLD+""+ChatColor.YELLOW+"Weapon Ups as \n"+ChatColor.BOLD+""+"you progress";
			
	static ItemStack item = new ItemStack(Material.IRON_SWORD,1);
	
	
	public static void onEnable()
	{
		List<String> lore = new ArrayList<String>(Arrays.asList(desc.split("\n")));
		MinigameCls.add(name, item, lore, new PVP(), PlayerWorld.PVP);
	}
	@Override
	public void onPlayerJoin(Player player) {
		Bukkit.broadcastMessage("PVP");
		if(Bukkit.getWorld("world_"+PlayerWorld.PVP)==null)
		{
			Bukkit.broadcastMessage("Genning!");
					
		}
		PlayerData.setWorld("world_"+PlayerWorld.PVP, player);
		if(spawnLoc == null)
		{
			spawnLoc = new Location(Bukkit.getWorld("world_"+PlayerWorld.PVP), 100, 100 , 100);
		}
		player.teleport(spawnLoc);
	}
	@Override
	public void onJoinCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
	}
	@Override
	public void onLeaveCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	
}
