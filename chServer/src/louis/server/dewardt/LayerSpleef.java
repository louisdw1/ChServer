package louis.server.dewardt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class LayerSpleef implements MiniGameListener{
	public static int maxPlayers = 5;
	public static  Map<Integer,ArrayList<Player>> players = new HashMap<Integer,ArrayList<Player>>();
	//This is just another way of doing it! Not prefered!
	public static Map<Player,Integer> players2 = new HashMap<Player,Integer>();
	Location spawnLoc;
	static String name = 
			ChatColor.BOLD+""+ChatColor.GREEN+"Layer Spleef!";
	static String desc = 
			ChatColor.BOLD+""+ChatColor.BLUE+"Fight through layers of different\n"+ChatColor.BOLD+""+ChatColor.BLUE+"types of blocks!\n" +
			ChatColor.BOLD+""+ChatColor.YELLOW+"Some slow and \n"+ChatColor.BOLD+""+ChatColor.WHITE+"some fast!\n" +
			ChatColor.BOLD+""+ChatColor.BLUE+"Use your wit to change tools and plan out \n"+ChatColor.BOLD+""+ChatColor.BLUE+"your attack!";
	static ItemStack item = new ItemStack(Material.ICE,1);
	static World layerSpleef;
	
	public static void onEnable()
	{
		int i = 1;
		while(i<=6)
		{
			players.put(i, new ArrayList<Player>());
			i++;
		}
		layerSpleef = Bukkit.getServer().createWorld(new WorldCreator("world_"+PlayerWorld.LAYERSPLEEF+"_game"));
		List<String> lore = new ArrayList<String>(Arrays.asList(desc.split("\n")));
		MinigameCls.add(name, item, lore, new LayerSpleef(), PlayerWorld.LAYERSPLEEF);
	}
	@Override
	public void onPlayerJoin(Player player) {
		//player joins this one
		//Bukkit.broadcastMessage("On join Spleef");
	
		if(spawnLoc==null)
		{
			spawnLoc = new Location(Bukkit.getWorld("world_"+PlayerWorld.LAYERSPLEEF), 100, 101 , 103);
		}
		PlayerData.setWorld("world_"+PlayerWorld.LAYERSPLEEF, player);
		player.getInventory().clear();
		player.teleport(spawnLoc);
	}
	
	@Override
	public void onJoinCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = (Player) sender;
		/*
		if(Bukkit.getWorld("world_"+PlayerWorld.LAYERSPLEEF+"_game") == null)
		{
			Bukkit.getServer().createWorld(new WorldCreator("world_"+PlayerWorld.LAYERSPLEEF+"_game"));
			Bukkit.broadcastMessage("Null");
		}
		*/
		int gameID = 1;
		if(args.length==0)
		{
			gameID = 0;
			int i = 1;
			//6 being the max games!
			while(i<=6)
			{	
				if(players.get(i)==null)
				{
					gameID = i;
					i=6;
				
				}else if(players.get(i).size()<maxPlayers){
					gameID = i;
					i=6;
				}
				i++;
			}
			//If it hasn't been changed AKA no free game has been found
			if(gameID == 0)
			{
				player.sendMessage(ChatColor.RED+"There appears to be no free games! :-( ");
				return;
			}
		}else {
			if(args[0].equals("debug")&&player.isOp())
			{
				player.sendMessage(ChatColor.GREEN+"You have joined the admin world!");
				player.teleport(new Location(Bukkit.getWorld("world_"+PlayerWorld.LAYERSPLEEF+"_game"),1008,17,312));		
				return;
			}
			int chosen = 1;
			try {
			chosen = Integer.parseInt(args[0]);
			 } catch(NumberFormatException e)
			 {
				 player.sendMessage(ChatColor.RED+"You must type a number after /join or you can leave it blank! I just couldn't get a number out of that!");
				 return;
			 }
			
			if(chosen<1)
			{
				 player.sendMessage(ChatColor.RED+"The number must be above 0 and less than or equal to 6!");
				 return;
			}
			if(chosen>6){
				 player.sendMessage(ChatColor.RED+"The number must be above 0 and less than or equal to 6!");
				 return;
			}
			if(players.get(chosen)==null)
			{
				
			}else if(players.get(chosen).size()>=maxPlayers){
				 player.sendMessage(ChatColor.RED+"Sorry your chosen number is full!");
				 return;
			}
			gameID = chosen;
			}

		
	//if(Bukkit.getWorld("world_"+PlayerWorld.LAYERSPLEEF+"_game_"+gameID) == null)
	//{
		//Bukkit.getServer().createWorld(new WorldCreator("world_"+PlayerWorld.LAYERSPLEEF+"_game_"+gameID));	
	//
		 ArrayList<Player> playerTemp = players.get(gameID);
		 playerTemp.add(player);
		players.put(gameID, playerTemp);
		player.sendMessage(ChatColor.GREEN+"You have joined "+gameID+"!");
		World w = Bukkit.getWorld("world_"+PlayerWorld.LAYERSPLEEF+"_game_"+gameID);
		if(w==null)
		{
			Bukkit.broadcastMessage("World is null! Debug Message : "+("world_"+PlayerWorld.LAYERSPLEEF+"_game_"+gameID));
		}
		player.teleport(new Location(w,1008,17,312));
	/*	World source = Bukkit.getWorld("world_1_game_1");
		File sourceFolder = source.getWorldFolder();
		 
		// The world to overwrite when copying
		World target = Bukkit.getWorld("world_1_game");
		File targetFolder = target.getWorldFolder();
		copyWorld(sourceFolder, targetFolder);
*/
	}
	@Override
	public void onLeaveCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		Player player = (Player) sender;
		if(players2.containsKey(player))
		{
			onPlayerJoin(player);
			return;
		}
		Lobby.goToLobby(player);
	}
	public void endGame(int id)
	{
		ArrayList<Player> playerList = players.get(id);
		int i = 0;
		while(i<playerList.size())
		{
			onPlayerJoin(playerList.get(i));
			players2.remove(playerList.get(i));
		}
		//Reset the list!
		players.put(id, new ArrayList<Player>());
	}
	public boolean deleteWorld(File path) {
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	     
	      return(path.delete());
	}
	public void copyWorld(File source, File target){
	    try {
	        ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
	        if(!ignore.contains(source.getName())) {
	            if(source.isDirectory()) {
	                if(!target.exists())
	                target.mkdirs();
	                String files[] = source.list();
	                for (String file : files) {
	                    File srcFile = new File(source, file);
	                 //  System.out.println(srcFile.getName());
	                   if(!srcFile.getName().equals("uid.dat"))
	                   {
	                	   System.out.println(srcFile.getName());
	                    File destFile = new File(target, file);
	                    copyWorld(srcFile, destFile);
	                   }
	                 }
	            } else {
	                InputStream in = new FileInputStream(source);
	                OutputStream out = new FileOutputStream(target);
	                byte[] buffer = new byte[1024];
	                int length;
	                while ((length = in.read(buffer)) > 0)
	                    out.write(buffer, 0, length);
	                in.close();
	                out.close();
	            }
	        }
	    } catch (IOException e) {
	 
	    }
	}
	
}
