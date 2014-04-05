package louis.server.dewardt;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Rank extends JavaPlugin implements Listener, CommandExecutor{
	public static Map<String, String> rank = new HashMap<String,String>();
	public static Map<String, String> owner = new HashMap<String,String>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if (sender instanceof ConsoleCommandSender) {
	           // do something
	        } else if(sender instanceof Player){
		           Player player = (Player) sender;
		           if(owner.containsKey(player.getName()))
		           {
		        	   owner.put(player.getName(), "[Owner]");
		           }else {
		        	   player.sendMessage(ChatColor.BOLD+""+ChatColor.RED+"You must be an owner or console!");
		           }
	           return false;
	        }
	        // do something
	        return false;
	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent evt)
	{
		evt.getPlayer();
	}
	@EventHandler
	public void onCommand()
	{
		
	}
	public void onEnable()
	{
		try{
			rank = SLAPI.load("rank.bin");
			owner = SLAPI.load("owner.bin");
	            }catch(Exception e){
	                //handle the exception
	                getLogger().warning("Couldn't load Save file: "+e.getStackTrace());
	            }
	}
	public void onDisable()
	{
		 try{
				SLAPI.save(rank,"rank.bin");
				SLAPI.save(owner,"owner.bin");

		            }catch(Exception e){
		                //handle the exception
		                getLogger().warning("Couldn't load Save file: "+e.getStackTrace());
		            }
	}
}
